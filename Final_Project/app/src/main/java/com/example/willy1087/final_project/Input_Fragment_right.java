package com.example.willy1087.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Input_Fragment_right extends Fragment {
    private Button clear, submit;
    private EditText salary_input, budget_input;
    public double the_salary, the_budget, the_balance, salary_json, budget_json, balance_json, spent;
    private TextView balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_fragment_right, container, false);

        salary_input  = (EditText)view.findViewById(R.id.salary);
        budget_input  = (EditText)view.findViewById(R.id.budget);
        clear = (Button)view.findViewById(R.id.clear);
        submit = (Button)view.findViewById(R.id.submit);
        balance = (TextView)view.findViewById(R.id.balance_view);

        File dir = getActivity().getFilesDir();
        File file = new File(dir,"budget.json");

        if(file.exists()){
            String JSON_String = ReadJSONFile();

            try {
                JSONObject jObj = new JSONObject(JSON_String);
                budget_json = jObj.getDouble("budget");
                salary_json = jObj.getDouble("salary");
                balance_json = jObj.getDouble("balance");
            }catch (JSONException e){
                e.printStackTrace();
            }
            //check if budget is 0
            if(budget_json == 0.0){
                the_budget = the_salary / 12;
            }

            budget_input.setText(budget_json+"");
            salary_input.setText(salary_json+"");

            //calculate the current amount spent
            spent = budget_json - balance_json;

            //calculate the new balance
            the_balance = budget_json - spent;

            balance.setText("Balance: $" + the_balance);
        }
        else {
            balance.setText("Balance: $" + 0);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(zero_check(salary_input.getText().toString())||zero_check(budget_input.getText().toString())){
                    //get the value to double the salary
                    if(salary_input.getText().toString().isEmpty()){
                        the_salary = 0;
                    }
                    else {
                        the_salary = Double.parseDouble(salary_input.getText().toString());
                    }

                    //get the value to double the budget
                    if(budget_input.getText().toString().isEmpty()){
                        the_budget = 0;
                    }
                    else {
                        the_budget = Double.parseDouble(budget_input.getText().toString());
                    }

                    //check if budget is 0
                    if(the_budget == 0.0){
                        the_budget = the_salary / 12;
                    }

                    spent = 0.0;

                    //need to get all the values form json file and check
                    //to see if the new budget is not less than amount spent
                    File dir = getActivity().getFilesDir();
                    File file = new File(dir,"budget.json");

                    //calculate the new balance
                    the_balance = the_budget - spent;

                    if(the_balance >= 0.0) {
                        //create JSON file
                        create_JSON(the_salary, the_budget, the_balance);

                        //refresh to start a new fragment
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Your new budget doesn't cover your current expenses!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update Budget",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Invalid Input!");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salary_input.setText("");
                budget_input.setText("");

                //temporary delete file for testing purposes
//                File dir = getActivity().getFilesDir();
//                File file = new File(dir, "budget.json");
//                boolean deleted = file.delete();
//                System.out.println("was the file deleted: " + deleted);
            }
        });

        return view;
    }

    //Helpers methods
    public String ReadJSONFile(){
        String ret="";

        try{
            InputStream inputStream = getActivity().openFileInput("budget.json");

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
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return ret;
    }

    public void create_JSON(double sal, double bud, double bal) {
        String jsonStr ="";
        File dir = getActivity().getFilesDir();
        File file = new File(dir, "budget.json");
        JSONObject jObj;

        if(file.exists()){
            System.out.println("file exists");

            //read file
            String file_string = ReadJSONFile();

            try{
                jObj = new JSONObject(file_string);
                salary_json = jObj.getDouble("salary");
                budget_json = jObj.getDouble("budget");
                balance_json = jObj.getDouble("balance");

                jObj.put("salary", sal);
                jObj.put("budget", bud);
                jObj.put("balance", bal);

                jsonStr = jObj.toString();
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("file doesn't exits yet");
            //add it to and Object
            jObj = new JSONObject();

            try{
                jObj.put("salary", sal);
                jObj.put("budget", bud);
                jObj.put("balance", bal);
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            jsonStr = jObj.toString();
        }

        //Toast.makeText(getActivity(), jsonStr, Toast.LENGTH_LONG).show();

        //return to this activity
        System.out.println("jsonString: " + jsonStr);

        //saving it to the internal storage
        String filename = "budget.json";

        // FileOutputStream outputStream;
        try {
            FileOutputStream outputStream=getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonStr.getBytes());
            outputStream.close();
        }
        catch (FileNotFoundException e){
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
        }
        else if (Double.parseDouble(d) == 0){
            checking = false;
        }

        return checking;
    }
}