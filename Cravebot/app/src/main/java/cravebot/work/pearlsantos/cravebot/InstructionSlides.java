package cravebot.work.pearlsantos.cravebot;

import android.graphics.Typeface;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cravebot.R;
import cravebot.results.elysi.customobjects.PagerContainer;

public class InstructionSlides extends AppCompatActivity {

    private CustomAdapter mSectionsPagerAdapter;
    private PagerContainer mContainer;
    private ViewPager mViewPager;
    private static ArrayList<Integer> imgId;
    private Button back;
    private static ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intstruction_slides);

        imgId = new ArrayList<>();
        imgId.add(R.drawable.instruction_1);
        imgId.add(R.drawable.instruction_2);
        imgId.add(R.drawable.instruction_3);

        back = (Button) findViewById(R.id.goBack);
        back.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/avenir_next_condensed.ttc"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                                .withListener(this).playOn(back);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).playOn(back);



        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mSectionsPagerAdapter = new CustomAdapter(getSupportFragmentManager());


        mViewPager.setAdapter(mSectionsPagerAdapter);


        // mViewPager.setOffscreenPageLimit(3);
        mViewPager.setClipChildren(false);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imgId.size() - 1) {
                    back.setVisibility(View.VISIBLE);
                } else {
                    back.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    public class CustomAdapter extends FragmentPagerAdapter {

        public CustomAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Instructions.newInstance(position);
        }

        @Override
        public int getCount() {
            return imgId.size();
        }

    }

    public static class Instructions extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static Instructions newInstance(int sectionNumber) {
            Instructions fragment = new Instructions();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public Instructions() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_instruction_slides, container, false);
            Picasso.with(getActivity().getApplicationContext()).load(imgId.get(getArguments().getInt
                    (ARG_SECTION_NUMBER))).
                    fit().into(((ImageView) rootView.findViewById(R.id.instruction)));
//            background = (ImageView) rootView.findViewById(R.id.instruction);
//            background.setImageResource(imgId.get(getArguments().getInt
//                    (ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
