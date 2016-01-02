package cravebot.results.elysi.cardlayoutview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cravebot.R;
import cravebot.customstuff.LoadingImages;
import cravebot.customstuff.TextViewPlus;
import cravebot.results.elysi.gridview.GridViewLayout;

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

        return new PlaceholderFragment().newInstance(position);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    public static class PlaceholderFragment extends Fragment {

        private final static String ARG_SECTION_NUMBER = "section_number";
        private ImageView foodImage, background, restoLogo;
        private FoodItem singleItem;
        private View view;
        private FrameLayout moreInfo, place;
        private ProgressBar progressBar, progressBarInfo;

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

            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .resetViewBeforeLoading(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .showImageForEmptyUri(R.drawable.card) // resource or drawable
                    .showImageOnFail(R.drawable.card)
                    .showImageOnLoading(R.drawable.card)//display stub image until image is loaded
                    .displayer(new FadeInBitmapDisplayer(100))
                    .build();


            ((TextViewPlus) view.findViewById(R.id.foodName)).setText(singleItem.getItemName());
            ((TextViewPlus) view.findViewById(R.id.restoName)).setText(singleItem.getRestoName());
            ((TextViewPlus) view.findViewById(R.id.price)).setText("P " + Double.toString(singleItem.getPrice()));

            foodImage = (ImageView) view.findViewById(R.id.foodImage);

            background = (ImageView) view.findViewById(R.id.background);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            String foodImg = APIFood + singleItem.getPhoto();
            Picasso.with(getContext().getApplicationContext()).load(R.drawable.card).fit().into(background);

            ImageLoader.getInstance().displayImage(foodImg, foodImage, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String url, View view) {
                    foodImage.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                }
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                    foodImage.setVisibility(View.VISIBLE);

                }
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    foodImage.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });


            ((TextViewPlus) view.findViewById(R.id.foodNameInfo)).setText(singleItem.getItemName().trim());
            ((TextViewPlus) view.findViewById(R.id.restoNameInfo)).setText(singleItem.getRestoName().trim());
            ((TextViewPlus) view.findViewById(R.id.priceInfo)).setText("P " + Double.toString(singleItem.getPrice()).trim());

            ((TextViewPlus) view.findViewById(R.id.description)).setText(singleItem.getDescription().trim());

            progressBarInfo = (ProgressBar) view.findViewById(R.id.progressBarInfo);

            restoLogo = (ImageView) view.findViewById(R.id.restoLogo);
            ImageLoader.getInstance().displayImage
                    (APIResto + singleItem.getRestoLogo(), restoLogo, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String url, View view) {
                            restoLogo.setVisibility(View.GONE);
                            progressBarInfo.setVisibility(View.VISIBLE);
                        }

                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            progressBarInfo.setVisibility(View.GONE);
                            foodImage.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            restoLogo.setVisibility(View.VISIBLE);
                            progressBarInfo.setVisibility(View.GONE);
                        }
                    });


            LinearLayout putOptions = (LinearLayout) view.findViewById(R.id.putOptions);
            int paddingBottom = putOptions.getPaddingBottom();
            float textS = ((TextViewPlus) view.findViewById(R.id.description)).getTextSize();
            int i = 1;
            while(i<=6 && !singleItem.getOptions(i).trim().equals("")){
                String option = singleItem.getOptions(i).trim();
                if(!option.equals("")){
                    LinearLayout layoutOptions = new LinearLayout(getContext().getApplicationContext());
                    layoutOptions.setOrientation(LinearLayout.HORIZONTAL);
                    layoutOptions.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                            , ViewGroup.LayoutParams.WRAP_CONTENT));
                    layoutOptions.setPadding(0, 0, 0, paddingBottom);

                    TextViewPlus optionText = new TextViewPlus(getContext().getApplicationContext());
                    optionText.setGravity(Gravity.START);
                    optionText.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                    optionText.setPadding(0, 0, paddingBottom, 0);
                    optionText.setTextColor(ContextCompat.getColor(getContext().getApplicationContext(),
                            R.color.darkGray));
                    optionText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textS);
                    optionText.setText(option);

                    TextViewPlus optionPrice = new TextViewPlus(getContext().getApplicationContext());
                    optionPrice.setGravity(Gravity.END);
                    optionPrice.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f));
                    optionPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, textS);
                    optionPrice.setTextColor(ContextCompat.getColor(getContext().getApplicationContext(),
                            R.color.darkGray));
                    String prices = singleItem.getPrices(i).trim();
                    if(prices.substring(0).equals("P"))
                        optionPrice.setText(prices);
                    else optionPrice.setText("P " +prices);

                    layoutOptions.addView(optionText);
                    layoutOptions.addView(optionPrice);

                    putOptions.addView(layoutOptions);

                    i++;
                }
            }

            ((ImageButton) view.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeVisibility(place, moreInfo);

                }
            });

            ((FloatingActionButton) view.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            Button testGridView = (Button) view.findViewById(R.id.testGridView);
            ((Button) view.findViewById(R.id.testGridView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), GridViewLayout.class);
                    i.putParcelableArrayListExtra(GoTask.LIST_KEY, items);
                    startActivity(i);
                    //getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                    getActivity().finish();
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


    }


}




