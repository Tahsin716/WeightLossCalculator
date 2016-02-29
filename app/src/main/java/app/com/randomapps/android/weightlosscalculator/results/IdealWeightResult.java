package app.com.randomapps.android.weightlosscalculator.results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.com.randomapps.android.weightlosscalculator.R;

public class IdealWeightResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_weight_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        double[] values = extras.getDoubleArray("ideal_weight_answer");
        String unit = extras.getString("ideal_weight_unit");
        String result;

        switch (unit) {
            case "Metric":
                result = String.format("%.1f kg - %.1f kg",values[0], values[1]);
                break;
            case "US":
            default:
                result = String.format("%.1f lbs - %.1f lbs",values[0], values[1]);
        }

        TextView textView2 = (TextView) findViewById(R.id.ideal_weight_result_textView2);
        textView2.setText(result);
    }

    //Close this activity
    public void closeActivity(View view) {
        this.finish();
    }
}
