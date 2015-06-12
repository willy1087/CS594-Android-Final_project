package com.example.willy1087.final_project;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Result_Fragment_left extends Fragment {
    TableLayout tbl;
    TableRow tbr;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment_left, container, false);


        //read data from json file
//        File dir = getActivity().getFilesDir();
//        File file = new File(dir, "result.json");
        String jsonData = "";
        jsonData = loadJSONFromAsset();

//        if(file.exists()){
//            String JSON_String = ReadJSONFile();
        if (!(jsonData == null)) {
            tbl = (TableLayout) view.findViewById(R.id.result_data);

            try {
//                JSONObject JFile = new JSONObject(JSON_String);
//                JSONArray JSON_array = JFile.getJSONArray("result");
                // Create a big JSON object for the whole file
                JSONObject obj = new JSONObject(jsonData);
                // System.out.println(obj);
                // Grab the array called input
                JSONArray JSON_array = obj.getJSONArray("result");

                for(int i = 0; i < JSON_array.length(); i++){
                    JSONObject row = JSON_array.getJSONObject(i);


                    if(i == 0) {

                        TextView date0 = (TextView)view.findViewById(R.id.date0);
                        TextView amount0 = (TextView)view.findViewById(R.id.amount0);
                        TextView type0 = (TextView)view.findViewById(R.id.type0);

                        date0.setText(row.getString("date"));
                        amount0.setText(row.getString("amount"));
                        type0.setText(row.getString("type"));

                    }else if (i == 1){

                        TextView date1 = (TextView)view.findViewById(R.id.date1);
                        TextView amount1 = (TextView)view.findViewById(R.id.amount1);
                        TextView type1 = (TextView)view.findViewById(R.id.type1);

                        date1.setText(row.getString("date"));
                        amount1.setText(row.getString("amount"));
                        type1.setText(row.getString("type"));
                    }else{

                        TextView date2 = (TextView)view.findViewById(R.id.date2);
                        TextView amount2 = (TextView)view.findViewById(R.id.amount2);
                        TextView type2 = (TextView)view.findViewById(R.id.type2);

                        date2.setText(row.getString("date"));
                        amount2.setText(row.getString("amount"));
                        type2.setText(row.getString("type"));
                    }



                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        return view;
    }

    //Helpers methods
/*    public String ReadJSONFile(){
        String ret = "";

        try{
            InputStream inputStream = getActivity().openFileInput("result.json");

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
    }*/

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