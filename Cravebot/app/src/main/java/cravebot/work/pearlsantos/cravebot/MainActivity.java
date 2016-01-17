package cravebot.work.pearlsantos.cravebot;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.Picasso;

import java.io.File;

import cravebot.R;

public class MainActivity extends AppCompatActivity {
    Integer[] imgRes;
    Integer[] imgRes2;
    String[] holder;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar mActionBar;
    private boolean[] filterClicked;
    private boolean whatsHotClicked;
    Context context = this;
    public static AlertDialog noInternet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "activity created");
        Fresco.initialize(context);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.
                mipmap.ic_sidebar_button);

        ImageView backgroundToolbar = (ImageView) findViewById(R.id.backgroundToolbar);
        Picasso.with(context).load(R.mipmap.background).into(backgroundToolbar);

        TextView title = (TextView) toolbar.findViewById(R.id.title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
        title.setTypeface(custom_font);

        noInternet = new AlertDialog.Builder(MainActivity.this)
                .setTitle("No Internet Connection")
                .setMessage("Please connect to the internet to use the application's services.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                }).create();

        int nextAction = getIntent().getIntExtra(CheckingStart.WHAT_TO_DO, -1);
        if(nextAction==2){
            MainActivity.this.startActivity(new Intent(MainActivity.this, InstructionSlides.class));
        }

        holder = new String[]{"o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "i"};
        imgRes = new Integer[]{R.drawable.beef,
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
                R.drawable.snacks,
                R.drawable.whats_hot};
        imgRes2 = new Integer[]{R.mipmap.beef_g,
                R.mipmap.beverages_g,
                R.mipmap.burgers_sandwiches_g,
                R.mipmap.chicken_g,
                R.mipmap.desserts_pastries_g,
                R.mipmap.fruits_vegetables_g,
                R.mipmap.noodles_soup_g,
                R.mipmap.pizza_pasta_g,
                R.mipmap.pork_g,
                R.mipmap.seafood_g,
                R.mipmap.set_meals_g,
                R.mipmap.snacks_g,
                R.drawable.whats_hot_button};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);

        ImageView background = (ImageView) findViewById(R.id.background);
        Picasso.with(context).load(R.mipmap.background).into(background);

        mDrawerList.setAdapter(new CustomListAdapter(this, holder, imgRes2));
//        LayoutInflater inflater = getLayoutInflater();
//        View listHeaderView = inflater.inflate(R.layout.header_view,null, false);
//
//        mDrawerList.addHeaderView(listHeaderView);
        filterClicked = new boolean[imgRes.length-1];
        for (int i = 0; i < imgRes.length-1; i++) {
            filterClicked[i] = true;
        }
        whatsHotClicked = false;
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView im = (ImageView) view.findViewById(R.id.choices);
                if(!(holder[position].equals("i"))){
                    if (!filterClicked[position]) {
                        im.setImageResource(imgRes2[position]);
                        filterClicked[position] = true;
                    } else {
                        im.setImageResource(imgRes[position]);
                        filterClicked[position] = false;
                    }
                }
               else{
                    if(!whatsHotClicked){
                        im.setImageResource(imgRes2[position]);
                        whatsHotClicked = true;
                        //insert here what should be done when what's hot is clicked
                    }
                    else{
                        im.setImageResource(imgRes[position]);
                        whatsHotClicked = false;
                    }
                }
            }
        });

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
        final RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(0, 999, this);
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
                new GoTask(context, filterClicked, min, max).execute("test");
            }
        });
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content_frame);
        RelativeLayout.LayoutParams seekParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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
        getMenuInflater().inflate(R.menu.action_button_menu, menu);
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
        switch (item.getItemId()) {
            case R.id.tutorials:
                MainActivity.this.startActivity(new Intent(MainActivity.this, InstructionSlides.class));
                break;
            case R.id.contact_info:
                final Dialog dialog = new Dialog(this, R.style.DialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.contact_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//                dialog.setTitle("CraveBot");
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/avenir_next_condensed.ttc");
                TextView email = (TextView) dialog.findViewById(R.id.email);
                email.setTypeface(custom_font);
                TextView fb = (TextView) dialog.findViewById(R.id.fb);
                fb.setTypeface(custom_font);
                TextView phone = (TextView) dialog.findViewById(R.id.phone);
                phone.setTypeface(custom_font);
                ImageButton close = (ImageButton) dialog.findViewById(R.id.close_dialog);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }

                });
                dialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
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

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_drawer_item, null, true);

        final int pos = position;
        //TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        imageView = (ImageView) rowView.findViewById(R.id.choices);
        //Picasso.with(context).load(imgid[pos]).fit().into(imageView);
        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        //txtTitle.setText(itemname[position]);
        if(itemname[pos].equals("o"))
            imageView.setImageResource(imgid[pos]);
        else
            imageView.setImageResource(R.drawable.whats_hot);

        //extratxt.setText("Description "+itemname[position]);
        return rowView;

    }

}