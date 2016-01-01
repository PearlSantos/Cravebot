package cravebot.work.pearlsantos.cravebot;


import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cravebot.results.elysi.results.CardLayout;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.
                mipmap.ic_sidebar_button);
        TextView title = (TextView)toolbar.findViewById(R.id.title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
        title.setTypeface(custom_font);
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
                R.mipmap.burgers_sandwiches_g,
                R.mipmap.chicken_g,
                R.mipmap.desserts_pastries_g,
                R.mipmap.fruits_vegetables_g,
                R.mipmap.noodles_soup_g,
                R.mipmap.pizza_pasta_g,
                R.mipmap.pork_g,
                R.mipmap.seafood_g,
                R.mipmap.set_meals_g,
                R.mipmap.snacks_g};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);
        mDrawerList.setAdapter(new CustomListAdapter(this, holder, imgRes));
//        LayoutInflater inflater = getLayoutInflater();
//        View listHeaderView = inflater.inflate(R.layout.header_view,null, false);
//
//        mDrawerList.addHeaderView(listHeaderView);
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
                Intent intent = new Intent(MainActivity.this, CardLayout.class);
                startActivity(intent);
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
        switch(item.getItemId()){
            case R.id.tutorials:

                break;
            case R.id.contact_info:
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.contact_dialog);
                dialog.setTitle("CraveBot");
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/avenir_next_condensed.ttc");
                TextView website = (TextView)dialog.findViewById(R.id.website);
                website.setTypeface(custom_font);
                TextView email = (TextView)dialog.findViewById(R.id.email);
                email.setTypeface(custom_font);
                TextView fb = (TextView)dialog.findViewById(R.id.fb);
                fb.setTypeface(custom_font);
                TextView emailLabel = (TextView)dialog.findViewById(R.id.email_label);
                emailLabel.setTypeface(custom_font);
                TextView fbLabel = (TextView)dialog.findViewById(R.id.fb_label);
                fbLabel.setTypeface(custom_font);
                FloatingActionButton close = (FloatingActionButton)dialog.findViewById(R.id.close_contact_dialog);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
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
//class CustomListAdapter extends ArrayAdapter<String> {
//
//    private final Activity context;
//    private final String[] itemname;
//    private final Integer[] imgid;
//
//    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
//        super(context, R.layout.custom_drawer_item, itemname);
//        // TODO Auto-generated constructor stub
//
//        this.context=context;
//        this.itemname=itemname;
//        this.imgid=imgid;
//    }
//
//    public View getView(int position,View view,ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.custom_drawer_item, null,true);
//
//        final int pos = position;
//        //TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
//        imageView = (ImageView) rowView.findViewById(R.id.choices);
//        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
//
//        //txtTitle.setText(itemname[position]);
//        imageView.setImageResource(imgid[pos]);
//
//        //extratxt.setText("Description "+itemname[position]);
//        return rowView;
//
//    };
//}

class CustomListAdapter extends BaseAdapter {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private ImageView imageView;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imgid.length;
    }

    @Override
    public Object getItem(int index) {
        // TODO Auto-generated method stub
        return imgid[index];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_drawer_item, null,true);

        final int pos = position;
        //TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        imageView = (ImageView) rowView.findViewById(R.id.choices);
        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        //txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[pos]);
      //extratxt.setText("Description "+itemname[position]);
        return rowView;

    }
}