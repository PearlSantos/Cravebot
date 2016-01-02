package cravebot.results.elysi.cardlayoutview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import cravebot.R;

public class CardLayout extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private GestureDetector mGestureDetector;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    private ArrayList<FoodItem> sample;
    private PagerContainer mContainer;


    public CardLayout() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_card_layout);

//        sample = new ArrayList<>();
//        sample.add(new FoodItem("Ayo Makan", "Ayo Makan.png", "AM002", "Beef Shawarma Plate", 100,
//                "Juicy Beef Shawarma with fresh tomatoes, onion and cucumber \nrolled in crisp" +
//                        "Pita bread with Yogurt sauce\n", "Any Side Dish + Iced Tea / Water", "130", " ", "",
//                " ", " ", " ", " ", " ", " ", " ", " ", "e78f005f81190f8c51b0ba1b93a7725f.jpg "));
//        sample.add(new FoodItem("Ayo Makan", "Ayo Makan.png","","Beef Shawarma Wrap",75,"Beef Shawarma"
//                +"Wrap with fresh tomatoes, onion and cucumber \nrolled in crisp Pita bread with Yogurt sauce\n",""
//                ,"","","","","","","","","","","","0c88af560aaf120e0468976635cc3dcd.jpg"));
//        sample.add(new FoodItem("Chicks 2 Go", "Chicks 2 Go.png","","Potato Croquettes (5 pcs)",50,"Choice of any sauce" +
//        "(White Garlic, Cheezy Jalapeno, Honey Mustard, Barbeque, Gravy, Lemon Butter)\n",
//        "","","","","","","","","","","","",
//        "1ad13f9f0253c0a8107ac1072adbdfb3.jpg"));
//        sample.add(new FoodItem("Chicks 2 Go", "Chicks 2 Go.png","","Graham Balls (5 pcs)",35,
//                "Marshmallows coated with crushed grahams\n","","","",""
//                ,"","","","","","","","","cf773842f3c0763c0a46074bb13f8557.jpg"));

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

                        double heightSize = height * 0.8;
                        height = (int) heightSize;
                        double widthSize = height * 0.6;
                        int width = (int) widthSize;


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
//        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        // mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());
        mViewPager.setOffscreenPageLimit(3);


        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        mViewPager.setClipChildren(false);

        int pos = getIntent().getIntExtra("position", -1);
        if (pos != -1) {
            mViewPager.setCurrentItem(pos, false);
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                finish();
//            }
//        });


        mGestureDetector = new GestureDetector(this, new OnSwipeListener() {
            @Override
            public boolean onSwipe(Direction d) {
                super.onSwipe(d);
                if (d == Direction.up) {
                    SectionsPagerAdapter.PlaceholderFragment frag = (SectionsPagerAdapter.PlaceholderFragment)
                            mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
                    if (frag != null) {
                        if (!(frag.getMoreInfo().getVisibility() == View.VISIBLE)) {
                            frag.changeVisibility(frag.getMoreInfo(), frag.getPlace());
                        } else {
                            frag.changeVisibility(frag.getPlace(), frag.getMoreInfo());
                        }
                    }
                    return true;
                } else if (d == Direction.down) {
                    finish();
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
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
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