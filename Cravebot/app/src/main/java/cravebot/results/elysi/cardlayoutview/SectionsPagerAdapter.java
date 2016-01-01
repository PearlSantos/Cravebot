package cravebot.results.elysi.cardlayoutview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
        private ImageView foodImage, background, backgroundInfo, restoLogo;
        private FoodItem singleItem;
        private View view;
        private FrameLayout moreInfo, place;
        private DisplayImageOptions options;
        private LinearLayout progress;
        private ProgressBar progressInfo;

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
            final int position = getArguments().getInt(ARG_SECTION_NUMBER);
            singleItem = items.get(position);

            options = new DisplayImageOptions.Builder()
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

            System.out.println("PRINT: SHOW" + singleItem.getItemName());
            TextViewPlus foodName = (TextViewPlus) view.findViewById(R.id.foodName);
            TextViewPlus restoName = (TextViewPlus) view.findViewById(R.id.restoName);
            TextViewPlus price = (TextViewPlus) view.findViewById(R.id.price);

//            Typeface hat = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "fonts/HATTEN.TTF");
//            Typeface gad = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "fonts/gadugi.ttf");

//            foodName.setTypeface(hat);
//            restoName.setTypeface(gad);
//            price.setTypeface(hat);


            foodName.setText(singleItem.getItemName());
            restoName.setText(singleItem.getRestoName());
            price.setText("P " + Double.toString(singleItem.getPrice()));

//            foodName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            restoName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            foodImage = (ImageView) view.findViewById(R.id.foodImage);
            foodImage.setVisibility(View.GONE);

            background = (ImageView) view.findViewById(R.id.background);
            backgroundInfo = (ImageView) view.findViewById(R.id.backgroundInfo);
            progress = (LinearLayout) view.findViewById(R.id.progress);

            String foodImg = APIFood + singleItem.getPhoto();
            Picasso.with(getContext().getApplicationContext()).load(R.drawable.card).fit().into(background);

            Picasso.with(getContext().getApplicationContext()).load(R.drawable.card).fit().into(backgroundInfo);

            ImageLoader.getInstance().displayImage(foodImg, foodImage, options, new SimpleImageLoadingListener() {


                @Override
                public void onLoadingStarted(String url, View view) {
                    foodImage.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progress.setVisibility(View.GONE);
                    foodImage.setVisibility(View.VISIBLE);

                }


                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    foodImage.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);

                }
            });


            TextViewPlus foodNameInfo = (TextViewPlus) view.findViewById(R.id.foodNameInfo);
            TextViewPlus restoNameInfo = (TextViewPlus) view.findViewById(R.id.restoNameInfo);
            TextViewPlus priceInfo = (TextViewPlus) view.findViewById(R.id.priceInfo);

            foodNameInfo.setText(singleItem.getItemName().trim());
            restoNameInfo.setText(singleItem.getRestoName().trim());
            priceInfo.setText("P " + Double.toString(singleItem.getPrice()).trim());

            TextViewPlus description = (TextViewPlus) view.findViewById(R.id.description);
            description.setText(singleItem.getDescription().trim());

            progressInfo = (ProgressBar) view.findViewById(R.id.progressInfo);

            restoLogo = (ImageView) view.findViewById(R.id.restoLogo);
            restoLogo.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage
                    (APIResto + singleItem.getRestoLogo(), restoLogo, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String url, View view) {
                            restoLogo.setVisibility(View.GONE);
                            progressInfo.setVisibility(View.VISIBLE);
                        }

                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            progressInfo.setVisibility(View.GONE);
                            foodImage.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            restoLogo.setVisibility(View.VISIBLE);
                            progressInfo.setVisibility(View.GONE);
                        }
                    });


//            UrlImageViewHelper helper = new UrlImageViewHelper();
//            helper.setUrlDrawable(restoLogo, APIResto+singleItem.getRestoLogo());
            //      UrlImageViewHelper.setUrlDrawable(restoLogo, APIResto+singleItem.getRestoLogo(), null, 60000);


            TextViewPlus option1 = (TextViewPlus) view.findViewById(R.id.option1);
            TextViewPlus option2 = (TextViewPlus) view.findViewById(R.id.option2);
            TextViewPlus option3 = (TextViewPlus) view.findViewById(R.id.option3);
            TextViewPlus option4 = (TextViewPlus) view.findViewById(R.id.option4);
            TextViewPlus option5 = (TextViewPlus) view.findViewById(R.id.option5);
            TextViewPlus option6 = (TextViewPlus) view.findViewById(R.id.option6);

            TextViewPlus price1 = (TextViewPlus) view.findViewById(R.id.price1);
            TextViewPlus price2 = (TextViewPlus) view.findViewById(R.id.price2);
            TextViewPlus price3 = (TextViewPlus) view.findViewById(R.id.price3);
            TextViewPlus price4 = (TextViewPlus) view.findViewById(R.id.price4);
            TextViewPlus price5 = (TextViewPlus) view.findViewById(R.id.price5);
            TextViewPlus price6 = (TextViewPlus) view.findViewById(R.id.price6);


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
                    changeVisibility(place, moreInfo);

                }
            });

            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
            FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            Button testGridView = (Button) view.findViewById(R.id.testGridView);
            testGridView.setOnClickListener(new View.OnClickListener() {
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




