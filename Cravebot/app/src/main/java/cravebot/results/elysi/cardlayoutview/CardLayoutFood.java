package cravebot.results.elysi.cardlayoutview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.results.elysi.customobjects.OnSwipeListener;
import cravebot.results.elysi.customobjects.PagerContainer;
import cravebot.results.elysi.gridview.RecyclerViewLayout;
import cravebot.work.pearlsantos.cravebot.GoTask;

public class CardLayoutFood extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private GestureDetector mGestureDetector;

    public final static int LOOPS = 1000;
    public static int FIRST_PAGE;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    private ArrayList<FoodItem> sample;
    private PagerContainer mContainer;


    public CardLayoutFood() {

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), sample);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        FIRST_PAGE = sample.size() * LOOPS / 2;
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        // mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        mViewPager.setCurrentItem(FIRST_PAGE);
        mViewPager.setOffscreenPageLimit(3);


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


        final ImageButton gridview = (ImageButton) findViewById(R.id.grid_view_button);
        gridview.getBackground().clearColorFilter();
        gridview.invalidate();
        gridview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridview.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                gridview.invalidate();
                Intent i = new Intent(CardLayoutFood.this, RecyclerViewLayout.class);
                i.putParcelableArrayListExtra(GoTask.LIST_KEY, sample);
                startActivity(i);
                CardLayoutFood.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                CardLayoutFood.this.finish();
            }
        });

        mGestureDetector = new GestureDetector(this, new OnSwipeListener(CardLayoutFood.this, sample) {
            @Override
            public boolean onSwipe(Direction d) {
                super.onSwipe(d);
                SectionsPagerAdapter.PlaceholderFragment frag = (SectionsPagerAdapter.PlaceholderFragment)
                        mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());

                if (d == Direction.up) {
                    if (frag != null) {
                        if (!(frag.getMoreInfo().getVisibility() == View.VISIBLE)) {
                            frag.changeVisibility(frag.getMoreInfo(), frag.getPlace());
                        } else {
                            frag.changeVisibility(frag.getPlace(), frag.getMoreInfo());
                        }
                    }
                    return true;
                } else if (d == Direction.down) {
                    if (frag != null) {
                        if (frag.getMoreInfo().getVisibility() == View.VISIBLE) {
                            frag.changeVisibility(frag.getPlace(), frag.getMoreInfo());
                        } else
                            finish();
                    }
                    return true;
                } else if (d == Direction.right) {
                    if (!(mViewPager.getCurrentItem() == 0)) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                    } else {
                        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1, true);
                    }
                    return true;
                } else {
                    if (!(mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1)) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    } else {
                        mViewPager.setCurrentItem(0, true);
                    }

                    return true;
                }
            }

        });
        mViewPager.setOnTouchListener(new View.OnTouchListener()

        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean eventConsumed = mGestureDetector.onTouchEvent(event);
                if (eventConsumed) {
                    return true;
                } else
                    return false;
            }
        });


    }


}