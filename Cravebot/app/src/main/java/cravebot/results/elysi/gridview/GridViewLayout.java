package cravebot.results.elysi.gridview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.cardlayoutview.CardLayout;
import cravebot.results.elysi.cardlayoutview.FoodItem;
import cravebot.results.elysi.cardlayoutview.GoTask;

public class GridViewLayout extends AppCompatActivity {
    private GridView gridview;
    private ArrayList<FoodItem> items;
    private final String APIFood = "http://cravebot.ph/photos/";
    private final String APIResto = "http://cravebot.ph/photos/logos/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridview = (GridView) findViewById(R.id.gridview);
        items = getIntent().getParcelableArrayListExtra(GoTask.LIST_KEY);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //GridViewLayout.this.overridePendingTransition(R.anim.zoom_out, R.anim.zoom_in);
                Intent i = new Intent(getApplicationContext(), CardLayout.class);
                i.putParcelableArrayListExtra(GoTask.LIST_KEY, items);
                i.putExtra("position", position);
                startActivity(i);
                finish();
            }
        });

        boolean pauseOnScroll = true; // or true
        boolean pauseOnFling = true; // or false
        PauseOnScrollListener listener = new PauseOnScrollListener
                (ImageLoader.getInstance(), pauseOnScroll, pauseOnFling);
        gridview.setOnScrollListener(listener);

    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private ImageView imageView;
        private LayoutInflater inflater;

        private DisplayImageOptions options;


        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(c);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.card)
                    .showImageForEmptyUri(R.drawable.card)
                    .showImageOnFail(R.drawable.card)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }

        public int getCount() {
            return items.size();
        }

        public Object getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                // if it's not recycled, initialize some attributes
                view = inflater.inflate(R.layout.gridview_layout, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.image);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                view.setTag(holder);
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                int width = gridview.getWidth();
                int margins = (width / 4) / 5;
                gridview.setHorizontalSpacing(margins);
                gridview.setVerticalSpacing(margins);
                // gridview.setColumnWidth(width / 4);
                double widthSize = width / 3.5;
                width = (int) widthSize;
                holder.imageView.setLayoutParams(new FrameLayout.LayoutParams(width, width));
            } else {
                holder = (ViewHolder) view.getTag();
            }


            FoodItem singleItem = items.get(position);
            ImageLoader.getInstance().displayImage(APIFood + singleItem.getPhoto(), holder.imageView,
                    options, new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String url, View view) {
                            holder.progressBar.setProgress(0);
                            holder.progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }
//                    , new ImageLoadingProgressListener() {
//                @Override
//                public void onProgressUpdate(String imageUri, View view, int current, int total) {
//                    holder.progressBar.setProgress(Math.round(100.0f * current / total));
//                }
//            }
            );


            return view;
        }

    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}