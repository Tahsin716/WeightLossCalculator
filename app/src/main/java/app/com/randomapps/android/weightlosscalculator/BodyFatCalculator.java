package app.com.randomapps.android.weightlosscalculator;

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

import app.com.randomapps.android.weightlosscalculator.fragments.BodyFatMetricSystem;
import app.com.randomapps.android.weightlosscalculator.fragments.BodyFatUsSystem;
import app.com.randomapps.android.weightlosscalculator.results.BodyFatResult;


public class BodyFatCalculator extends AppCompatActivity {

    private ViewPager viewpager;

    private EditText usHeightFeetID, usHeightInchID, usNeckFeetID, usNeckInchID, usWaistFeetID, usWaistInchID,
                     usHipFeetID, usHipInchID;

    private EditText metricHeightID, metricNeckID, metricWaistID, metricHipID;

    private String usHeightFeet, usHeightInch, usNeckFeet, usNeckInch, usWaistFeet, usWaistInch, usHipFeet, usHipInch;
    private String metricHeight, metricNeck, metricWaist, metricHip;

    private static int usGender, metricGender; // 0 means male and 1 means female

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_calculator);

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
        viewPagerAdapter.addFragment(new BodyFatUsSystem(), "US Units");
        viewPagerAdapter.addFragment(new BodyFatMetricSystem(), "Metric Units");
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


    //Selecting the gender in the radio button on Metric Unit
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

    //Selecting the gender in the radio button on US Unit
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

    //Action performed when the result button on the US unit is clicked
    public void onUsResultButtonClicked(View view) {

        usHeightFeetID = (EditText) findViewById(R.id.body_fat_us_height_feet);
        usHeightInchID = (EditText) findViewById(R.id.body_fat_us_height_inch);
        usNeckFeetID = (EditText) findViewById(R.id.body_fat_us_neck_feet);
        usNeckInchID = (EditText) findViewById(R.id.body_fat_us_neck_inch);
        usWaistFeetID = (EditText) findViewById(R.id.body_fat_us_waist_feet);
        usWaistInchID = (EditText) findViewById(R.id.body_fat_us_waist_inch);
        usHipFeetID = (EditText) findViewById(R.id.body_fat_us_hip_feet);
        usHipInchID = (EditText) findViewById(R.id.body_fat_us_hip_inch);

        usHeightFeet = usHeightFeetID.getText().toString();
        usHeightInch = usHeightInchID.getText().toString();
        usNeckFeet = usNeckFeetID.getText().toString();
        usNeckInch = usNeckInchID.getText().toString();
        usWaistFeet = usWaistFeetID.getText().toString();
        usWaistInch = usWaistInchID.getText().toString();
        usHipFeet = usHipFeetID.getText().toString();
        usHipInch = usHipInchID.getText().toString();

        //To check if any widgets have been left empty
        if( isEmptyWidgets(usHeightFeet, usHeightInch, usNeckFeet, usNeckInch, usWaistFeet, usWaistInch, usHipFeet, usHipInch)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        double bodyFat,heightFeet, heightInch, neckFeet, neckInch, waistFeet, waistInch, hipFeet, hipInch;

        heightFeet = Double.parseDouble(usHeightFeet);
        heightInch = Double.parseDouble(usHeightInch);
        neckFeet = Double.parseDouble(usNeckFeet);
        neckInch = Double.parseDouble(usNeckInch);
        waistFeet = Double.parseDouble(usWaistFeet);
        waistInch = Double.parseDouble(usWaistInch);
        hipFeet = Double.parseDouble(usHipFeet);
        hipInch = Double.parseDouble(usHipInch);

        //To avoid divide-by-zero error
        if( (heightFeet == 0.00) || (neckFeet == 0.00) || (waistFeet == 0.00) || (hipFeet == 0.00)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        double convertedHeight = (heightFeet*12 + heightInch)*2.54;
        double convertedNeck = (neckFeet*12 + neckInch)*2.54;
        double convertedWaist = (waistFeet*12 + waistInch)*2.54;
        double convertedHip = (hipFeet*12 + hipInch)*2.54;

        bodyFat = calculateBodyFat(convertedHeight, convertedNeck, convertedWaist, convertedHip, usGender);

        //Starts the new Activity and passes the result
        Intent intent = new Intent(getBaseContext(), BodyFatResult.class);
        intent.putExtra("body_fat_result", bodyFat);
        startActivity(intent);
    }

    //Action performed when the result button on the Metric unit is clicked
    public void onMetricResultButtonClicked(View view) {

        metricHeightID = (EditText) findViewById(R.id.body_fat_metric_height);
        metricNeckID = (EditText) findViewById(R.id.body_fat_metric_neck);
        metricWaistID = (EditText) findViewById(R.id.body_fat_metric_waist);
        metricHipID = (EditText) findViewById(R.id.body_fat_metric_hip);

        metricHeight = metricHeightID.getText().toString();
        metricNeck = metricNeckID.getText().toString();
        metricWaist = metricWaistID.getText().toString();
        metricHip = metricHipID.getText().toString();

        //To check if any widgets have been left empty
        if( isEmptyWidgets(metricHeight, metricNeck, metricWaist, metricHip)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        double height, neck, waist, hip, bodyFat;

        height = Double.parseDouble(metricHeight);
        neck = Double.parseDouble(metricNeck);
        waist = Double.parseDouble(metricWaist);
        hip = Double.parseDouble(metricHip);

        //To avoid divide-by-zero error
        if( (height == 0.00) || (neck == 0.00) || (waist == 0.00) || (hip == 0.00)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        bodyFat = calculateBodyFat(height, neck, waist, hip, metricGender);

        //Starts the new Activity and passes the result
        Intent intent = new Intent(getBaseContext(), BodyFatResult.class);
        intent.putExtra("body_fat_result", bodyFat);
        startActivity(intent);
    }


    //To check if any widgets have been left empty in US unit
    private boolean isEmptyWidgets(String heightFeet, String heightInch, String neckFeet, String neckInch,
                                   String waistFeet, String waistInch, String hipFeet, String hipInch) {

        if(heightFeet.trim().equals("")) {
            return true;
        }
        else if(heightInch.trim().equals("")) {
            return true;
        }
        else if(neckFeet.trim().equals("")) {
            return true;
        }
        else if(neckInch.trim().equals("")) {
            return true;
        }
        else if(waistFeet.trim().equals("")) {
            return true;
        }
        else if(waistInch.trim().equals("")) {
            return true;
        }
        else if(hipFeet.trim().equals("")) {
            return true;
        }
        else if(hipInch.trim().equals("")) {
            return true;
        }
        else
            return false;
    }


    //To check if any widgets have been left empty in Metric unit
    private boolean isEmptyWidgets(String height, String neck, String waist, String hip) {

        if(height.trim().equals("")) {
            return true;
        }
        else if(neck.trim().equals("")) {
            return true;
        }
        else if(waist.trim().equals("")) {
            return true;
        }
        else if(hip.trim().equals("")) {
            return true;
        }
        else
            return false;
    }


    //Calculate body fat
    private double calculateBodyFat(double height, double neck, double waist, double hip, int gender) {

        double result;

        switch (gender) {
            case 0:
            default:
                result = (495/(1.0324-0.19077*(Math.log10(waist-neck))+ 0.15456*(Math.log10(height))))-450;
                break;
            case 1:
                result = (495/(1.29579-0.35004*(Math.log10(waist+hip-neck))+ 0.22100*(Math.log10(height))))-450;

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
