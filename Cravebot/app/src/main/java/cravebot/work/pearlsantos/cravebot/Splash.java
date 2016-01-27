package cravebot.work.pearlsantos.cravebot;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import cravebot.R;

/**
 * Created by elysi on 12/31/2015.
 * This class shows a Splash Screen with the logo of Cravebot
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

        //Setting size of the CraveBot logo
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

        background.setImageURI(Uri.parse("res:/" + R.mipmap.background));
        cravebot.setImageURI(Uri.parse("res:/" + R.drawable.cravebot_start));


        //Show Splash Screen for a few seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new CheckingStart(getApplicationContext(), Splash.this).execute();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }

}
