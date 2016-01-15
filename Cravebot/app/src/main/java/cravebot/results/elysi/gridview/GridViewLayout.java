package cravebot.results.elysi.gridview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.cardlayoutview.CardLayoutFood;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.work.pearlsantos.cravebot.GoTask;

public class GridViewLayout extends AppCompatActivity {
    private GridView gridview;
    private ArrayList<FoodItem> items;
    private final String APIFood = "http://cravebot.ph/photos/";

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
                Intent i = new Intent(getApplicationContext(), CardLayoutFood.class);
                i.putParcelableArrayListExtra(GoTask.LIST_KEY, items);
                i.putExtra("position", position);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                GridViewLayout.this.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }
        });

        boolean pauseOnScroll = true; // or true
        boolean pauseOnFling = true; // or false
        PauseOnScrollListener listener = new PauseOnScrollListener
                (ImageLoader.getInstance(), pauseOnScroll, pauseOnFling);
        gridview.setOnScrollListener(listener);
//
//        final TabLayout switchViews = (TabLayout) findViewById(R.id.switchView);
//        TabLayout.Tab card = switchViews.newTab().setIcon(R.drawable.card);
//        switchViews.addTab(card);
//        switchViews.addTab(switchViews.newTab().setIcon(R.drawable.grid_view_button));
//        switchViews.setSelectedTabIndicatorColor(Color.BLUE);
//        switchViews.setSelectedTabIndicatorHeight(10);
//        card.select();
//        switchViews.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (switchViews.getSelectedTabPosition() == 0) {
//                    Intent i = new Intent(GridViewLayout.this, CardLayout.class);
//                    i.putParcelableArrayListExtra(GoTask.LIST_KEY, items);
//                    startActivity(i);
//                    GridViewLayout.this.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//                    GridViewLayout.this.finish();
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//        ImageView cardViewButton = (ImageView) findViewById(R.id.card_view_button);
//        Picasso.with(GridViewLayout.this).load(R.drawable.card).fit().into(cardViewButton);
//        cardViewButton.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        ImageView view = (ImageView) v;
//                        view.setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                        v.invalidate();
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP:
//                        Intent i = new Intent(GridViewLayout.this, CardLayoutFood.class);
//                        i.putParcelableArrayListExtra(GoTask.LIST_KEY, items);
//                        startActivity(i);
//                        GridViewLayout.this.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//                        GridViewLayout.this.finish();
//                        // Your action here on button click
//
//                    case MotionEvent.ACTION_CANCEL: {
//                        ImageView view = (ImageView) v;
//                        view.clearColorFilter();
//                        view.invalidate();
//                        break;
//                    }
//                }
//                return true;
//            }
//        });
//        ImageView gridViewButton = (ImageView) findViewById(R.id.grid_view_button);
//        Picasso.with(GridViewLayout.this).load(R.drawable.grid_view_button).fit().into(gridViewButton);
//
//        ;


//        ((ImageButton)findViewById(R.id.card_view_button)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(GridViewLayout.this, CardLayout.class);
//                    i.putParcelableArrayListExtra(GoTask.LIST_KEY, items);
//                startActivity(i);
//                GridViewLayout.this.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//                GridViewLayout.this.finish();
//            }
//        });


 //       final ImageButton gridView = (ImageButton) findViewById(R.id.grid_view_button);
//        YoYo.with(Techniques.Pulse)
//                .duration(1200)
//                .interpolate(new AccelerateDecelerateInterpolator())
//                .withListener(new Animator.AnimatorListener() {
//
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        YoYo.with(Techniques.Pulse)
//                                .duration(1200)
//                                .interpolate(new AccelerateDecelerateInterpolator())
//                                .withListener(this).playOn(gridView);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//                    }
//                }).playOn(gridView);

    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private ImageView imageView;
        private ProgressBar progressBar;
        private View v;
        private LayoutInflater inflater;

        private DisplayImageOptions options;


        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(c);

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
//            View view = convertView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.gridview_item, parent, false);
                holder = new ViewHolder();
                v.setTag(holder);
            } else {
                v = (View) convertView;
                holder = (ViewHolder) v.getTag();
            }

            holder.imageView = (ImageView) v.findViewById(R.id.image);
            holder.progressBar = (ProgressBar) v.findViewById(R.id.progress);
            int width = gridview.getWidth();
            int margins = (width / 4) / 5;
            gridview.setHorizontalSpacing(margins);
            gridview.setVerticalSpacing(margins);
            // gridview.setColumnWidth(width / 4);
            double widthSize = width / 3.5;
            width = (int) widthSize;
            holder.imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width));

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


            return v;
        }

    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}