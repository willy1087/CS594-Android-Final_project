package com.example.willy1087.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class Input_Fragment_left extends Fragment {

    private Button clear,submit;
    private EditText amount;
    private Spinner spinner;

    public double the_amount;
    public String the_choice;

    private TextView the_budget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_fragment_left, container, false);

        //checking monthly values
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        final int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        final int currentYear = localCalendar.get(Calendar.YEAR);



        the_budget = (TextView)view.findViewById(R.id.budget_value);

        //set the budget based on the file otherwise set it to 0
        File dir4 = getActivity().getFilesDir();
        File file4 = new File(dir4,"budget.json");

        String value = "";
        double d = 0.0;
        if(file4.exists()){

            String budget = ReadJSONFile(file4.getName());


            try {
                JSONObject check = new JSONObject(budget);
                d = check.getDouble("budget");
                //value = (""+d);

            }catch (JSONException e){
                e.printStackTrace();
            }

            //need to get the total amount for the month to get the balance
            File dir = getActivity().getFilesDir();
            File file = new File(dir, "input.json");

            double store_amount = 0;

            if (file.exists()) {


                String JSON_String = ReadJSONFile(file.getName());


                try {
                    JSONObject check = new JSONObject(JSON_String);
                    JSONArray JSON_array = check.getJSONArray("input");

                    for (int i = 0; i < JSON_array.length(); i++) {
                        JSONObject row = JSON_array.getJSONObject(i);

                        //checking what to add
                        //will be using current month
                        String x = row.optString("date");
                        //splitting the date into 3 fields but only use 2 (month and year)
                        String [] fields = x.split("-");

                        int json_year = Integer.parseInt(fields[2]);
                        int json_month = Integer.parseInt(fields[0]);

                        if(json_year == currentYear && json_month == currentMonth){
                            store_amount += row.getDouble("amount");
                        }

                        //write balance (store_amount) to budget file
                        File bud_dir = getActivity().getFilesDir();
                        File bud_file = new File(bud_dir, "budget.json");

                        //if file exits write to it
                        if(bud_file.exists()){

                            String file_string = ReadJSONFile(bud_file.getName());

                            try{
                                JSONObject jObj = new JSONObject(file_string);

                                jObj.put("balance",d-store_amount);

                                //write to budget file
                                try {
                                    FileOutputStream outputStream=getActivity().openFileOutput("budget.json", Context.MODE_PRIVATE);
                                    outputStream.write(jObj.toString().getBytes());
                                    outputStream.close();
                                }catch (FileNotFoundException e){
                                    e.printStackTrace();
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }

                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        //end of write balance
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //check this future value

                System.out.println("The total amount from the JSON file without the new value added " + store_amount);

            }else{
                store_amount = 0;
            }

            double balance = d - store_amount;
            value = (""+balance);



        }else{
            value = "0";
        }

        the_budget.append(value);

        //if the amount is close to the budget then set the text to red (value holder)
        //we need 80 of the budget at most
//        double warning = budget*0.80;
//
//        if(holder >= warning){
//            the_budget.setTextColor(Color.RED);
//        }



        clear = (Button)view.findViewById(R.id.clear);
        amount  = (EditText)view.findViewById(R.id.amount);

        submit = (Button)view.findViewById(R.id.submit);

        //set up spinner with adapter
        spinner = (Spinner)view.findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.report_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);





        System.out.println("This is the current month " + currentMonth + " and Current Year"+ currentYear );

        //checking monthly values




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                //store the selected item to string the choice
                the_choice = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the value to double the amount
                if(!zero_check(amount.getText().toString())){
                //if(amount.getText().toString().equals("0") || amount.getText().toString().isEmpty()){
                    //the_amount = 0;

                    //invalid input try again

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Invalid Input!");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();


                }else {
                    the_amount = Double.parseDouble(amount.getText().toString());
//                }


                    //need to get all the amount values form json file and
                    //add them to see if the amount exceed the budget

                    File dir = getActivity().getFilesDir();
                    File file = new File(dir, "input.json");
                    double holder;
                    if (file.exists()) {


                        String JSON_String = ReadJSONFile(file.getName());

                        double store_amount = 0;
                        try {
                            JSONObject check = new JSONObject(JSON_String);
                            JSONArray JSON_array = check.getJSONArray("input");

                            for (int i = 0; i < JSON_array.length(); i++) {
                                JSONObject row = JSON_array.getJSONObject(i);

                                //checking what to add
                                //will be using current month
                                String x = row.optString("date");
                                //splitting the date into 3 fields but only use 2 (month and year)
                                String [] fields = x.split("-");

                                int json_year = Integer.parseInt(fields[2]);
                                int json_month = Integer.parseInt(fields[0]);

                                if(json_year == currentYear && json_month == currentMonth){
                                    store_amount += row.getDouble("amount");
                                }

                                //System.out.println("The date as a string is " + x);


                                //store_amount += row.getDouble("amount");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //check this future value
                        holder = store_amount + the_amount;

                        System.out.println("The total amount from the JSON file without the new value added " + store_amount);

                    } else {
                        holder = the_amount;

                    }

                    //read from the budget.json file to assign budget

                    File dir3 = getActivity().getFilesDir();
                    File file3 = new File(dir3, "budget.json");

                    double budget = 0;
                    if (file3.exists()) {
                        //get the object from budget
                        //read object
                        String budget_st = ReadJSONFile(file3.getName());


                        try {
                            JSONObject check = new JSONObject(budget_st);
                            budget = check.getDouble("budget");

                            System.out.println("The budget from the file is " + budget);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        //for testing purposes
                        budget = 0;
                    }
                    if (holder > budget) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("You are about to exceed your budget!");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update Budget Or Wait until the end of the month",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                        alertDialog.show();

                    } else {
                        //create JSON file
                        create_JSON(the_amount, the_choice);

                        //refresh to start a new fragment
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);

                    }
                }




            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");


                //temporary delete budget file
                File dir2 = getActivity().getFilesDir();
                File file2 = new File(dir2,"budget.json");
                boolean deleted2= file2.delete();
                System.out.println("Budget file was delete" + deleted2);

                //temporary delete file for testing purposes
//                File dir = getActivity().getFilesDir();
//                File file = new File(dir,"input.json");
//                boolean deleted = file.delete();
//                System.out.println("was the file deleted: "+ deleted);


            }


        });

        return view;
    }


    //Helpers methods

    public String ReadJSONFile(String filename){

        String ret="";

                try{
                    InputStream inputStream = getActivity().openFileInput(filename);

                    if(inputStream != null){
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";

                        StringBuilder stringBuilder = new StringBuilder();

                        while((receiveString = bufferedReader.readLine())!=null){
                            stringBuilder.append(receiveString);
                        }
                        inputStream.close();
                        ret = stringBuilder.toString();

                    }

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

        return ret;
    }

    public void create_JSON(double amount, String type) {


        //get current date
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date dateobj = new Date();
        df.format(dateobj);

        //save amount, type, and date to JSON

        //create a Json Object
        JSONObject row = new JSONObject();

        try {

            row.put("type",type);
            row.put("amount",amount);
            row.put("date", df.format(dateobj));

        }catch(JSONException e) {
            e.printStackTrace();
        }


        String jsonStr ="";

        File dir = getActivity().getFilesDir();
        File file = new File(dir,"input.json");

        if(file.exists()){
            System.out.println("file exists");

            //read file
            String file_string = ReadJSONFile(file.getName());

            try{
                JSONObject jObj = new JSONObject(file_string);
                JSONArray array = jObj.getJSONArray("input");
                array.put(row);

                jObj.put("input",array);

                jsonStr = jObj.toString();
            }
            catch (JSONException e){
                e.printStackTrace();
            }

        }else{
            System.out.println("file doesn't exits yet");

            //add it to and Object array
            JSONArray jsonArray = new JSONArray();


            jsonArray.put(row);

            JSONObject inputObject = new JSONObject();

            try{
                inputObject.put("input", jsonArray);
            }catch (JSONException e){
                e.printStackTrace();
            }

            jsonStr = inputObject.toString();
        }


        //Toast.makeText(getActivity(),jsonStr, Toast.LENGTH_LONG).show();

        //return to this activity
        System.out.println("jsonString: "+jsonStr);

        //saving it to the internal storage
        String filename = "input.json";

//        FileOutputStream outputStream;
        try {
            FileOutputStream outputStream=getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonStr.getBytes());
            outputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    public boolean zero_check(String d){

        boolean checking = true;

        if(d.isEmpty()){
            checking = false;
        }else if (Double.parseDouble(d) == 0){
            checking = false;
        }

        return checking;
    }

}
