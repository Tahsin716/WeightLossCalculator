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

public class AnorexicResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anorexic_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Gets the data passed by from the previous activity
        Bundle extras = getIntent().getExtras();
        double value = extras.getDouble("anorexic_bmi_answer");
        String answer = String.format("%.2f", value);

        String text;
        String color;

        if(value < 18.5) {
            text = "You bmi suggests an anorexic bmi";
            color = "#f01e07";
        }
        else {
            text = "Your bmi does not suggest an anorexic bmi";
            color = "#00b200";
        }

        TextView textView1 = (TextView) findViewById(R.id.anorexic_result_textView1);
        textView1.setText("Your BMI is " + answer);
        textView1.setTextSize(30);

        TextView textView2 = (TextView) findViewById(R.id.anorexic_result_textView2);
        textView2.setText(text);
        textView2.setTextSize(25);
        textView2.setTextColor(Color.parseColor(color));

    }

    //Closes the activity on button click
    public void closeActivity(View view) {
        this.finish();
    }
}
