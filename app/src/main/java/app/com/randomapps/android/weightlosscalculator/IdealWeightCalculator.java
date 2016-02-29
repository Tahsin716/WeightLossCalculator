package app.com.randomapps.android.weightlosscalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.com.randomapps.android.weightlosscalculator.fragments.IdealWeightMetricSystem;
import app.com.randomapps.android.weightlosscalculator.fragments.IdealWeightUsSystem;
import app.com.randomapps.android.weightlosscalculator.results.IdealWeightResult;

public class IdealWeightCalculator extends AppCompatActivity {

    private ViewPager viewpager;

    private EditText metricAgeID, metricHeightID;
    private EditText usAgeID, usFeetID, usInchID;

    private String metricAge, metricHeight;
    private String usAge, usFeet, usInch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_weight_calculator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager);
    }


    //Set up view pager for TabLayout
    public void setupViewPager(ViewPager viewpager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new IdealWeightUsSystem(), "US Units");
        viewPagerAdapter.addFragment(new IdealWeightMetricSystem(), "Metric Units");
        viewpager.setAdapter(viewPagerAdapter);
    }



    //Using FragmentPagerAdapter to set up the fragments
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }


    //Action performed when radio button of Metric unit is clicked
    public void onMetricRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioMetricMale:
                if (checked)
                    break;

            case R.id.radioMetricFemale:
                if (checked)
                    break;
        }
    }


    //Action performed when radio button of US unit is clicked
    public void onUSRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioUSMale:
                if (checked)
                    break;

            case R.id.radioUSFemale:
                if (checked)
                    break;
        }
    }


    //Action performed when the result button on the Metric unit is clicked
    public void onMetricResultButtonClick(View view) {

        metricAgeID = (EditText) findViewById(R.id.ideal_metric_age_input);
        metricHeightID = (EditText) findViewById(R.id.ideal_height_input);

        metricAge = metricAgeID.getText().toString();
        metricHeight = metricHeightID.getText().toString();

        //To check if any widget has been left empty
        if( isEmptyWidgets(metricAge, metricHeight)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        double height;
        double[] idealWeight;

        age = Integer.parseInt(metricAge);
        height = Double.parseDouble(metricHeight);

        //To avoid divide by zero error
        if( (age == 0) || (height == 0.00) ) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        idealWeight = calculateIdealWeight(age, height);

        //Starts the activity and passes the result
        Intent intent = new Intent(getBaseContext(), IdealWeightResult.class);
        intent.putExtra("ideal_weight_answer", idealWeight);
        intent.putExtra("ideal_weight_unit", "Metric");
        startActivity(intent);
    }


    //Action performed when the result button on the US unit is clicked
    public void onUSResultButtonClick(View view) {

        usAgeID = (EditText) findViewById(R.id.ideal_us_age_input);
        usFeetID = (EditText) findViewById(R.id.ideal_us_height_feet);
        usInchID = (EditText) findViewById(R.id.ideal_us_height_inch);

        usAge = usAgeID.getText().toString();
        usFeet = usFeetID.getText().toString();
        usInch = usInchID.getText().toString();

        //To check if any widgets have been left empty
        if( isEmptyWidgets(usAge, usFeet, usInch)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, feet, inch;
        double[] idealWeight;

        age = Integer.parseInt(usAge);
        feet = Integer.parseInt(usFeet);
        inch = Integer.parseInt(usInch);

        //To avoid divide-by-zero error
        if( (age == 0) || (feet == 0) ) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        idealWeight = calculateIdealWeight(age, feet, inch);

        //Starts the activity and passes the result
        Intent intent = new Intent(getBaseContext(), IdealWeightResult.class);
        intent.putExtra("ideal_weight_answer", idealWeight);
        intent.putExtra("ideal_weight_unit", "US");
        startActivity(intent);
    }


    //To check if any widgets have been left empty in Metric unit
    private boolean isEmptyWidgets(String age, String height) {

        if(age.trim().equals("")){
            return true;
        }
        else if(height.trim().equals("")) {
            return true;
        }
        else
            return false;
    }

    //To check if any widgets have been left empty in US unit
    private boolean isEmptyWidgets(String age, String feet, String inch) {

        if(age.trim().equals("")) {
            return true;
        }
        else if(feet.trim().equals("")) {
            return true;
        }
        else if(inch.trim().equals("")) {
            return true;
        }
        else
            return false;
    }

    //Calculate ideal weight for metric unit
    private double[] calculateIdealWeight(int age, double height) {

        double result[] = new double[2];

        //minimum ideal weight
        result[0] = 18.5 * ( (height*height) / 10000 );

        //maximum ideal weight
        result[1] = 24.9 * ( (height*height) / 10000 );

        return result;
    }


    //Calculate ideal weight for us unit
    private double[] calculateIdealWeight(int age, int feet, int inch) {

        double[] result = new double[2];

        //minimum ideal weight
        result[0] = (18.5 * (double)( (feet*12 + inch)*(feet*12 +inch) ) ) / 703;

        //maximum ideal weight
        result[1] = (24.9 * (double)( (feet*12 + inch)*(feet*12 +inch) ) ) / 703;

        return result;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if( id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}