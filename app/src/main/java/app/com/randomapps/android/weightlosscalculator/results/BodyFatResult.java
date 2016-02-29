package app.com.randomapps.android.weightlosscalculator.results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.com.randomapps.android.weightlosscalculator.R;

public class BodyFatResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get the result
        Bundle extras = getIntent().getExtras();
        double value = extras.getDouble("body_fat_result");
        String result = String.format("%.1f%%", value);

        TextView textView2 = (TextView) findViewById(R.id.body_fat_result_textView2);
        textView2.setText(result);
    }

    //Closes the Activity
    public void closeActivity(View view) {
        this.finish();
    }
}
