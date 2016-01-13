package cravebot.results.elysi.cardlayoutview;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cravebot.R;
import cravebot.results.elysi.customobjects.FoodItem;
import cravebot.work.pearlsantos.cravebot.GoTask;

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
            s += food.getOptions(1) + "\n";
            s += food.getPrices(1) + "\n";
            s += food.getOptions(2) + "\n";
            s += food.getPrices(2) + "\n";
            s += food.getOptions(3) + "\n";
            s += food.getPrices(3) + "\n";
            s += food.getOptions(4) + "\n";
            s += food.getPrices(4) + "\n";
            s += food.getOptions(5) + "\n";
            s += food.getPrices(5) + "\n";
            s += food.getOptions(6) + "\n";
            s += food.getPrices(6) + "\n";
            s += food.getRestoLogo() + "\n";
            s += food.getRestoName() + "\n";
        }
        text.setText(s);
    }
}
