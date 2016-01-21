package cravebot.work.pearlsantos.cravebot;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cravebot.R;

public class InstructionSlides extends AppCompatActivity {

    private CustomAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static ArrayList<Integer> imgId;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_slides);

        imgId = new ArrayList<>();
        imgId.add(R.drawable.instruction_1);
        imgId.add(R.drawable.instruction_2);
        imgId.add(R.drawable.instruction_3);
        imgId.add(R.drawable.instruction_4);


        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        back = (Button) findViewById(R.id.goBack);
        back.getBackground().clearColorFilter();
        back.invalidate();
        back.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.getBackground().setColorFilter(0xFF6666, PorterDuff.Mode.SRC_ATOP);
                back.invalidate();
                finish();
                InstructionSlides.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(back,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(310);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();

        mSectionsPagerAdapter = new CustomAdapter(getSupportFragmentManager());

        mViewPager.setClipChildren(false);
        mViewPager.setAdapter(mSectionsPagerAdapter);
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
            ((SimpleDraweeView) rootView.findViewById(R.id.instruction)).setImageURI(
                    Uri.parse("res:/" + imgId.get(getArguments().getInt(ARG_SECTION_NUMBER))));
            return rootView;
        }
    }
}
