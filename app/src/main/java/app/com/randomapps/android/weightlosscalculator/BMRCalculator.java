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

import app.com.randomapps.android.weightlosscalculator.fragments.BMRMetricSystem;
import app.com.randomapps.android.weightlosscalculator.fragments.BMRUsSystem;
import app.com.randomapps.android.weightlosscalculator.results.BMIResult;
import app.com.randomapps.android.weightlosscalculator.results.BMRResult;

public class BMRCalculator extends AppCompatActivity {

    private ViewPager viewpager;

    private EditText metricAgeID, metricHeightID, metricWeightID;
    private EditText usAgeID, usFeetID, usInchID, usWeightID;

    private String metricAge, metricHeight, metricWeight;
    private String usAge, usFeet, usInch, usWeight;

    private static int usGender, metricGender; //if 0 the male else if 1 the female

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmrcalculator);

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
        viewPagerAdapter.addFragment(new BMRUsSystem(), "US Units");
        viewPagerAdapter.addFragment(new BMRMetricSystem(), "Metric Units");
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
                    metricGender = 0;
                    break;

            case R.id.radioMetricFemale:
                if (checked)
                    metricGender = 1;
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
                    usGender = 0;
                    break;

            case R.id.radioUSFemale:
                if (checked)
                    usGender = 1;
                    break;
        }
    }

    //Action performed when result button on Metric unit is clicked
    public void onMetricResultButtonClicked(View view) {

        metricAgeID = (EditText) findViewById(R.id.bmr_metric_age_input);
        metricHeightID = (EditText) findViewById(R.id.bmr_metric_height_centimeters);
        metricWeightID = (EditText) findViewById(R.id.bmr_metric_weight);

        metricAge = metricAgeID.getText().toString();
        metricHeight = metricHeightID.getText().toString();
        metricWeight = metricWeightID.getText().toString();

        if( isEmptyWidgets(metricAge, metricHeight, metricWeight) ) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, bmr;
        double height, weight;

        age = Integer.parseInt(metricAge);
        height = Double.parseDouble(metricHeight);
        weight = Double.parseDouble(metricWeight);

        //To avoid divide-by-zero error
        if( (age == 0) || (height == 0.00) || (weight == 0.00)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        bmr = calculateBMR(age, height, weight);

        //Starts the new Activity and sends the result
        Intent intent = new Intent(getBaseContext(), BMRResult.class);
        intent.putExtra("bmr_answer", bmr);
        startActivity(intent);
    }

    //Action performed when the result button on US unit is clicked
    public void onUSResultButtonClick(View view) {

        usAgeID = (EditText) findViewById(R.id.bmr_us_age_input);
        usFeetID = (EditText) findViewById(R.id.bmr_us_height_feet);
        usInchID = (EditText) findViewById(R.id.bmr_us_height_inch);
        usWeightID = (EditText) findViewById(R.id.bmr_us_weight);

        usAge = usAgeID.getText().toString();
        usFeet = usFeetID.getText().toString();
        usInch = usInchID.getText().toString();
        usWeight = usWeightID.getText().toString();

        //To check if any widgets have been left empty
        if( isEmptyWidgets(usAge, usFeet, usInch, usWeight) ) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, feet, inch, bmr;
        double weight;

        age = Integer.parseInt(usAge);
        feet = Integer.parseInt(usFeet);
        inch = Integer.parseInt(usInch);
        weight = Double.parseDouble(usWeight);

        //To check for divide-by-zero error
        if( (age == 0) || (feet == 0) || (weight == 0.00)){
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        bmr = calculateBMR(age, feet, inch, weight);

        //Starts the new Activity and sends the result
        Intent intent = new Intent(getBaseContext(), BMRResult.class);
        intent.putExtra("bmr_answer", bmr);
        startActivity(intent);
    }


    //To check if any widgets have been left empty in Metric unit
    private boolean isEmptyWidgets(String age, String height, String weight) {

        if(age.trim().equals("")) {
            return true;
        }
        else if(height.trim().equals("")) {
            return true;
        }
        else if(weight.trim().equals("")) {
            return true;
        }
        else
            return false;
    }


    //To check if any widgets have been left empty in US unit
    private boolean isEmptyWidgets(String age, String feet, String inch, String weight) {

        if(age.trim().equals("")) {
            return true;
        }
        else if(feet.trim().equals("")) {
            return true;
        }
        else if(inch.trim().equals("")) {
            return true;
        }
        else if(weight.trim().equals("")) {
            return true;
        }
        else
            return false;
    }

    //Calculate BMR for Metric unit
    int calculateBMR(int age, double height, double weight) {

        int result;

        switch (metricGender) {

            case 0:
            default:
                result = (int)(10*weight + 6.25*height -5*age + 5);
                break;
            case 1 :
                result = (int)(10*weight + 6.25*height -5*age -161);
        }

        return result;
    }


    //Calculate BMR for US unit
    int calculateBMR(int age, int feet, int inch, double weight) {

        int result;
        double convertedHeight = (feet*12 + inch)*2.54;
        double convertedWeight = (weight / 2.2046);

        switch (usGender) {

            case 0:
            default:
                result = (int)(10*convertedWeight + 6.25*convertedHeight -5*age + 5);
                break;
            case 1 :
                result = (int)(10*convertedWeight + 6.25*convertedHeight -5*age -161);
        }

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
