package cravebot.results.elysi.cardlayoutview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.AsyncTask;


import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.gridview.GridViewLayout;

public class CardLayout extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private InfinitePagerAdapter wrappedAdapter;
    private GestureDetector mGestureDetector;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public InfiniteViewPager mViewPager;
    private ArrayList<FoodItem> sample;
    private PagerContainer mContainer;


    public CardLayout() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_card_layout);

        sample = getIntent().getParcelableArrayListExtra(GoTask.LIST_KEY);

        mContainer = (PagerContainer) findViewById(R.id.pager_container);

        mViewPager = (InfiniteViewPager) findViewById(R.id.viewPager);

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
        wrappedAdapter = new InfinitePagerAdapter(mSectionsPagerAdapter);
        mViewPager.setAdapter(wrappedAdapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        // mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        // set current item in the adapter to middle

        mViewPager.setOffscreenPageLimit(3);


        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        mViewPager.setClipChildren(false);
        int pos = getIntent().getIntExtra("position", -1);
        if (pos != -1) {
            mViewPager.setCurrentItem((mViewPager.getChildCount() * mSectionsPagerAdapter.LOOPS_COUNT / 2) + pos, false);
        }

//        ImageView background = (ImageView) findViewById(R.id.background);
//        Picasso.with(getApplicationContext()).load(R.drawable.food_bg).into(background);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
//
//        final TabLayout switchViews = (TabLayout) findViewById(R.id.switchView);
//        switchViews.addTab(switchViews.newTab().setIcon(R.drawable.card));
//        TabLayout.Tab grid = switchViews.newTab().setIcon(R.drawable.grid_view_button);
//        switchViews.addTab(grid);
//        switchViews.setSelectedTabIndicatorColor(Color.BLUE);
//        switchViews.setSelectedTabIndicatorHeight(10);
//        grid.select();
//
//        switchViews.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (switchViews.getSelectedTabPosition() == 1) {
//                    Intent i = new Intent(CardLayout.this, GridViewLayout.class);
//                    i.putParcelableArrayListExtra(GoTask.LIST_KEY, sample);
//                    startActivity(i);
//                    CardLayout.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                    CardLayout.this.finish();
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
//        ((FloatingActionButton) findViewById(R.id.gridview)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(CardLayout.this, GridViewLayout.class);
//                    i.putParcelableArrayListExtra(GoTask.LIST_KEY, sample);
//                    startActivity(i);
//                    //getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
//                    CardLayout.this.finish();
//
//            }
//        });


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
                        Intent i = new Intent(CardLayout.this, GridViewLayout.class);
                        i.putParcelableArrayListExtra(GoTask.LIST_KEY, sample);
                        startActivity(i);
                        CardLayout.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        CardLayout.this.finish();
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

//        ((ImageButton) findViewById(R.id.grid_view_button)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(CardLayout.this, GridViewLayout.class);
//                i.putParcelableArrayListExtra(GoTask.LIST_KEY, sample);
//                startActivity(i);
//                CardLayout.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                CardLayout.this.finish();
//            }
//        });

        mGestureDetector = new GestureDetector(this, new OnSwipeListener(CardLayout.this, sample) {
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

        final ImageButton cardView = (ImageButton) findViewById(R.id.card_view_button);
        YoYo.with(Techniques.Pulse)
                .duration(1200)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        YoYo.with(Techniques.Pulse)
                                .duration(1200)
                                .interpolate(new AccelerateDecelerateInterpolator())
                                .withListener(this).playOn(cardView);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).playOn(cardView);
    }

}