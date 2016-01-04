package cravebot.survey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import cravebot.R;

public class Survey extends AppCompatActivity {
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_view_flipper);

        flipper = (ViewFlipper) findViewById(R.id.viewFlipperSurvey);
        flipper.setDisplayedChild(0);

        ImageButton submit = (ImageButton) findViewById(R.id.surveyButtonCode);

        ImageButton finish = (ImageButton) findViewById(R.id.surveyButtonSubmit);

        ImageButton back = (ImageButton) findViewById(R.id.surveyButtonThanks);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToSurvey();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flipper.setDisplayedChild(2);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void goToSurvey(){
        flipper.setDisplayedChild(1);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();

        Bitmap bitHeader = BitmapFactory
                .decodeResource(getResources(), R.drawable.question_header);

        Bitmap header = Bitmap.createScaledBitmap(bitHeader, width, 40, true);

        TextView question1 = (TextView) findViewById(R.id.question1);
        question1.setBackground(new BitmapDrawable(getResources(), header));

        TextView question2 = (TextView) findViewById(R.id.question2);
        question2.setBackground(new BitmapDrawable(getResources(), header));

        TextView question3 = (TextView) findViewById(R.id.question3);
        question3.setBackground(new BitmapDrawable(getResources(), header));

        TextView question4 = (TextView) findViewById(R.id.question4);
        question4.setBackground(new BitmapDrawable(getResources(), header));
    }
}
