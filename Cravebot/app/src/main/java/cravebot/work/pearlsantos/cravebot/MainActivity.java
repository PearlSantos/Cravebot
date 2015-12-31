package cravebot.work.pearlsantos.cravebot;


import android.content.res.Configuration;
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

import cravebot.R;
import cravebot.results.elysi.cardlayoutview.GoTask;

public class MainActivity extends AppCompatActivity {
    Integer[] imgRes;
    Integer[] imgRes2;
    String[] holder;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    private boolean[] filterClicked;
    String TAG;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "activity created");
        setContentView(R.layout.activity_main);
        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());//for caching

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        final ImageLoader imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheExtraOptions(480, 320, null)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
                .threadPriority(Thread.MAX_PRIORITY)
                .threadPoolSize(5)
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(options)
                .build();

        imageLoader.init(config);

        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();

        getSupportActionBar().hide();
        holder = new String[] {"o","o","o","o","o","o","o","o","o","o","o","o"};
        imgRes = new Integer[] {R.drawable.beef,
                  R.drawable.beverages,
                R.drawable.burger_sandwiches,
                R.drawable.chicken,
                R.drawable.desserts_pastries,
                R.drawable.fruits_vegetables,
                R.drawable.noodles_soup,
                R.drawable.pizza_pasta,
                R.drawable.pork,
                R.drawable.seafood,
                R.drawable.setmeals,
                R.drawable.snacks};
        imgRes2 = new Integer[] {R.mipmap.beef_g,
                R.mipmap.beverages_g,
                R.mipmap.burger_sandwiches_g,
                R.mipmap.chicken_g,
                R.mipmap.desserts_pastries_g,
                R.mipmap.fruits_vegetables_g,
                R.mipmap.noodles_soup_g,
                R.mipmap.pizza_pasta_g,
                R.mipmap.pork_g,
                R.mipmap.seafood_g,
                R.mipmap.setmeals_g,
                R.mipmap.snacks_g};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);
        // public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid)
        mDrawerList.setAdapter(new CustomListAdapter(this, holder, imgRes));
        filterClicked = new boolean[imgRes.length];
        for(int i=0; i<imgRes.length;i++){
            filterClicked[i] = false;
        }
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView im = (ImageView) view.findViewById(R.id.choices);
                if(!filterClicked[position]) {
                    im.setImageResource(imgRes2[position]);
                    filterClicked[position] = true;
                }
                else{
                    im.setImageResource(imgRes[position]);
                    filterClicked[position] = false;
                }

            }
        });
//        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);z
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //changeBackground(position);
//                view = (ImageView) findViewById(R.id.choices);
//                view.setImageResource(imgRes2[position]);
//            }
//        });
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.mipmap.ic_launcher,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // create RangeSeekBar as Integer range between 20 and 75
        final RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(0, 300, this);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                // handle changed range values

            }
        });

// add RangeSeekBar to pre-defined layout
        ImageButton goButton = (ImageButton) findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double min = seekBar.getSelectedMinValue();
                double max = seekBar.getSelectedMaxValue();
                Log.d("MainActivity", "button pressed");
                new GoTask(getApplicationContext(), filterClicked, min, max).execute("test");

            }
        });
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content_frame);
        RelativeLayout.LayoutParams seekParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        seekParam.addRule(RelativeLayout.BELOW, goButton.getId());
        seekParam.setMargins(5, 0, 5, 0);
        layout.addView(seekBar, seekParam);


    }




    // Set the adapter for the list view
    //TO-DO: create an adapter that helps with the listview contaning only images
    // Set the list's click listener


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new SampleFragment();
                break;
            case 1:
                fragment = new SampleFragment();
                break;
            case 2:
                fragment = new SampleFragment();
                break;
            default:
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .commit();

        mDrawerList.setItemChecked(possition, true);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    public void changeBackground(int position){
        ImageView imageView = (ImageView) findViewById(R.id.choices);
        imageView.setImageResource(imgRes2[position]);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            //SelectItem(position);

        }


    }

    //Clearing caches
    public void onDestroy(){
        super.onDestroy();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }



}
//
class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private ImageView imageView;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.custom_drawer_item, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_drawer_item, null,true);

        final int pos = position;
        //TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        imageView = (ImageView) rowView.findViewById(R.id.choices);
        //Picasso.with(context).load(imgid[pos]).fit().into(imageView);
        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        //txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[pos]);

        //extratxt.setText("Description "+itemname[position]);
        return rowView;

    };
}
