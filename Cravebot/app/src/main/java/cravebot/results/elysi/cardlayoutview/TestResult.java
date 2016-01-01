package cravebot.results.elysi.cardlayoutview;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cravebot.R;

public class TestResult extends Activity {
    TextView text;
    ArrayList<FoodItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        text = (TextView) findViewById(R.id.textViewtester);

        //Do this to get the arraylist in CardLayout.
        Intent i = getIntent();
        list = i.getParcelableArrayListExtra(GoTask.LIST_KEY);

        String s = "";

        for(FoodItem food: list){
            s += food.getItemName() + "\n";
            s += food.getDescription() + "\n";
            s += food.getPrice() + "\n";
            s += food.getPhoto() + "\n";
            s += food.getOption1() + "\n";
            s += food.getPrice1() + "\n";
            s += food.getOption2() + "\n";
            s += food.getPrice2() + "\n";
            s += food.getOption3() + "\n";
            s += food.getPrice3() + "\n";
            s += food.getOption4() + "\n";
            s += food.getPrice4() + "\n";
            s += food.getOption5() + "\n";
            s += food.getPrice5() + "\n";
            s += food.getOption6() + "\n";
            s += food.getPrice6() + "\n";
            s += food.getRestoLogo() + "\n";
            s += food.getRestoName() + "\n";
        }
        text.setText(s);
    }
}
