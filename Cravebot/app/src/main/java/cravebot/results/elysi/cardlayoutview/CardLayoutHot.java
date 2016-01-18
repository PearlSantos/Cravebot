package cravebot.results.elysi.cardlayoutview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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


import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.results.elysi.customobjects.OnSwipeListener;
import cravebot.results.elysi.customobjects.PagerContainer;
import cravebot.results.elysi.customobjects.SmartFragmentStatePagerAdapter;
import cravebot.results.elysi.gridview.RecyclerViewLayout;
import cravebot.work.pearlsantos.cravebot.GoTask;

public class CardLayoutHot extends AppCompatActivity {

    private CustomPromosAdapter mSectionsPagerAdapter;
    private GestureDetector mGestureDetector;

    public final static int LOOPS = 1000;
    public static int FIRST_PAGE;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    private ArrayList<String> items;
    private PagerContainer mContainer;


    public CardLayoutHot() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_card_layout_food);

        //items = getIntent().getParcelableArrayListExtra(GoTask.LIST_KEY);

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

        mSectionsPagerAdapter = new CustomPromosAdapter(getSupportFragmentManager(), items);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        FIRST_PAGE = items.size() * LOOPS / 2;
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
                Intent i = new Intent(CardLayoutHot.this, RecyclerViewLayout.class);
               // i.putParcelableArrayListExtra(GoTask.LIST_KEY, items);
                startActivity(i);
                CardLayoutHot.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                CardLayoutHot.this.finish();
            }
        });

    }

}

class CustomPromosAdapter extends SmartFragmentStatePagerAdapter {
    private static ArrayList<String> items;

    private final static String APIHot = "";

    public CustomPromosAdapter(FragmentManager fm, ArrayList<String> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        System.out.println("PRINT: POS " + position);
        position = position % items.size();
        return new PromosFragment().newInstance(position);

    }

    @Override
    public int getCount() {
        return items.size() * CardLayoutFood.LOOPS;
    }

    public static class PromosFragment extends Fragment {

        private final static String ARG_SECTION_NUMBER = "section_number";
        private SimpleDraweeView promo;


        public static PromosFragment newInstance(int sectionNumber) {
            PromosFragment fragment = new PromosFragment();
            System.out.println("PRINT: INSTANCE" + sectionNumber);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PromosFragment() {
        }


        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_card_layout_food, container, false);
            String singleItem = items.get(getArguments().getInt(ARG_SECTION_NUMBER));

            Uri food = Uri.parse(singleItem);
            promo = (SimpleDraweeView) view.findViewById(R.id.promo);
            promo.setImageURI(food);

            return view;
        }

    }
}