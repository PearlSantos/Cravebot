package cravebot.work.pearlsantos.cravebot;

//importing needed widgets from internal and external libraries
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import cravebot.R;

public class MainActivity extends AppCompatActivity {
    String[] items; //item name
    DrawerLayout mDrawerLayout; //DrawerLayout for the NavDrawer
    ListView mDrawerList; //
    ActionBarDrawerToggle mDrawerToggle; //toggle listener for the menu icon
    ActionBar mActionBar; 
    private boolean[] filterClicked; //boolean array that states whether a filter is clicked
    Context context = this;
    public static AlertDialog noInternet;
    public static int nextAction; 
    TextView min; //the TextView that holds the minimum value
    TextView max; //the TextView that holds the maximum value
    boolean selectAll = false; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "activity created");
        setContentView(R.layout.activity_main);
		//setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.
                mipmap.ic_menu2);
		//setting up the fresco image
        SimpleDraweeView backgroundToolbar = (SimpleDraweeView) findViewById(R.id.backgroundToolbar);
        backgroundToolbar.setImageURI(Uri.parse("res:/" + R.mipmap.background));

		//setting the toolbar title and style
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

        nextAction = getIntent().getIntExtra(CheckingStart.WHAT_TO_DO, -1);
        if (nextAction == 2) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, InstructionSlides.class));
        }
		
		//setting the array of items
        items = new String[]{"BEEF", "BEVERAGES", "BURGERS + SANDWICHES", "CHICKEN", "DESSERTS + PASTRIES", "FRUITS AND VEGETABLES", "NOODLES + SOUP", "PIZZA + PASTA", "PORK", "SEAFOOD", "SET MEALS", "SNACKS"};
		
		//initializing the drawer layout and listview
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);
        ImageView background = (ImageView) findViewById(R.id.background);
        background.setImageURI(Uri.parse("res:/" + R.mipmap.background));

        mDrawerList.setAdapter(new CustomListAdapter(this, items));
		
		//putting values in the filterClicked boolean array
        filterClicked = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            filterClicked[i] = false;
        }
		
		//setting the list's listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//changes the filter's background upon click and also its corresponding value in the filterClicked boolean array
                TextView tView = (TextView) view.findViewById(R.id.itemText);
                if (!filterClicked[position]) {
                    tView.setBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appGreen)));
                    filterClicked[position] = true;
                    Log.d("filter", Integer.toString(position) + " " + filterClicked[position]);
                } else {
                    tView.setBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appRed)));
                    filterClicked[position] = false;
                    Log.d("filter", Integer.toString(position) + " " + filterClicked[position]);
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

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // create RangeSeekBar as Integer range between 0 to 10
        final RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(0, 10, this);
		//initializing the textView's for min and max
        min = (TextView) findViewById(R.id.minValue);
        max = (TextView) findViewById(R.id.maxValue);
        min.setText("PHP 0");
        max.setText("PHP 500");
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                int mMinValue = seekBar.getSelectedMinValue()*50; //to ensure the intervals of 50
                int mMaxValue = seekBar.getSelectedMaxValue()*50;
                min.setText("PHP " + mMinValue + "");
                max.setText("PHP " + mMaxValue + "");
            }
        });

        ImageButton goButton = (ImageButton) findViewById(R.id.goButton);
        LinearLayout valueSpace = (LinearLayout) findViewById(R.id.displayBudgetValues);
		//adding the listener to the Go Button
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				//determines the selected values for min and max and sending it to the GoTask method
                double min = seekBar.getSelectedMinValue()*50;
                double max = seekBar.getSelectedMaxValue()*50;
                Log.d("MainActivity", "button pressed");
                new GoTask(context, filterClicked, min, max).execute("test");
            }
        });
		// adding the RangeSeekBar to our layout
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content_frame);
        RelativeLayout.LayoutParams seekParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        seekParam.addRule(RelativeLayout.BELOW, goButton.getId());
        seekParam.addRule(RelativeLayout.ABOVE, valueSpace.getId());
        seekParam.setMargins(5, 0, 5, 0);
        layout.addView(seekBar, seekParam);

        //setting the select all button to toggle

        final ImageButton selectAllButton = (ImageButton) findViewById(R.id.selectAllButton);
        selectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectAll) {
                    selectAll = true;
                    selectAllButton.setImageResource(R.drawable.select_all_on);

                    //what happens when select all is clicked
                    for(int i =0; i < mDrawerList.getChildCount(); i++){
                        View someView = mDrawerList.getChildAt(i);
                        TextView textView = (TextView) someView.findViewById(R.id.itemText);
                        textView.setBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appGreen)));
                        filterClicked[i] = true;
                    }
                } else {
                    selectAll = false;
                    selectAllButton.setImageResource(R.drawable.select_all_off);
                   
                    //what happens when select all is turned off
                    for(int i =0; i < mDrawerList.getChildCount(); i++){
                        View someView = mDrawerList.getChildAt(i);
                        TextView textView = (TextView) someView.findViewById(R.id.itemText);
                        textView.setBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appRed)));
                        filterClicked[i] = false;
                    }
                }
            }
        });

        CardView whatsHot = (CardView) findViewById(R.id.whatsHot);
        whatsHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
				//what do you want What's Hot to display

                }
            }
        );


    }






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
                Intent tuts = new Intent(MainActivity.this, InstructionSlides.class);
                tuts.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(tuts);
                MainActivity.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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
        //cardView.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.appGreen)));


        return rowView;

    }


}