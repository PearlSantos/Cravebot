package cravebot.work.pearlsantos.cravebot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import cravebot.R;
import cravebot.customstuff.LoadingImages;

/**
 * Created by elysi on 12/31/2015.
 */

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private SimpleDraweeView background, cravebot;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_splashscreen);

        background = (SimpleDraweeView) findViewById(R.id.background);
        cravebot = (SimpleDraweeView) findViewById(R.id.cravebot);
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

        background.setImageURI(Uri.parse("res:/" + R.drawable.food_bg));
        cravebot.setImageURI(Uri.parse("res:/" + R.drawable.cravebot_start));



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(Splash.this, CheckingStart.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.overridePendingTransition(R.anim.pull_in_right,
//                        R.anim.push_out_left);
//                Splash.this.finish();
                new CheckingStart(getApplicationContext(), Splash.this).execute();
            }
        }, SPLASH_DISPLAY_LENGTH);



        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(Splash.this, CheckingStart.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.overridePendingTransition(R.anim.pull_in_right,
//                        R.anim.push_out_left);
//                Splash.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
    }

}
