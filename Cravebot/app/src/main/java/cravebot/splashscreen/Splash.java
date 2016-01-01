package cravebot.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import cravebot.R;
import cravebot.customstuff.LoadingImages;
import cravebot.results.elysi.results.SectionsPagerAdapter;
import cravebot.work.pearlsantos.cravebot.MainActivity;

/**
 * Created by elysi on 12/31/2015.
 */

public class Splash extends AppCompatActivity{
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private ImageView background, cravebot;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splashscreen);

        background = (ImageView) findViewById(R.id.background);
        cravebot = (ImageView) findViewById(R.id.cravebot);
        final FrameLayout splashLayout = (FrameLayout) findViewById(R.id.splashLayout);

        splashLayout.getViewTreeObserver().
                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        //now we can retrieve the width and height
                        int height = splashLayout.getHeight();

                        double heightSize = height * 0.4;
                        height = (int) heightSize;


                        cravebot.setLayoutParams(new FrameLayout.LayoutParams(height, height, Gravity.CENTER));


                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                            splashLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        else
                            splashLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        LoadingImages loadingImages = new LoadingImages(getApplicationContext());
        loadingImages.loadBitmapFromRes(R.drawable.wot, background, 150, 150);
        loadingImages.loadBitmapFromRes(R.drawable.cravebot_start, cravebot, 100, 100);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.overridePendingTransition(R.anim.pull_in_right,
                        R.anim.push_out_left);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.splashLayout));
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
        cravebot.setImageBitmap(null);
        background.setImageBitmap(null);
    }

}
