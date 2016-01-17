package cravebot.results.elysi.cardlayoutview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;


import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

import cravebot.R;
import cravebot.customstuff.TextViewPlus;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.results.elysi.customobjects.SmartFragmentStatePagerAdapter;

/**
 * /**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 * Created by elysi on 12/24/2015.
 */

public class SectionsPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static ArrayList<FoodItem> items;

    private final static String APIFood = "http://cravebot.ph/photos/";
    private final static String APIResto = "http://cravebot.ph/photos/logos/";


    public SectionsPagerAdapter(FragmentManager fm, ArrayList<FoodItem> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        System.out.println("PRINT: POS " + position);
        position = position % items.size();
        return new PlaceholderFragment().newInstance(position);

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
        return items.size() * CardLayoutFood.LOOPS;
    }

    public static class PlaceholderFragment extends Fragment {

        private final static String ARG_SECTION_NUMBER = "section_number";
        private SimpleDraweeView foodImage, restoLogo;
        private FoodItem singleItem;
        private View view;
        private FrameLayout moreInfo, place;
        private ProgressBar progressBar, progressBarInfo;
        private GestureDetector ges;

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
            view = inflater.inflate(R.layout.fragment_card_layout_food, container, false);
            singleItem = items.get(getArguments().getInt(ARG_SECTION_NUMBER));

            ((TextViewPlus) view.findViewById(R.id.foodName)).setText(singleItem.getItemName());
            ((TextViewPlus) view.findViewById(R.id.restoName)).setText(singleItem.getRestoName());
            ((TextViewPlus) view.findViewById(R.id.price)).setText("P " + String.format("%.2f", singleItem.getPrice()).trim());

            foodImage = (SimpleDraweeView) view.findViewById(R.id.foodImage);
            //    foodImage.setVisibility(View.GONE);
            //   progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            String foodImg = APIFood + singleItem.getPhoto();

            Uri food = Uri.parse(foodImg);
            foodImage.setImageURI(food);


            ((TextViewPlus) view.findViewById(R.id.foodNameInfo)).setText(singleItem.getItemName().trim());
            ((TextViewPlus) view.findViewById(R.id.restoNameInfo)).setText(singleItem.getRestoName().trim());
            ((TextViewPlus) view.findViewById(R.id.priceInfo)).setText("P " + String.format("%.2f", singleItem.getPrice()).trim());

            ((TextViewPlus) view.findViewById(R.id.description)).setText(singleItem.getDescription().trim());

           // progressBarInfo = (ProgressBar) view.findViewById(R.idandroid.progressBarInfo);

            restoLogo = (SimpleDraweeView) view.findViewById(R.id.restoLogo);
           // restoLogo.setVisibility(View.GONE);
            String restoLogoUrl = APIResto+singleItem.getRestoLogo();
            Log.d("RestoLogo", restoLogoUrl);
            //if (!restoLogoUrl.equals("null")) {
                Uri resto = Uri.parse(restoLogoUrl);
                restoLogo.setImageURI(resto);

//            } else {
//                progressBarInfo.setVisibility(View.GONE);
//                restoLogo.setVisibility(View.GONE);
//            }

            LinearLayout putOptions = (LinearLayout) view.findViewById(R.id.putOptions);
            int paddingBottom = putOptions.getPaddingBottom();
            float textS = ((TextViewPlus) view.findViewById(R.id.description)).getTextSize();
            int i = 1;
            while (i <= 6 && !singleItem.getOptions(i).trim().equals("")) {
                String option = singleItem.getOptions(i).trim();
                if (!option.equals("")) {
                    LinearLayout layoutOptions = new LinearLayout(getActivity().getApplicationContext());
                    layoutOptions.setOrientation(LinearLayout.HORIZONTAL);
                    layoutOptions.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                            , ViewGroup.LayoutParams.WRAP_CONTENT));
                    layoutOptions.setPadding(0, 0, 0, paddingBottom);

                    TextViewPlus optionText = new TextViewPlus(getActivity().getApplicationContext());
                    optionText.setGravity(Gravity.START);
                    optionText.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                    optionText.setPadding(0, 0, paddingBottom, 0);
                    optionText.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),
                            R.color.darkGray));
                    optionText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textS);
                    optionText.setText(option);

                    String optionPriceString = singleItem.getPrices(i).trim();
                    TextViewPlus optionPrice = null;
                    if (!optionPriceString.equals("")) {
                        optionPrice = new TextViewPlus(getActivity().getApplicationContext());
                        optionPrice.setGravity(Gravity.END);
                        optionPrice.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f));
                        optionPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, textS);
                        optionPrice.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),
                                R.color.darkGray));
                        String prices = singleItem.getPrices(i).trim();
                        if (prices.trim().substring(0).equals("P"))
                            optionPrice.setText(prices);
                        else optionPrice.setText("P " + prices);
                    }

                    layoutOptions.addView(optionText);
                    if (optionPrice != null) {
                        layoutOptions.addView(optionPrice);
                    }

                    putOptions.addView(layoutOptions);

                    i++;
                }
            }

            final ImageButton back = (ImageButton) view.findViewById(R.id.back);
            back.getBackground().clearColorFilter();
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    back.invalidate();
                    changeVisibility(place, moreInfo);

                }

            });
            moreInfo = (FrameLayout) view.findViewById(R.id.root_frameInfo);
            place = (FrameLayout) view.findViewById(R.id.root_frame);

            return view;
        }

        public void changeVisibility(FrameLayout visible, FrameLayout gone) {
            visible.setVisibility(View.VISIBLE);


            for (int i = 0; i < visible.getChildCount(); i++) {
                View child = visible.getChildAt(i);
                child.setVisibility(View.VISIBLE);
                child.postInvalidate();
            }
            gone.setVisibility(View.GONE);

            for (int i = 0; i < gone.getChildCount(); i++) {
                View child = gone.getChildAt(i);
                child.setVisibility(View.GONE);
                child.postInvalidate();
            }
            visible.clearAnimation();
            visible.startLayoutAnimation();

        }

        public void displayFoodImage(String url) {

        }

        public void displayRestoLogo(String url) {
        }


        public FrameLayout getMoreInfo() {
            return moreInfo;
        }

        public FrameLayout getPlace() {
            return place;
        }

        public ImageView getFoodImage() {
            return foodImage;
        }

    }


}





