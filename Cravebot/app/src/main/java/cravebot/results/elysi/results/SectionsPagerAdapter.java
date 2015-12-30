package cravebot.results.elysi.results;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cravebot.R;
import cravebot.customfont.TextViewPlus;

/**
 *  /**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
* Created by elysi on 12/24/2015.
 */

public class SectionsPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static ArrayList<FoodItem> items;
    private static String APIFood, APIResto;

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<FoodItem> items,
            String APIFood, String APIResto) {
        super(fm);
        this.items = items;
        this.APIFood = APIFood;
        this.APIResto = APIResto;
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
        public DisplayImageOptions options;

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


            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .showImageOnLoading(R.drawable.card) // resource or drawable
                    .showImageForEmptyUri(R.drawable.card) // resource or drawable
                    .showImageOnFail(R.drawable.card)
                    .showImageOnLoading(R.drawable.back_button)//display stub image until image is loaded
                    .displayer(new FadeInBitmapDisplayer(20))
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
            background = (ImageView) view.findViewById(R.id.background);
            backgroundInfo = (ImageView) view.findViewById(R.id.backgroundInfo);
//            UrlImageViewHelper helper = new UrlImageViewHelper();
//            UrlImageViewHelper.setUrlDrawable(foodImage, APIFood + singleItem.getPhoto());
//            foodImage.invalidate();
//            background.invalidate();
//            backgroundInfo.invalidate();

            String foodImg = APIFood + singleItem.getPhoto();

            loadBitmapFromRes(R.drawable.card, background);
            loadBitmapFromRes(R.drawable.card, backgroundInfo);
           // foodImage.setImageBitmap(images.get(getArguments().getInt(ARG_SECTION_NUMBER)));
            ImageLoader.getInstance().displayImage(foodImg, foodImage, options, new SimpleImageLoadingListener() {
                boolean cacheFound;
                ProgressDialog progressDialog;
                @Override
                public void onLoadingStarted(String url, View view) {
                                    }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                }
            });

//          loadBitmapFromURL(APIFood+singleItem.getPhoto(), foodImage);

            moreInfo = (FrameLayout) view.findViewById(R.id.root_frameInfo);
            place = (FrameLayout) view.findViewById(R.id.root_frame);

            TextViewPlus foodNameInfo = (TextViewPlus) view.findViewById(R.id.foodNameInfo);
            TextViewPlus restoNameInfo = (TextViewPlus) view.findViewById(R.id.restoNameInfo);
            TextViewPlus priceInfo = (TextViewPlus) view.findViewById(R.id.priceInfo);

            foodNameInfo.setText(singleItem.getItemName().trim());
            restoNameInfo.setText(singleItem.getRestoName().trim());
            priceInfo.setText("P " + Double.toString(singleItem.getPrice()).trim());

            TextViewPlus description = (TextViewPlus) view.findViewById(R.id.description);
            description.setText(singleItem.getDescription().trim());

            restoLogo = (ImageView) view.findViewById(R.id.restoLogo);
            ImageLoader.getInstance().displayImage(APIResto+singleItem.getRestoLogo(), restoLogo, options, new SimpleImageLoadingListener() {
                boolean cacheFound;

                @Override
                public void onLoadingStarted(String url, View view) {
                    List<String> memCache = MemoryCacheUtils.findCacheKeysForImageUri(url, ImageLoader.getInstance().getMemoryCache());
                    cacheFound = !memCache.isEmpty();
                    if (!cacheFound) {
                        File discCache = DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache());
                        if (discCache != null) {
                            System.out.println("IS CACHED");
                            cacheFound = discCache.exists();
                        }
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (cacheFound) {
                        MemoryCacheUtils.removeFromCache(imageUri, ImageLoader.getInstance().getMemoryCache());
                        DiskCacheUtils.removeFromCache(imageUri, ImageLoader.getInstance().getDiskCache());
                        ImageLoader.getInstance().displayImage(imageUri, (ImageView) view);
                    }
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

            return view;
        }

        public void onDestroy() {
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

        public void onStop() {
            super.onStop();
            foodImage.setImageBitmap(null);
            backgroundInfo.setImageBitmap(null);
            background.setImageBitmap(null);
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

        }

        public void displayFoodImage(String url){

        }

        public void displayRestoLogo(String url){
        }


        public FrameLayout getMoreInfo() {
            return moreInfo;
        }

        public FrameLayout getPlace() {
            return place;
        }


        public void loadBitmapFromRes(int resId, ImageView imageView) {
//            BitmapWorkerTask task = new BitmapWorkerTask(getContext().getApplicationContext(),imageView);
//            final String imageKey = String.valueOf(resId);
//
//            final Bitmap bitmap = task.getBitmapFromMemCache(imageKey);
//            if (bitmap != null) {
//                imageView.setImageBitmap(bitmap);
//            } else {
//                imageView.setImageResource(R.drawable.back_button);
//                task = new BitmapWorkerTask(getContext().getApplicationContext(),imageView);
//                task.execute(resId);
//            }
            final BitmapWorkerTaskT task = new BitmapWorkerTaskT(getContext().getApplicationContext(), imageView);
            Bitmap bm = task.decodeBitmapFromResource(resId);
            imageView.setImageBitmap(bm);

        }


        public void loadBitmapFromURL(String resId, ImageView imageView) {
            if (BitmapWorkerTaskT.cancelPotentialWork(resId, imageView)) {
                final BitmapWorkerTaskT task = new BitmapWorkerTaskT(getContext().getApplicationContext(), imageView);
                Bitmap mPlaceHolderBitmap = task.decodeBitmapFromResource(R.drawable.back_button);
                final BitmapWorkerTaskT.AsyncDrawableT asyncDrawable =
                        new BitmapWorkerTaskT.AsyncDrawableT(getResources(), mPlaceHolderBitmap, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(resId);
            }
        }


    }


}




