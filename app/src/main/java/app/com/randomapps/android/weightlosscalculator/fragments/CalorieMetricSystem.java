package app.com.randomapps.android.weightlosscalculator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import app.com.randomapps.android.weightlosscalculator.R;

public class CalorieMetricSystem extends Fragment {

    public CalorieMetricSystem() {
        //Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.calorie_metric_fragment, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.calorie_metric_spinner);

        //createFromResource is a static method thus no need to instantiate a new object
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this.getActivity(), R.array.activity_list, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        return v;
    }
}
