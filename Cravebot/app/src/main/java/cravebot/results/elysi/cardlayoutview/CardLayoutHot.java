package cravebot.results.elysi.cardlayoutview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;


import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.results.elysi.customobjects.PagerContainer;
import cravebot.results.elysi.customobjects.SmartFragmentStatePagerAdapter;
import cravebot.results.elysi.gridview.RecyclerViewLayout;
import cravebot.work.pearlsantos.cravebot.GoTask;

public class CardLayoutHot extends AppCompatActivity {

    private CustomAdapter mSectionsPagerAdapter;
    private GestureDetector mGestureDetector;

    public final static int LOOPS = 1000;
    public static int FIRST_PAGE;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    private static ArrayList<FoodItem> sample;
    private PagerContainer mContainer;


    public CardLayoutHot() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_card_layout_food);

        sample = getIntent().getParcelableArrayListExtra(GoTask.LIST_KEY);

        mContainer = (PagerContainer) findViewById(R.id.pager_container);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mContainer.getViewTreeObserver().
                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        //now we can retrieve the width and height
                        int height = mContainer.getHeight();
                        int width = mContainer.getWidth();

                        double heightSize = height * 0.9;
                        height = (int) heightSize;
                        double widthSize = width * 0.85;
                        width = (int) widthSize;


                        mViewPager.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                        mViewPager.setPageMargin(width / 20);
//                        mViewPager.setPadding(width /30, width /30, width /30, width /30);


                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                            mContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        else
                            mContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        mSectionsPagerAdapter = new CustomAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        FIRST_PAGE = sample.size() * LOOPS / 2;
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        // mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        mViewPager.setCurrentItem(FIRST_PAGE);
        mViewPager.setOffscreenPageLimit(1);


        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        mViewPager.setClipChildren(false);
        int pos = getIntent().getIntExtra("position", -1);
        if (pos != -1) {
            mViewPager.setCurrentItem(pos, false);
        }

        mSectionsPagerAdapter.notifyDataSetChanged();

//        ImageView background = (ImageView) findViewById(R.id.background);
//        Picasso.with(getApplicationContext()).load(R.drawable.food_bg).into(background);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        ((ImageButton) findViewById(R.id.grid_view_button)).setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        Intent i = new Intent(CardLayoutHot.this, RecyclerViewLayout.class);
                        i.putParcelableArrayListExtra(GoTask.LIST_KEY, sample);
                        startActivity(i);
                        CardLayoutHot.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        CardLayoutHot.this.finish();
                        // Your action here on button click

                    case MotionEvent.ACTION_CANCEL: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

    }

    public static int getFirsPage() {
        return FIRST_PAGE;
    }



    public class CustomAdapter extends SmartFragmentStatePagerAdapter {


        private final static String APIPromos = "where the promos are";


        public CustomAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            System.out.println("PRINT: POS " + position);
            position = position % sample.size();
            return new Promos().newInstance(position);

        }

        @Override
        public int getCount() {
//        if (items != null && items.size() > 0)
//        {
//            return items.size()*LOOPS_COUNT; // simulate infinite by big number of products
//        }
//        else
//        {
//            return 1;
//        }
            return sample.size() * CardLayoutFood.LOOPS;
        }

    }

    public static class Promos extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static Promos newInstance(int sectionNumber) {
            Promos fragment = new Promos();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public Promos() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_card_layout_hot, container, false);
            return rootView;
        }
    }
}

