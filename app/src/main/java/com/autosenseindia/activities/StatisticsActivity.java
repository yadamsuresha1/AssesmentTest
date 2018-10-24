package com.autosenseindia.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.autosenseindia.R;
import com.autosenseindia.models.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private ArrayList<User> usersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);

        usersList = (ArrayList<User>) getIntent().getSerializableExtra("users");

        createBarChart();
    }

    private void createBarChart() {
        //create bar entires...
        ArrayList<BarEntry> entries = new ArrayList<>();

        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            entries.add(new BarEntry(i, Float.valueOf(usersList.get(i).getSalary().replace("$", "").replace(",", ""))));
            labels.add(usersList.get(i).getName());
        }

        BarChart chart = findViewById(R.id.salariesChart);

        BarDataSet dataset = new BarDataSet(entries, "Salaries");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        chart.setData(new BarData(dataset));

        Description description = new Description();
        description.setText(getString(R.string.char_description));
        chart.setDescription(description);
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }
}
