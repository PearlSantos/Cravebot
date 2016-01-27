package cravebot.results.elysi.gridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.cardlayoutview.CardLayoutFood;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.work.pearlsantos.cravebot.GoTask;

/**
 * Created by elysi on 1/16/2016.
 * This class is a RecyclerView that acts as a GridView
 */
public class RecyclerViewLayout extends AppCompatActivity {
    private ArrayList<FoodItem> items;
    private static RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        items = getIntent().getParcelableArrayListExtra(GoTask.LIST_KEY);

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), items, RecyclerViewLayout.this);
        mRecyclerView.setAdapter(adapter);

        //Set margins/spaces between items
        int spanCount = 3;
        int margins = 40;
        boolean includeEdge = false;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, margins, includeEdge));

    }

    // Adapter for RecyclerView
    public class MyRecyclerViewAdapter extends RecyclerView
            .Adapter<MyRecyclerViewAdapter
            .ViewHolder> {
        private String LOG_TAG = "MyRecyclerViewAdapter";
        private ArrayList<FoodItem> mDataset;
        private final String APIFood = "http://cravebot.ph/photos/";
        private Context context;
        private Activity act;


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public SimpleDraweeView image;

            public ViewHolder(View itemView) {
                super(itemView);

                image = (SimpleDraweeView) itemView.findViewById(R.id.sdvImage);
                image.clearColorFilter();
                image.invalidate();

                int width = mRecyclerView.getWidth();
                double widthSize = width / 3.3;
                width = (int) widthSize;
                image.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width));

                itemView.setOnClickListener(this);
            }

            //Setting function for clicking an item
            @Override
            public void onClick(View v) {
                image.setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                image.invalidate();
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

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.recycler_view_item, parent, false);
            ViewHolder dataObjectHolder = new ViewHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holderT, int position) {
            final ViewHolder holder = holderT;
            String photo = APIFood + mDataset.get(position).getPhoto();
            Uri imageUri = Uri.parse(photo);
            holder.image.setImageURI(imageUri);
        }
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    }
}
