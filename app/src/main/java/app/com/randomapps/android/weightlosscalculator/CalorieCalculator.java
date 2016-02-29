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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.com.randomapps.android.weightlosscalculator.fragments.CalorieMetricSystem;
import app.com.randomapps.android.weightlosscalculator.fragments.CalorieUsSystem;
import app.com.randomapps.android.weightlosscalculator.results.CalorieResult;


public class CalorieCalculator extends AppCompatActivity {

    private ViewPager viewpager;

    private Spinner usSpinnerID, metricSpinnerID;

    private EditText usAgeID, usFeetID, usInchID, usPoundID;
    private String usAge, usFeet, usInch, usPound;

    private EditText metricAgeID, metricHeightID, metricWeightID;
    private String metricAge, metricHeight, metricWeight;

    private static int usGender, metricGender; //O means male and 1 means female

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

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
        viewPagerAdapter.addFragment(new CalorieUsSystem(), "US Units");
        viewPagerAdapter.addFragment(new CalorieMetricSystem(), "Metric Units");
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

    //Action performed when the radio button on the US unit is selected
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

    //Action performed when the radio button on Metric unit is selected
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

    //Action performed when the result button on the Metric unit is clicked
    public void onMetricResultButtonClicked(View view) {

        metricAgeID = (EditText) findViewById(R.id.calorie_metric_age);
        metricHeightID = (EditText) findViewById(R.id.calorie_metric_height_centimeters);
        metricWeightID = (EditText) findViewById(R.id.calorie_metric_weight);
        metricSpinnerID = (Spinner) findViewById(R.id.calorie_metric_spinner);

        metricAge = metricAgeID.getText().toString();
        metricHeight = metricHeightID.getText().toString();
        metricWeight = metricWeightID.getText().toString();

        //To check if any widgets have been left empty
        if( ( isEmptyWidgets(metricAge, metricHeight, metricWeight) ) || ( metricSpinnerID.getSelectedItemPosition() <= 0)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, spinnerItem;
        double height, weight;
        int[] calorieRange;

        age = Integer.parseInt(metricAge);
        height = Double.parseDouble(metricHeight);
        weight = Double.parseDouble(metricWeight);
        spinnerItem = metricSpinnerID.getSelectedItemPosition();

        //To avoid divide-by-zero error
        if( (age == 0) || (height == 0.00) || (weight == 0.00)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        calorieRange = calculateCalorie(age, height, weight, spinnerItem);

        //Starts the activity and passes the result
        Intent intent = new Intent(getBaseContext(), CalorieResult.class);
        intent.putExtra("calorie_answer", calorieRange);
        intent.putExtra("calorie_unit", "Metric");
        startActivity(intent);
    }


    //Action performed when the result button on the US unit is clicked
    public void onUsResultButtonClicked(View view) {

        usAgeID = (EditText) findViewById(R.id.calorie_us_age);
        usFeetID = (EditText) findViewById(R.id.calorie_us_height_feet);
        usInchID = (EditText) findViewById(R.id.calorie_us_height_inch);
        usPoundID = (EditText) findViewById(R.id.calorie_us_weight);
        usSpinnerID = (Spinner) findViewById(R.id.calorie_us_spinner);

        usAge = usAgeID.getText().toString();
        usFeet = usFeetID.getText().toString();
        usInch = usInchID.getText().toString();
        usPound = usPoundID.getText().toString();

        //To check if any widgets is empty
        if( isEmptyWidgets(usAge, usFeet, usInch, usPound) || (usSpinnerID.getSelectedItemPosition() <= 0)) {
           Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, spinnerItem, feet, inch;
        double pound;
        int[] calorieRange;

        age = Integer.parseInt(usAge);
        feet = Integer.parseInt(usFeet);
        inch = Integer.parseInt(usInch);
        pound = Double.parseDouble(usPound);
        spinnerItem = usSpinnerID.getSelectedItemPosition();

        //To avoid divide-by-zero  error
        if( (age == 0) || (feet == 0) || (inch == 0) || (pound == 0.00)) {
            Toast.makeText(this, "Enter correct data", Toast.LENGTH_SHORT).show();
            return;
        }

        calorieRange = calculateCalorie(age, feet, inch, pound, spinnerItem);

        //Starts the activity and passes the result
        Intent intent = new Intent(getBaseContext(), CalorieResult.class);
        intent.putExtra("calorie_answer", calorieRange);
        intent.putExtra("calorie_unit", "US");
        startActivity(intent);
    }



    //To check whether any widgets have been left empty Metric unit
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

    //To check whether any widgets have been left empty in US unit
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

    //Calculate the range of calories of Metric unit
    private int[] calculateCalorie(int age, double height, double weight, int item) {

        int[] result = new int[5];
        int bmr;

        switch (metricGender) {
            case 0:
            default:
                bmr = (int) (10 * weight + 6.25 * height - 5 * age + 5);
                break;
            case 1:
                bmr = (int)(10*weight + 6.25*height -5*age -161);
                break;

        }

        switch (item) {

            case 1:
                bmr = (int)(bmr*1.2);
                break;
            case 2:
                bmr = (int)(bmr*1.375);
                break;
            case 3:
                bmr = (int)(bmr*1.55);
                break;
            case 4:
                bmr = (int)(bmr*1.725);
                break;
            case 5:
                bmr = (int)(bmr*1.9);
                break;
        }

        result[0] = bmr;
        result[1] = bmr - 500;
        result[2] = bmr - 1000;
        result[3] = bmr + 500;
        result[4] = bmr + 1000;

        return result;
    }

    //Calculate the range of calories of Metric unit
    private int[] calculateCalorie(int age, int feet, int inch, double weight, int item) {

        int[] result = new int[5];
        int bmr;
        double convertedHeight = (feet*12 + inch)*2.54;
        double convertedWeight = (weight / 2.2046);

        switch(usGender) {
            case 0:
            default:
                bmr = (int)(10*convertedWeight + 6.25*convertedHeight -5*age + 5);
                break;
            case 1 :
                bmr = (int)(10*convertedWeight + 6.25*convertedHeight -5*age -161);
        }


        switch (item) {

            case 1:
                bmr = (int)(bmr*1.2);
                break;
            case 2:
                bmr = (int)(bmr*1.375);
                break;
            case 3:
                bmr = (int)(bmr*1.55);
                break;
            case 4:
                bmr = (int)(bmr*1.725);
                break;
            case 5:
                bmr = (int)(bmr*1.9);
                break;
        }

        result[0] = bmr;
        result[1] = bmr - 500;
        result[2] = bmr - 1000;
        result[3] = bmr + 500;
        result[4] = bmr + 1000;

        return result;
    }


    //Used to display the result in a dialog box
    private void displayResult(int[] answer, String fragmentName) {

        String result = new String();

        switch (fragmentName) {
            case "Metric" :
                result = String.format("You need %d calories/day to maintain your weight%n"+
                        "You need %d calories/day to lose 0.5 kg/week%n"+
                        "You need %d calories/day to lose 1 kg/week%n"+
                        "You need %d calories/day to gain 0.5 kg/week%n"+
                        "You need %d calories/day to gain 1 kg/week%n", answer[0], answer[1], answer[2], answer[3], answer[4]);
                break;
            case "US" :
                 result = String.format("You need %d calories/day to maintain your weight%n"+
                        "You need %d calories/day to lose 1 lbs/week%n"+
                        "You need %d calories/day to lose 2 lbs/week%n"+
                        "You need %d calories/day to gain 1 lbs/week%n"+
                        "You need %d calories/day to gain 2 lbs/week", answer[0], answer[1], answer[2], answer[3], answer[4]);
                        break;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Result");
        alertDialog.setMessage(result);

        //OK button to close the dialog
        alertDialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();


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
