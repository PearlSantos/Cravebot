package cravebot.results.elysi.results;

import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

import cravebot.R;

/**
 * Created by elysi on 12/24/2015.
 */

public class SectionsPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static ArrayList<FoodItem> items;
    private static String APIFood,APIResto;
    private final FragmentManager mFragmentManager;
    private static Fragment mFragmentAtPos0;
    private static ViewPager mViewPager;
    private static int position;

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<FoodItem> items, String APIFood, String APIResto) {
        super(fm);
        SectionsPagerAdapter.items = items;
        SectionsPagerAdapter.APIFood = APIFood;
        SectionsPagerAdapter.APIResto = APIResto;
        mFragmentManager = fm;
        mViewPager= CardLayout.mViewPager;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        System.out.println("PRINT: POS " + position);

        return new PlaceholderFragment().newInstance(position);

    }

    public static class PlaceholderFragment extends Fragment {

        private final static String ARG_SECTION_NUMBER = "section_number";

        private int width, height;
        private ImageView foodImage;
        private static FoodItem singleItem;
        private View.OnClickListener lis;
        View view;


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            System.out.println("PRINT: INSTANCE" + sectionNumber);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }


        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_card_layout, container, false);
            singleItem = items.get(getArguments().getInt(ARG_SECTION_NUMBER));



            System.out.println("PRINT: SHOW" + singleItem.getItemName());
            TextView foodName = (TextView) view.findViewById(R.id.foodName);
            TextView restoName = (TextView) view.findViewById(R.id.restoName);
            TextView price = (TextView) view.findViewById(R.id.price);

            Typeface hat = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "fonts/HATTEN.TTF");
            Typeface gad = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "fonts/gadugi.ttf");

            foodName.setTypeface(hat);
            restoName.setTypeface(gad);
            price.setTypeface(hat);

            foodName.setText(singleItem.getItemName());
            restoName.setText(singleItem.getRestoName());
            price.setText("P " + Double.toString(singleItem.getPrice()));

//            foodName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            restoName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            foodImage = (ImageView) view.findViewById(R.id.foodImage);
//            ((BitmapDrawable)foodImage.getDrawable()).getBitmap().recycle();

            UrlImageViewHelper helper = new UrlImageViewHelper();
            UrlImageViewHelper.setUrlDrawable(foodImage, APIFood + singleItem.getPhoto());
            foodImage.invalidate();

            Button testMore = (Button) view.findViewById(R.id.testMore);
////            testMore.setOnClickListener(this.lis);
            testMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FrameLayout moreInfo = (FrameLayout) view.findViewById(R.id.root_frameInfo);
                    moreInfo.setVisibility(View.VISIBLE);

                    for (int i = 0; i < moreInfo.getChildCount(); i++) {
                        View child = moreInfo.getChildAt(i);
                        child.setVisibility(View.VISIBLE);
                        child.postInvalidate();
                    }

                    FrameLayout place = (FrameLayout) view.findViewById(R.id.root_frame);
                    place.setVisibility(View.GONE);

                    for (int i = 0; i < place.getChildCount(); i++) {
                        View child = place.getChildAt(i);
                        child.setVisibility(View.GONE);
                        child.postInvalidate();
                    }

                    //   FragmentTransaction trans = getChildFragmentManager()
//                            .beginTransaction();
//
//
//				/*
//				 * IMPORTANT: We use the "root frame" defined in
//				 * "root_fragment.xml" as the reference to replace fragment
//				 */
//
//                    trans.replace(R.id.root_frame, new MoreInfoFragment().newInstance(getArguments().
//                            getInt(ARG_SECTION_NUMBER)));
//
//				/*
//				 * IMPORTANT: The following lines allow us to add the fragment
//				 * to the stack and return to it later, by pressing back
//				 */
//                    trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    trans.addToBackStack(null);
//
//                    trans.commit();
                }
            });

            TextView foodNameInfo = (TextView) view.findViewById(R.id.foodNameInfo);
            TextView restoNameInfo = (TextView) view.findViewById(R.id.restoNameInfo);
            TextView priceInfo = (TextView) view.findViewById(R.id.priceInfo);


            foodNameInfo.setTypeface(hat);
            restoNameInfo.setTypeface(gad);
            priceInfo.setTypeface(hat);

            foodNameInfo.setText(singleItem.getItemName().trim());
            restoNameInfo.setText(singleItem.getRestoName().trim());
            priceInfo.setText("P " + Double.toString(singleItem.getPrice()).trim());

            TextView description = (TextView) view.findViewById(R.id.description);
            description.setTypeface(gad);
            description.setText(singleItem.getDescription().trim());

            //restoLogo = (ImageView) view.findViewById(R.id.restoLogo);

//            UrlImageViewHelper helper = new UrlImageViewHelper();
//            helper.setUrlDrawable(restoLogo, APIResto+singleItem.getRestoLogo());


            TextView option1 = (TextView) view.findViewById(R.id.option1);
            TextView option2 = (TextView) view.findViewById(R.id.option2);
            TextView option3 = (TextView) view.findViewById(R.id.option3);
            TextView option4 = (TextView) view.findViewById(R.id.option4);
            TextView option5 = (TextView) view.findViewById(R.id.option5);
            TextView option6 = (TextView) view.findViewById(R.id.option6);

            TextView price1 = (TextView) view.findViewById(R.id.price1);
            TextView price2 = (TextView) view.findViewById(R.id.price2);
            TextView price3 = (TextView) view.findViewById(R.id.price3);
            TextView price4 = (TextView) view.findViewById(R.id.price4);
            TextView price5 = (TextView) view.findViewById(R.id.price5);
            TextView price6 = (TextView) view.findViewById(R.id.price6);


            option1.setTypeface(gad);
            option2.setTypeface(gad);
            option3.setTypeface(gad);
            option4.setTypeface(gad);
            option5.setTypeface(gad);
            option6.setTypeface(gad);

            price1.setTypeface(gad);
            price2.setTypeface(gad);
            price3.setTypeface(gad);
            price4.setTypeface(gad);
            price5.setTypeface(gad);
            price6.setTypeface(gad);

            option1.setText(singleItem.getOption1().trim());
            option2.setText(singleItem.getOption2().trim());
            option3.setText(singleItem.getOption3().trim());
            option4.setText(singleItem.getOption4().trim());
            option5.setText(singleItem.getOption5().trim());
            option6.setText(singleItem.getOption6().trim());

            price1.setText(singleItem.getPrice1().trim());
            price2.setText(singleItem.getPrice2().trim());
            price3.setText(singleItem.getPrice3().trim());
            price4.setText(singleItem.getPrice4().trim());
            price5.setText(singleItem.getPrice5().trim());
            price6.setText(singleItem.getPrice6().trim());


            ImageButton back = (ImageButton) view.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FrameLayout place = (FrameLayout) view.findViewById(R.id.root_frame);
                    place.setVisibility(View.VISIBLE);

                    for (int i = 0; i < place.getChildCount(); i++) {
                        View child = place.getChildAt(i);
                        child.setVisibility(View.VISIBLE);
                        child.postInvalidate();
                    }

                    FrameLayout moreInfo = (FrameLayout) view.findViewById(R.id.root_frameInfo);
                    moreInfo.setVisibility(View.GONE);

                    for (int i = 0; i < moreInfo.getChildCount(); i++) {
                        View child = moreInfo.getChildAt(i);
                        child.setVisibility(View.GONE);
                        child.postInvalidate();
                    }


                }
            });

            return view;
        }

        public void onDestroy(){
            super.onDestroy();
            unbindDrawables(view.findViewById(R.id.fragmentLayoutWhole));
            System.gc();

        }


        private void unbindDrawables(View view) {
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }
        }

        public void onStop()
        {
            super.onStop();
            foodImage.setImageBitmap(null);
        }

    }

    @Override
    public int getCount() {
        return items.size();
    }

//    public int getItemPosition(Object object)
//    {
//        if (object instanceof PlaceholderFragment && mFragmentAtPos0 instanceof MoreInfoFragment)
//            return POSITION_NONE;
//        return POSITION_UNCHANGED;
//    }

//
//    public static class MoreInfoFragment extends Fragment {
//
//        private final static String ARG_SECTION_NUMBER = "section_number";
//
//        private int width, height;
//        private static ImageView restoLogo;
//        private static FoodItem singleItem;
//
//
//        public static MoreInfoFragment newInstance(int sectionNumber) {
//            MoreInfoFragment fragment = new MoreInfoFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        public MoreInfoFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.more_info_layout, container, false);
//            singleItem = items.get(getArguments().getInt(ARG_SECTION_NUMBER));
//
//            TextView foodName = (TextView) view.findViewById(R.id.foodName);
//            TextView restoName = (TextView) view.findViewById(R.id.restoName);
//            TextView price = (TextView) view.findViewById(R.id.price);
//
//            Typeface hat = Typeface.createFromAsset(getContext().getAssets(), "fonts/HATTEN.TTF");
//            Typeface gad = Typeface.createFromAsset(getContext().getAssets(), "fonts/gadugi.ttf");
//
//            foodName.setTypeface(hat);
//            restoName.setTypeface(gad);
//            price.setTypeface(hat);
//
//            foodName.setText(singleItem.getItemName().trim());
//            restoName.setText(singleItem.getRestoName().trim());
//            price.setText("P " + Double.toString(singleItem.getPrice()).trim());
//
//            TextView description = (TextView) view.findViewById(R.id.description);
//            description.setTypeface(gad);
//            description.setText(singleItem.getDescription().trim());
//
//            //restoLogo = (ImageView) view.findViewById(R.id.restoLogo);
//
////            UrlImageViewHelper helper = new UrlImageViewHelper();
////            helper.setUrlDrawable(restoLogo, APIResto+singleItem.getRestoLogo());
//
//
//            TextView option1 = (TextView) view.findViewById(R.id.option1);
//            TextView option2 = (TextView) view.findViewById(R.id.option2);
//            TextView option3 = (TextView) view.findViewById(R.id.option3);
//            TextView option4 = (TextView) view.findViewById(R.id.option4);
//            TextView option5 = (TextView) view.findViewById(R.id.option5);
//            TextView option6 = (TextView) view.findViewById(R.id.option6);
//
//            TextView price1 = (TextView) view.findViewById(R.id.price1);
//            TextView price2 = (TextView) view.findViewById(R.id.price2);
//            TextView price3 = (TextView) view.findViewById(R.id.price3);
//            TextView price4 = (TextView) view.findViewById(R.id.price4);
//            TextView price5 = (TextView) view.findViewById(R.id.price5);
//            TextView price6 = (TextView) view.findViewById(R.id.price6);
//
//
//            option1.setTypeface(gad);
//            option2.setTypeface(gad);
//            option3.setTypeface(gad);
//            option4.setTypeface(gad);
//            option5.setTypeface(gad);
//            option6.setTypeface(gad);
//
//            price1.setTypeface(gad);
//            price2.setTypeface(gad);
//            price3.setTypeface(gad);
//            price4.setTypeface(gad);
//            price5.setTypeface(gad);
//            price6.setTypeface(gad);
//
//            option1.setText(singleItem.getOption1().trim());
//            option2.setText(singleItem.getOption2().trim());
//            option3.setText(singleItem.getOption3().trim());
//            option4.setText(singleItem.getOption4().trim());
//            option5.setText(singleItem.getOption5().trim());
//            option6.setText(singleItem.getOption6().trim());
//
//            price1.setText(singleItem.getPrice1().trim());
//            price2.setText(singleItem.getPrice2().trim());
//            price3.setText(singleItem.getPrice3().trim());
//            price4.setText(singleItem.getPrice4().trim());
//            price5.setText(singleItem.getPrice5().trim());
//            price6.setText(singleItem.getPrice6().trim());
//
//            return view;
//
//        }
//
//    }
//
//    public static class RootFragment extends Fragment {
//
//        private static final String TAG = "RootFragment";
//
//        private final static String ARG_SECTION_NUMBER = "section_number";
//
//
//
//        public static RootFragment newInstance(int sectionNumber) {
//            RootFragment fragment = new RootFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//		/* Inflate the layout for this fragment */
//            View view = inflater.inflate(R.layout.root_fragment, container, false);
//
//            final FragmentTransaction transaction = getChildFragmentManager()
//                    .beginTransaction();
//		/*
//		 * When this container fragment is created, we fill it with our first
//		 * "real" fragment
//		 */
////            new PlaceholderFragment(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    transaction.replace(R.id.root_frame, MoreInfoFragment.newInstance(getArguments()
////                            .getInt(ARG_SECTION_NUMBER)));
////                    transaction.commit();
////                }
////            });
//            transaction.replace(R.id.root_frame, PlaceholderFragment.newInstance(getArguments()
//                    .getInt(ARG_SECTION_NUMBER)));
//
//            transaction.commit();
//
//            return view;
//        }
//
//    }

}


