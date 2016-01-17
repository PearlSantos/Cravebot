package cravebot.results.elysi.gridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.cardlayoutview.CardLayoutFood;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.work.pearlsantos.cravebot.GoTask;

/**
 * Created by elysi on 1/16/2016.
 */
public class RecyclerViewLayout extends AppCompatActivity {
    private ArrayList<FoodItem> items;
    private static RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        items = getIntent().getParcelableArrayListExtra(GoTask.LIST_KEY);

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), items, RecyclerViewLayout.this);
        mRecyclerView.setAdapter(adapter);
        int spanCount = 3;
        int margins = 40;
        boolean includeEdge = false;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, margins, includeEdge));

    }

    public class MyRecyclerViewAdapter extends RecyclerView
            .Adapter<MyRecyclerViewAdapter
            .ViewHolder> {
        private String LOG_TAG = "MyRecyclerViewAdapter";
        private ArrayList<FoodItem> mDataset;
        private final String APIFood = "http://cravebot.ph/photos/";
        private Context context;
        private DisplayImageOptions options;
        private Activity act;


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView image;
            public ProgressBar progressBar;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
                int width = mRecyclerView.getWidth();
                double widthSize = width / 3.3;
                width = (int) widthSize;
                image.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width));

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent i = new Intent(act, CardLayoutFood.class);
                i.putParcelableArrayListExtra(GoTask.LIST_KEY, mDataset);
                i.putExtra("position", getAdapterPosition());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                act.startActivity(i);
                act.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                act.finish();
            }
        }

        public MyRecyclerViewAdapter(Context context, ArrayList<FoodItem> myDataset, Activity act) {
            this.context = context;
            mDataset = myDataset;
            this.act = act;

            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.cravebot_start)
                    .showImageOnLoading(R.drawable.card)
                    .cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.gridview_item, parent, false);
            ViewHolder dataObjectHolder = new ViewHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holderT, int position) {
            final ViewHolder holder = holderT;
            String photo = mDataset.get(position).getPhoto();
            ImageLoader.getInstance().displayImage(APIFood + photo, holder.image,
                    options, new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String url, View view) {
                            holder.progressBar.setProgress(0) ;
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

        }

//    public void addItem(ViewHolder dataObj, int index) {
//        mDataset.add(dataObj);
//        notifyItemInserted(index);
//    }
//
//    public void deleteItem(int index) {
//        mDataset.remove(index);
//        notifyItemRemoved(index);
//    }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    }
}