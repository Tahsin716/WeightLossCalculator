package app.com.randomapps.android.weightlosscalculator.results;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.com.randomapps.android.weightlosscalculator.R;

public class BMRResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmrresult);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        int value = extras.getInt("bmr_answer");
        String answer = String.format("%d",value);

        TextView textView2 = (TextView) findViewById(R.id.bmr_result_textView2);
        textView2.setText(answer);
        textView2.setTextColor(Color.parseColor("#00b200"));
    }

    //Closes the activity
    public void closeActivity(View view) {
        this.finish();
    }
}
