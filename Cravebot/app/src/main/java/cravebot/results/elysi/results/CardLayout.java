package cravebot.results.elysi.results;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;

import cravebot.R;

public class CardLayout extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private GestureDetector ges;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    private ArrayList<FoodItem> sample;
    private PagerContainer mContainer;

    private boolean end = false;
    private boolean start = false;

    private final String APIFood = "http://cravebot.ph/photos/";
    private final String APIResto = "http://cravebot.ph/photos/logos/";

    public CardLayout(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_card_layout);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.


        sample = new ArrayList<>();
        sample.add(new FoodItem("Ayo Makan", "Ayo Makan.png", "AM002", "Beef Shawarma Plate", 100,
                "Juicy Beef Shawarma with fresh tomatoes, onion and cucumber \nrolled in crisp" +
                        "Pita bread with Yogurt sauce\n", "Any Side Dish + Iced Tea / Water", "130", " ", "",
                " ", " ", " ", " ", " ", " ", " ", " ", "e78f005f81190f8c51b0ba1b93a7725f.jpg "));
        sample.add(new FoodItem("Ayo Makan", "Ayo Makan.png","","Beef Shawarma Wrap",75,"Beef Shawarma"
                +"Wrap with fresh tomatoes, onion and cucumber \nrolled in crisp Pita bread with Yogurt sauce\n",""
                ,"","","","","","","","","","","","0c88af560aaf120e0468976635cc3dcd.jpg"));
        sample.add(new FoodItem("Chicks 2 Go", "Chicks 2 Go.png","","Potato Croquettes (5 pcs)",50,"Choice of any sauce" +
        "(White Garlic, Cheezy Jalapeno, Honey Mustard, Barbeque, Gravy, Lemon Butter)\n",
        "","","","","","","","","","","","",
        "1ad13f9f0253c0a8107ac1072adbdfb3.jpg"));
        sample.add(new FoodItem("Chicks 2 Go", "Chicks 2 Go.png","","Graham Balls (5 pcs)",35,
                "Marshmallows coated with crushed grahams\n","","","",""
                ,"","","","","","","","","cf773842f3c0763c0a46074bb13f8557.jpg"));


//        sample.add(new FoodItem("cute_merlin", "Merlin"));
//        sample.add(new FoodItem("chibi_hiccup", "Hiccup"));
//        sample.add(new FoodItem("chibi_hiccup_4", "Hiccup"));
//        sample.add(new FoodItem("chibi_merlin_and_arthur", "Merlin"));


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

                        double heightSize =  height * 0.8;
                        height = (int) heightSize;
                        double widthSize = height*0.6;
                        int width = (int) widthSize;


                        mViewPager.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                        mViewPager.setPageMargin(width /20);
//                        mViewPager.setPadding(width /30, width /30, width /30, width /30);


                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                            mContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        else
                            mContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), sample, APIFood, APIResto);
//        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());
        //A little space between pages


        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        mViewPager.setClipChildren(false);



//        ges = new GestureDetector(this, new OnSwipeListener());
//        mContainer.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (ges.onTouchEvent(event)) {
//                    if(OnSwipeListener.swipe.equals("up")){
//                        Toast.makeText(TabbedActivity.this, "UP", Toast.LENGTH_LONG).show();
//                    }
//                    else if(OnSwipeListener.swipe.equals("down")){
//                        Toast.makeText(TabbedActivity.this, "UP", Toast.LENGTH_LONG).show();
//                    }
//                    else if(OnSwipeListener.swipe.equals("right")){
//                        Toast.makeText(TabbedActivity.this, "RIGHT", Toast.LENGTH_LONG).show();
//                    }
//                    else if(OnSwipeListener.swipe.equals("left")){
//                        Toast.makeText(TabbedActivity.this, "LEFT", Toast.LENGTH_LONG).show();
//                    }
//                    return true;
//                }
//                return false;
//
//            }
//        });



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            final float threshhold = 0.5f;
            boolean checkDirection;

            public void onPageScrolled(int i, float v, int i2) {
//                if(checkDirection){
//                    if(threshhold>v+0.1f && start == true){
//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1, true);
//                            }
//                        });
//
//                        start = false;
//                        end = true;
//                        System.out.println("START OUT OF BOUNDS S: " + start + " E: " + end);
//                        System.out.println("FLOATS (OUT)" + v + (v+0.1f));
//                    }
//                    if(threshhold<v-0.1f && end== true){
//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mViewPager.setCurrentItem(0, true);
//                            }
//                        });
//                        start = true;
//                        end = false;
//                        System.out.println("END OUT OF BOUNDS S: " + start + " E: " + end);
//                        System.out.println("FLOATE (OUT)" + v + (v-0.1f));
//                    }
//                }
//                checkDirection = false;
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0 || i == mViewPager.getAdapter().getCount() - 1) {
                    if (i == 0) {
                        start = true;
                        end = false;
                        System.out.println("IS AT START (OUT) S: " + start + " E: " + end);
                    }
                    if (i == mViewPager.getAdapter().getCount() - 1) {
                        start = false;
                        end = true;
                        System.out.println("IS AT END (OUT) S: " + start + " E: " + end);
                    }
                } else {
                    start = false;
                    end = false;

                    System.out.println("IS BETWEEN (OUT) S: " + start + " E: " + end + " " + i);
                    System.out.println("(OUT) CHECK DIRECTION:" + checkDirection);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
//                if((start == true || end == true) && i == ViewPager.SCROLL_STATE_DRAGGING){
//                    checkDirection = true;
//                }
//                else
//                    checkDirection = false;
            }
        });




//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                finish();
//            }
//        });



    }

    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }


//    public boolean onTouchEvent(MotionEvent ev){
//        boolean event = ges.onTouchEvent(ev);
//        if(OnSwipeListener.swipe=="up"){
//            //more info
//            Toast.makeText(TabbedActivity.this, "SWIPE UP", Toast.LENGTH_SHORT).show();
//        }
//        else if(OnSwipeListener.swipe=="down"){
//            finish();
//        }
//        else if(OnSwipeListener.swipe=="left" && start ==true){
//            mViewPager.setCurrentItem(mViewPager.getAdapter().getCount()-1, true);
//            start = false;
//            end = true;
//        }
//        else if(OnSwipeListener.swipe=="right" && end ==true){
//            mViewPager.setCurrentItem(0, true);
//            start = true;
//            end = false;
//        }
//
//
//        return event;
//    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */


    /**
     * A placeholder fragment containing a simple view.
     */





}