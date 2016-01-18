package cravebot.results.elysi.cardlayoutview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

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

            String foodImg = APIFood + singleItem.getPhoto();

            Uri food = Uri.parse(foodImg);
            foodImage.setImageURI(food);


            ((TextViewPlus) view.findViewById(R.id.foodNameInfo)).setText(singleItem.getItemName().trim());
            ((TextViewPlus) view.findViewById(R.id.restoNameInfo)).setText(singleItem.getRestoName().trim());
            ((TextViewPlus) view.findViewById(R.id.priceInfo)).setText("P " + String.format("%.2f", singleItem.getPrice()).trim());

            ((TextViewPlus) view.findViewById(R.id.description)).setText(singleItem.getDescription().trim());


            restoLogo = (SimpleDraweeView) view.findViewById(R.id.restoLogo);
            String restoLogoUrl = APIResto + singleItem.getRestoLogo();
            Log.d("RestoLogo", restoLogoUrl);
            if (!restoLogoUrl.equals("null")) {
                Uri resto = Uri.parse(restoLogoUrl);
                restoLogo.setImageURI(resto);
            }
            int i = 1;
            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> priceOptions = new ArrayList<>();
            while (i <= 6 && !singleItem.getOptions(i).trim().equals("")) {
                String op = singleItem.getOptions(i).trim();
                String prices = singleItem.getPrices(i).trim();
                options.add(op);
                priceOptions.add(prices);
                i++;
            }

            if (options.size() != 0) {
                final ListView listviewOptions = (ListView) view.findViewById(R.id.listView_options);
                listviewOptions.setAdapter(new CustomListAdapter(options, priceOptions));

                listviewOptions.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (listviewOptions.getLastVisiblePosition() == listviewOptions.getAdapter().getCount() -1 &&
                                listviewOptions.getChildAt(listviewOptions.getChildCount() - 1).getBottom() <= listviewOptions.getHeight())
                        {
                            //It is scrolled all the way down here
                            listviewOptions.setEnabled(false);
                        }
                        else if (listviewOptions.getFirstVisiblePosition() == 0 &&
                                listviewOptions.getChildAt(0).getTop() >= 0)
                        {
                            //It is scrolled all the way up here
                            listviewOptions.setEnabled(false);
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    }
                });
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

        public FrameLayout getMoreInfo() {
            return moreInfo;
        }

        public FrameLayout getPlace() {
            return place;
        }


        public class CustomListAdapter extends BaseAdapter {
            ArrayList<String> opt;
            ArrayList<String> pri;

            public CustomListAdapter(ArrayList<String> opt, ArrayList<String> pri) {
                this.opt = opt;
                this.pri = pri;
            }

            @Override
            public int getCount() {
                return opt.size();
            }

            @Override
            public Object getItem(int position) {
                return opt.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getContext().getApplicationContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v;

                System.out.println(parent.getClass().getName());
                System.out.println(position);

                if (convertView == null) {
                    v = inflater.inflate(R.layout.option_items, null);
                } else {
                    v = convertView;
                }


                ((TextViewPlus) v.findViewById(R.id.option)).setText(opt.get(position));
                String price = pri.get(position);
                ((TextViewPlus) v.findViewById(R.id.priceOption)).setText(pri.get(position));

                return v;
            }
        }

    }


}





