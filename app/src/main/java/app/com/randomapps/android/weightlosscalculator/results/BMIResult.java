package app.com.randomapps.android.weightlosscalculator.results;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.com.randomapps.android.weightlosscalculator.R;

public class BMIResult extends AppCompatActivity {

    private Color color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Gets the data passed by from the previous activity
        Bundle extras = getIntent().getExtras();
        double value = extras.getDouble("bmi_answer");
        String answer = String.format("%.2f", value);

        String condition;
        String color;

        if(value >= 18.5 && value <= 25) {
            condition = "Normal";
            color = "#00b200";
        }
        else if(value > 25 && value <= 30) {
            condition = "Overweight";
            color = "#ff8100";
        }
        else if(value < 18.5) {
            condition = "Underweight";
            color = "#f0c307";
        }
        else {
            condition = "Obese";
            color = "#f01e07";
        }

        TextView textView1 = (TextView) findViewById(R.id.bmi_result_textView1);
        textView1.setText("Your Body Mass Index is " + answer);
        textView1.setTextSize(35);

        TextView textView2 = (TextView) findViewById(R.id.bmi_result_textView2);
        textView2.setText(condition);
        textView2.setTextSize(30);
        textView2.setTextColor(Color.parseColor(color));
    }

    //Closes activity on button click
    public void closeActivity(View view) {
        this.finish();
    }
}
