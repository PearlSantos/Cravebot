package cravebot.work.pearlsantos.cravebot;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;

import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;


import cravebot.R;

public class MainActivity extends AppCompatActivity {
//    Integer[] imgRes;
//    Integer[] imgRes2;
    String[] items;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar mActionBar;
    private boolean[] filterClicked;
    Context context = this;
    public static AlertDialog noInternet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "activity created");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.
                mipmap.ic_sidebar_button);

        SimpleDraweeView backgroundToolbar = (SimpleDraweeView) findViewById(R.id.backgroundToolbar);
        backgroundToolbar.setImageURI(Uri.parse("res:/" + R.drawable.food_bg));


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

        items = new String[]{"BEEF", "BEVERAGES", "BURGERS + SANDWICHES", "CHICKEN", "DESSERTS + PASTRIES", "FRUITS AND VEGETABLES", "NOODLES + SOUP", "PIZZA + PASTA", "PORK", "SEAFOOD", "SET MEALS", "SNACKS", "WHAT'S HOT"};
//        imgRes = new Integer[]{R.drawable.beef,
//                R.drawable.beverages,
//                R.drawable.burger_sandwiches,
//                R.drawable.chicken,
//                R.drawable.desserts_pastries,
//                R.drawable.fruits_vegetables,
//                R.drawable.noodles_soup,
//                R.drawable.pizza_pasta,
//                R.drawable.pork,
//                R.drawable.seafood,
//                R.drawable.setmeals,
//                R.drawable.snacks,
//                R.drawable.whats_hot};
//        imgRes2 = new Integer[]{R.mipmap.beef_g,
//                R.mipmap.beverages_g,
//                R.mipmap.burgers_sandwiches_g,
//                R.mipmap.chicken_g,
//                R.mipmap.desserts_pastries_g,
//                R.mipmap.fruits_vegetables_g,
//                R.mipmap.noodles_soup_g,
//                R.mipmap.pizza_pasta_g,
//                R.mipmap.pork_g,
//                R.mipmap.seafood_g,
//                R.mipmap.set_meals_g,
//                R.mipmap.snacks_g,
//                R.drawable.whats_hot_button};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);

        ImageView background = (ImageView) findViewById(R.id.background);
        background.setImageURI(Uri.parse("res:/" + R.drawable.food_bg));

        mDrawerList.setAdapter(new CustomListAdapter(this, items));
//        LayoutInflater inflater = getLayoutInflater();
//        View listHeaderView = inflater.inflate(R.layout.header_view,null, false);
//
//        mDrawerList.addHeaderView(listHeaderView);
        filterClicked = new boolean[items.length-1];
        for (int i = 0; i < items.length-1; i++) {
            filterClicked[i] = true;
        }
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardView cView = (CardView) view.findViewById(R.id.containerCard);

                if(!(items[position].equals("WHAT'S HOT"))){
                    if (!filterClicked[position]) {
                        cView.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appGreen)));
                        filterClicked[position] = true;
                        Log.d("filter", Integer.toString(position) + " " + filterClicked[position]);
                    } else {
                        cView.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appRed)));
                        filterClicked[position] = false;
                        Log.d("filter", Integer.toString(position) + " " + filterClicked[position]);
                    }
                }
               else{
                    //anuman ang gusto mong mangyari sa WHAT'S HOT
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

                WindowManager manager = (WindowManager) getSystemService(Activity.WINDOW_SERVICE);
                int width, height;
                AbsListView.LayoutParams params;

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO){
                    width = manager.getDefaultDisplay().getWidth();
                    height = manager.getDefaultDisplay().getHeight();
                }else{
                    Point point = new Point();
                    manager.getDefaultDisplay().getSize(point);
                    width = point.x;
                    height = point.y;
                }

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = width;
                lp.height = height;
                dialog.getWindow().setAttributes(lp);
                //dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
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

    public void onDestroy(){
        super.onDestroy();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();

        imagePipeline.clearCaches();
    }
}


//
class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemName;
    private TextView textView;
    private CardView cardView;

    public CustomListAdapter(Activity context, String[] itemName) {
        super(context, R.layout.custom_drawer_item, itemName);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemName = itemName;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_drawer_item, null, true);

        final int pos = position;
        textView = (TextView) rowView.findViewById(R.id.itemText);
        cardView = (CardView) rowView.findViewById(R.id.containerCard);

        textView.setText(itemName[pos]);
        if(!(itemName[pos].equals("WHAT'S HOT")))
            cardView.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appGreen)));
        else
            cardView.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appYellow)));

        return rowView;

    }

}