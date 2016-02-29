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

import app.com.randomapps.android.weightlosscalculator.fragments.AnorexicMetricSystem;
import app.com.randomapps.android.weightlosscalculator.fragments.AnorexicUsSystem;
import app.com.randomapps.android.weightlosscalculator.results.AnorexicResult;
import app.com.randomapps.android.weightlosscalculator.results.BMIResult;

public class AnorexicBMICalculator extends AppCompatActivity {

    private ViewPager viewpager;

    private EditText usAgeID, usFeetID, usInchID, usPoundsID;
    private EditText metricAgeID, metricHeightID, metricWeightID;

    private String metricAge, metricHeight, metricWeight;
    private String usAge, usFeet, usInch, usPounds;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anorexic_bmicalculator);

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
        viewPagerAdapter.addFragment(new AnorexicUsSystem(), "US Units");
        viewPagerAdapter.addFragment(new AnorexicMetricSystem(), "Metric Units");
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
                    break;

            case R.id.radioMetricFemale:
                if (checked)
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
                    break;

            case R.id.radioUSFemale:
                if (checked)
                    break;
        }
    }

    //Action performed when the result button on the Metric button is clicked
    public void onMetricResultButtonClicked(View view) {

        metricAgeID = (EditText) findViewById(R.id.anorexic_metric_age_input);
        metricHeightID = (EditText) findViewById(R.id.anorexic_metric_height_centimeters);
        metricWeightID = (EditText) findViewById(R.id.anorexic_metric_weight);

        metricAge = metricAgeID.getText().toString();
        metricHeight = metricHeightID.getText().toString();
        metricWeight = metricWeightID.getText().toString();

        //To check if any EditText fields have been left empty
        if( isEmptyWidgets(metricAge, metricHeight, metricWeight)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        double height, weight , bmi;

        age = Integer.parseInt(metricAge);
        height = Double.parseDouble(metricHeight);
        weight = Double.parseDouble(metricWeight);

        //To avoid divide-by-zero error
        if( (age == 0) || (height == 0.00) || (weight == 0.00)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        //Formula to calculate bmi for metric unit
        bmi = weight / ( (height*height)/10000 );

        //Starts the new Activity and sends the result
        Intent intent = new Intent(getBaseContext(), AnorexicResult.class);
        intent.putExtra("anorexic_bmi_answer", bmi);
        startActivity(intent);
    }


    //Action performed when the result button on the US button is clicked
    public void onUSResultButtonClick(View view) {

        usAgeID = (EditText) findViewById(R.id.anorexic_us_age_input);
        usFeetID = (EditText) findViewById(R.id.anorexic_us_height_feet);
        usInchID = (EditText) findViewById(R.id.anorexic_us_height_inch);
        usPoundsID = (EditText) findViewById(R.id.anorexic_us_weight);

        usAge = usAgeID.getText().toString();
        usFeet = usFeetID.getText().toString();
        usInch = usInchID.getText().toString();
        usPounds = usPoundsID.getText().toString();

        //To check if any EditText fields have been left empty
        if( isEmptyWidgets(usAge, usFeet, usInch, usPounds) ) {

            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, feet, inch;
        double bmi,  pounds;

        age = Integer.parseInt(usAge);
        feet = Integer.parseInt(usFeet);
        inch = Integer.parseInt(usInch);
        pounds = Double.parseDouble(usPounds);

        //To avoid divide-by-zero error
        if( (age == 0) || (feet == 0) || (pounds == 0.00) ) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        //formula to calculate bmi in US units
        bmi = 703 *( pounds / (double)((feet*12+inch)*(feet*12+inch)) );

        //Starts the new Activity and sends the result
        Intent intent = new Intent(getBaseContext(), AnorexicResult.class);
        intent.putExtra("anorexic_bmi_answer", bmi);
        startActivity(intent);
    }


    //Checks if any EditText widgets have been left empty for us unit
    private boolean isEmptyWidgets(String age, String feet, String inch, String pounds) {

        if(age.trim().equals("")) {
            return true;
        }
        else if(feet.trim().equals("")) {
            return true;
        }
        else if(inch.trim().equals("")) {
            return true;
        }
        else if(pounds.trim().equals("")) {
            return true;
        }

        return false;
    }

    //Checks if any EditText widgets have been left empty for metric unit
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

        return false;
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
