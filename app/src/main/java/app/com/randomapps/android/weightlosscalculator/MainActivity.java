package app.com.randomapps.android.weightlosscalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup collapsing toolbar, and... it doesn't work lol
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitleEnabled(false);


        //List of Calculators
        String[] calculatorList = { "BMI Calculator", "Calorie Calculator", "Body Fat Calculator",
                "BMR Calculator", "Anorexic BMI Calculator", "Ideal Weight Calculator" };


        //Set the ListView
        ListAdapter theAdapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.textView1, calculatorList);
        ListView theView = (ListView) findViewById(R.id.list_view1);
        theView.setAdapter(theAdapter);


        //Go to new activity when item is selected
        theView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, BMICalculator.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, CalorieCalculator.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, BodyFatCalculator.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, BMRCalculator.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, AnorexicBMICalculator.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, IdealWeightCalculator.class));
                        break;
                }

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
