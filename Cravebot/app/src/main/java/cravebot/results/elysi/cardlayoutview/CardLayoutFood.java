package cravebot.results.elysi.cardlayoutview;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cravebot.R;
import cravebot.customstuff.TextViewPlus;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.results.elysi.customobjects.OnSwipeListener;
import cravebot.results.elysi.customobjects.PagerContainer;
import cravebot.results.elysi.gridview.RecyclerViewLayout;
import cravebot.work.pearlsantos.cravebot.CheckingStart;
import cravebot.work.pearlsantos.cravebot.GoTask;
import cravebot.work.pearlsantos.cravebot.MainActivity;

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

    private LinearLayout message;
    private final String once = "SWIPE_UP";
    private SharedPreferences prefs;


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

                        double heightSize = height * 0.8;
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


        mViewPager.setCurrentItem(FIRST_PAGE);
        mViewPager.setOffscreenPageLimit(3);


        mViewPager.setClipChildren(false);
        int pos = getIntent().getIntExtra("position", -1);
        if (pos != -1) {
            mViewPager.setCurrentItem(pos % sample.size(), false);
        }

        prefs = getApplicationContext().getSharedPreferences
                (CheckingStart.PREFS_NAME, Context.MODE_PRIVATE);

        if (MainActivity.nextAction == 2 && prefs.getInt(once, -1) == -1) {
            message = new LinearLayout(CardLayoutFood.this);
            message.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.BOTTOM));

            message.setOrientation(LinearLayout.VERTICAL);
            message.setGravity(Gravity.CENTER);

            SimpleDraweeView upButton = new SimpleDraweeView(CardLayoutFood.this);
            upButton.setImageURI(Uri.parse("res:/" + R.drawable.up_button));

            TextViewPlus swipeUp = new TextViewPlus(CardLayoutFood.this);
            swipeUp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/avenir_next_condensed.ttc"));
            swipeUp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            swipeUp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red_400));
            swipeUp.setText("Swipe up to\nknow more!");

            float density = getResources().getDisplayMetrics().density;
            int params = (int) (45 * density + 0.5f);

            upButton.setLayoutParams(new LinearLayout.LayoutParams(params, params, Gravity.CENTER_VERTICAL));
            swipeUp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));


            message.addView(upButton);
            message.addView(swipeUp);


            FrameLayout swipeUpMes = (FrameLayout) findViewById(R.id.swipeUpMes);
            swipeUpMes.addView(message);
            swipeUpMes.bringChildToFront(message);

            System.out.println("DISPLAY");

            TranslateAnimation animateSwipeUp = new TranslateAnimation(0, 0, -120, 0);
            animateSwipeUp.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
            animateSwipeUp.setRepeatCount(Animation.INFINITE);
            message.startAnimation(animateSwipeUp);
        }

        mSectionsPagerAdapter.notifyDataSetChanged();


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

                        if (MainActivity.nextAction == 2 && prefs.getInt(once, -1) == -1) {
                            prefs.edit().putInt(once, 1).commit();
                            message.clearAnimation();
                            ((ViewGroup) message.getParent()).removeView(message);
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

        }

        );
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
                                      }

        );


    }


}