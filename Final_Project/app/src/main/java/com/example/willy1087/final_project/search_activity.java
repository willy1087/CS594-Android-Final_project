package com.example.willy1087.final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Date;


public class search_activity extends Activity {
    private Button search, clear;
    private Spinner spinner;
    private EditText start_date_input, end_date_input;
    private String the_choice;
    private Date start_date = null;
    private Date end_date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        File dir = getFilesDir();
        File file = new File(dir,"result.json");
        boolean deleted = file.delete();
        System.out.println("result was the file deleted? "+ deleted);

        search = (Button) findViewById(R.id.search);
        clear = (Button)findViewById(R.id.clear);
        start_date_input = (EditText) findViewById(R.id.startDate);
        end_date_input = (EditText) findViewById(R.id.endDate);

        //set up spinner with adapter
        spinner = (Spinner)findViewById(R.id.searchType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(search_activity.this, R.array.search_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(search_activity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                //store the selected item to string the_choice
                the_choice = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to get all the values form json file and
                //compare them with user input start_date, end_date and type

                //get and format date
                SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");

                try {
                    if((isDate(start_date_input.getText().toString()))
                            && (isDate(end_date_input.getText().toString()))){
                        start_date = df1.parse(start_date_input.getText().toString());
                        end_date = df1.parse(end_date_input.getText().toString());
                    }
                    else {
                        AlertDialog alertDialog = new AlertDialog.Builder(search_activity.this).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Invalid date input!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Please enter correct date format (MM/dd/yyyy).",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                File dir = getFilesDir();
                File file = new File(dir, "input.json");

                if(file.exists()){
                    String JSON_String = ReadJSONFile("input.json");

                    try {
                        JSONObject JFile = new JSONObject(JSON_String);
                        JSONArray JSON_array = JFile.getJSONArray("input");
                        SimpleDateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
                        Date row_date = null;

                        for(int i = 0; i < JSON_array.length(); i++){
                            JSONObject row = JSON_array.getJSONObject(i);
                            try {
                                row_date = df2.parse(row.getString("date"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(the_choice.equals("All")){
                                if((row_date.compareTo(start_date) >= 0)
                                        && (row_date.compareTo(end_date) <= 0)){
                                    //create JSON file
                                    create_JSON(row);
                                }
                            }
                            else{
                                if((row_date.compareTo(start_date) >= 0)
                                        && (row_date.compareTo(end_date) <= 0)
                                        && (the_choice.equals(row.getString("type")))){
                                    //create JSON file
                                    create_JSON(row);
                                }
                            }
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(search_activity.this, result_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(search_activity.this).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("File does not exist!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Add data to file.",
                            new DialogInterface.OnClickListener() {
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
                start_date_input.setText("");
                end_date_input.setText("");
                spinner.setSelection(0);

                //temporary delete file for testing purposes
                File dir = search_activity.this.getFilesDir();
                File file = new File(dir,"result.json");
                boolean deleted = file.delete();
                System.out.println("was the file deleted: " + deleted);
            }
        });
    }

    //Helpers methods
    public String ReadJSONFile(String file){
        String ret = "";

        try{
            InputStream inputStream = openFileInput(file);

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                StringBuilder stringBuilder = new StringBuilder();

                while((receiveString = bufferedReader.readLine())!= null){
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

    public void create_JSON(JSONObject r) {
        //save amount, type, and date to JSON
        //create a Json Object
        JSONObject row = r;

        String jsonStr = "";
        File dir = getFilesDir();
        File file = new File(dir, "result.json");

        if(file.exists()){
            System.out.println("file exists");

            //read file
            String file_string = ReadJSONFile("result.json");

            try{
                JSONObject jObj = new JSONObject(file_string);
                JSONArray array = jObj.getJSONArray("result");
                array.put(row);

                jObj.put("result", array);

                jsonStr = jObj.toString();
            }
            catch (JSONException e){
                e.printStackTrace();
            }

        }
        else{
            System.out.println("file doesn't exits yet");

            //add it to and Object array
            JSONArray jsonArray = new JSONArray();

            jsonArray.put(row);

            JSONObject inputObject = new JSONObject();

            try{
                inputObject.put("result", jsonArray);
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            jsonStr = inputObject.toString();
        }

        //Toast.makeText(search_activity.this, jsonStr, Toast.LENGTH_LONG).show();

        //return to this activity
        System.out.println("jsonString: " + jsonStr);

        //saving it to the internal storage
        String filename = "result.json";

        try {
            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
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

    public boolean isDate(String date){
        if(date == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);

        try{
            // if date not valid, will throw ParseException
            Date d = sdf.parse(date);
        }
        catch (ParseException e){
            e.printStackTrace();
            return false;
        }

        return true;
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
        if(id == R.id.home_button){

            Intent intent = new Intent(search_activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        else if (id == R.id.weekly_menu_button) {

            Intent intent = new Intent(search_activity.this,Weekly.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            //return true;
        }else if (id == R.id.monthly_menu_button){

            //goes to monthly activity
            Intent intent = new Intent(search_activity.this,MonthlyReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            return true;
        }else if (id == R.id.search_menu_button){

            //goes to search activity

            return true;
        }else if (id== R.id.delete_everything){

            //delete all the files and go back to main fragment
            File dir = getFilesDir();
            File file = new File(dir,"input.json");
            boolean deleted = file.delete();
            System.out.println("was the file deleted: "+ deleted);

            File dir1 = getFilesDir();
            File file1 = new File(dir1,"budget.json");
            boolean deleted1 = file1.delete();
            System.out.println("was the file deleted: "+ deleted1);

            File dir2 = getFilesDir();
            File file2 = new File(dir2,"result.json");
            boolean deleted2 = file2.delete();
            System.out.println("was the file deleted: "+ deleted2);


            Intent intent = new Intent(search_activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
//        if (id == R.id.home_button) {
//            Intent intent = new Intent(search_activity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(intent);
//        }
//
//        return super.onOptionsItemSelected(item);
    }
}
