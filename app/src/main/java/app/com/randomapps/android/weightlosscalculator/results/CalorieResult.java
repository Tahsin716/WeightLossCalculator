package app.com.randomapps.android.weightlosscalculator.results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.com.randomapps.android.weightlosscalculator.R;

public class CalorieResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        int[] values = extras.getIntArray("calorie_answer");
        String unit = extras.getString("calorie_unit");
        String[] result = new String[5];
        String[] text = new String[5];

        result[0] = String.format("%d Calories/day", values[0]);
        result[1] = String.format("%d Calories/day", values[1]);
        result[2] = String.format("%d Calories/day", values[2]);
        result[3] = String.format("%d Calories/day", values[3]);
        result[4] = String.format("%d Calories/day", values[4]);

        switch (unit) {
            case "Metric":
                text[0] = "To maintain your weight you need ";
                text[1] = "To lose 0.5 kg/week you need ";
                text[2] = "To lose 1 kg/week you need ";
                text[3] = "To gain 0.5 kg/week you need ";
                text[4] = "To gain 1 kg/week you need ";
                break;
            case "US":
                text[0] = "To maintain your weight you need ";
                text[1] = "To lose 1 lbs/week you need ";
                text[2] = "To lose 2 lbs/week you need ";
                text[3] = "To gain 1 lbs/week you need ";
                text[4] = "To gain 2 lbs/week you need ";
        }

        //Set the text
        TextView text0 = (TextView) findViewById(R.id.calorie_result_text_0);
        text0.setText(text[0]);

        TextView text1 = (TextView) findViewById(R.id.calorie_result_text_1);
        text1.setText(text[1]);

        TextView text2 = (TextView) findViewById(R.id.calorie_result_text_2);
        text2.setText(text[2]);

        TextView text3 = (TextView) findViewById(R.id.calorie_result_text_3);
        text3.setText(text[3]);

        TextView text4 = (TextView) findViewById(R.id.calorie_result_text_4);
        text4.setText(text[4]);


        //Set the results
        TextView result0 = (TextView) findViewById(R.id.calorie_result_0);
        result0.setText(result[0]);

        TextView result1 = (TextView) findViewById(R.id.calorie_result_1);
        result1.setText(result[1]);

        TextView result2 = (TextView) findViewById(R.id.calorie_result_2);
        result2.setText(result[2]);

        TextView result3 = (TextView) findViewById(R.id.calorie_result_3);
        result3.setText(result[3]);

        TextView result4 = (TextView) findViewById(R.id.calorie_result_4);
        result4.setText(result[4]);
    }

    //Close this activity
    public void closeActivity(View view) {
        this.finish();
    }
}
