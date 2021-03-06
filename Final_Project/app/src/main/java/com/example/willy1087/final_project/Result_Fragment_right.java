package com.example.willy1087.final_project;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class Result_Fragment_right extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment_right, container, false);

        double TOTAL = 0.0;
        double TRANSPORTATION = 0.0;
        double EDUCATION = 0.0;
        double MISCELLENOUS = 0.0;
        double ENTERTAINMENT = 0.0;
        double FOOD = 0.0;
        // Ints to hold the percentages
        double transPercent = 0;
        double eduPercent = 0;
        double entPercent = 0;
        double misPercent = 0;
        double foodPercent = 0;

        // Testing writing to a file
        String jsonData = "";
        jsonData = loadJSONFromAsset();

        if (!(jsonData == null)) {


            // Create a JSON object to parse the data
            try {
                // Create a big JSON object for the whole file
                JSONObject obj = new JSONObject(jsonData);
                // System.out.println(obj);
                // Grab the array called input
                JSONArray JSON_array = obj.getJSONArray("result");

                for (int i = 0; i < JSON_array.length(); i++) {
                    // Grabs the objects from the array
                    JSONObject object = JSON_array.getJSONObject(i);
                    // System.out.println(object);
                    double object_amount = object.getDouble("amount");
                    System.out.println(object_amount);
                    String object_type = object.getString("type");
                    System.out.println(object_type);
                    TOTAL += object_amount;
                    // now parsing the object by its type

                    if (object_type.equals("Food")) {
                        System.out.println("its food");
                        FOOD += object_amount;
                    } else if (object_type.equals("Transportation")) {
                        System.out.println("its transportation");
                        TRANSPORTATION += object_amount;
                    } else if (object_type.equals("Education")) {
                        System.out.println("its education");
                        EDUCATION += object_amount;
                    } else if (object_type.equals("Entertainment")) {
                        System.out.println("its entertainment");
                        ENTERTAINMENT += object_amount;
                    } else {
                        System.out.println("its Miscellenous");
                        MISCELLENOUS += object_amount;
                    }
                }
                System.out.println("The grand Total:");
                System.out.println(TOTAL);

                foodPercent = Math.round((FOOD / TOTAL) * 100);
                eduPercent = Math.round((EDUCATION / TOTAL) * 100);
                transPercent = Math.round((TRANSPORTATION / TOTAL) * 100);
                entPercent = Math.round((ENTERTAINMENT / TOTAL) * 100);
                misPercent = Math.round((MISCELLENOUS / TOTAL) * 100);
                // Need to convert the percentages to ints for the graph
                int foodPercent2 = (int) foodPercent;
                int eduPercent2 = (int) eduPercent;
                int transPercent2 = (int) transPercent;
                int entPercent2 = (int) entPercent;
                int misPercent2 = (int) misPercent;
                System.out.println(foodPercent2);
                System.out.println(eduPercent2);
                System.out.println(transPercent2);
                System.out.println(entPercent2);
                System.out.println(misPercent2);
                // The chart
                BarChart chart = (BarChart)view.findViewById(R.id.chart);
                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(transPercent2, 0));
                entries.add(new BarEntry(foodPercent2, 1));
                entries.add(new BarEntry(entPercent2, 2));
                entries.add(new BarEntry(eduPercent2, 3));
                entries.add(new BarEntry(misPercent2, 4));
                // Define the set
                BarDataSet dataset = new BarDataSet(entries, "Percentages of Monthly Spending");
                // Add the Labels
                ArrayList<String> labels = new ArrayList<String>();
                labels.add("Transportation");
                labels.add("Food");
                labels.add("Entertainment");
                labels.add("Education");
                labels.add("Miscellenous");
                // set the view
                // BarChart chart = new BarChart(context);

                // add the data to the chart
                BarData data = new BarData(labels, dataset);
                chart.setData(data);
                // Add a description
                chart.setDescription("Monthly Spending");
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);

                chart.animateY(3000, Easing.EasingOption.EaseOutBounce);

            } catch (JSONException e) {
                System.out.println("Failed to get JSON object");
            }
            //Toast.makeText(getBaseContext(), jsonData, Toast.LENGTH_LONG).show();
            System.out.println("Luis Campos");

        }

        return view;
    }

    // Function to read in JSON DATA
    public String loadJSONFromAsset() {
        String json = null;

        File dir = getActivity().getFilesDir();
        File file = new File(dir,"result.json");

        if(file.exists()){

            try {
                //InputStream is = getAssets().open("input.json");
                InputStream is = getActivity().openFileInput("result.json");

                //check if file exits, otherwise create an empty one
                int size = is.available();

                byte[] buffer = new byte[size];

                is.read(buffer);

                is.close();

                json = new String(buffer, "UTF-8");

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return json;
    }
}