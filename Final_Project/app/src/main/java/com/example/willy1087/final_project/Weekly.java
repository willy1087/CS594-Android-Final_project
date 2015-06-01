package com.example.willy1087.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gayatri D on 5/30/2015.
 */
public class Weekly extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly);


        TextView text1=(TextView)findViewById(R.id.heading1);
        TextView text2=(TextView)findViewById(R.id.heading2);
        TextView text3=(TextView)findViewById(R.id.heading3);
        TextView text4=(TextView)findViewById(R.id.heading4);
        TextView text5=(TextView)findViewById(R.id.heading5);
        text2.setText("Food");
        text3.setText("Edu");
        text4.setText("Trans");
        text5.setText("Entr");

        populateTable();

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

        if(id == R.id.home_button){

            Intent intent = new Intent(Weekly.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            //return true;
        }

        //noinspection SimplifiableIfStatement
        else if (id == R.id.weekly_menu_button) {

            return true;
        }else if (id == R.id.monthly_menu_button){

            //goes to monthly activity
            Intent intent = new Intent(Weekly.this,MonthlyReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            //return true;
        }else if (id == R.id.search_menu_button){

            //goes to search activity

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public String loadJsonFromAssets()
    {
        String json=null;

        File dir = getFilesDir();
        File file = new File(dir,"input.json");

        if(file.exists()) {

            try {
                InputStream is = openFileInput("input.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    public Date convertStringToDate(String dateString)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }


    public void populateTable()
    {
        String jsonData=loadJsonFromAssets();


        //firstDay=new StringBuilder(firstDay).reverse().toString();
        //Log.i("dateTag", firstDay);
        double[] totalDay1=new double[5];
        double[] totalDay2=new double[5];
        double[] totalDay3=new double[5];
        double[] totalDay4=new double[5];
        double[] totalDay5=new double[5];
        double[] totalDay6=new double[5];
        double[] totalDay7=new double[5];

        int day=0;
        Date prevDate=null;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()); //gets the first day of the week
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");//format to "MM-dd-YYYY" because format in json file for date is "MM-dd-YYYY"
        String firstDay=sdf.format(cal.getTime());
        Date first=convertStringToDate(firstDay); //Convert the formatted string to date
        //first has the date for the first day of the current week

        if (!(jsonData == null)) {

            try {
                JSONObject json = new JSONObject(jsonData);
                JSONArray data = json.optJSONArray("input");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObj = (JSONObject) data.get(i);
                    Date date = convertStringToDate(jsonObj.optString("date")); //convert the date field from json file to object

//
//                Log.i("dateConvertJson", date.toString());
//                Log.i("firstdateConvertJson", first.toString());
//                Log.i("in compare to", first.compareTo(date)+"");
//


                    if (first.compareTo(date) <= 0 && day < 7) //first.compareTo(date) compares the first day of the week and date read from json file and returns
                    // 1 if first day of the week is greater than date
                    // 0,-1 if first day of the week is equal,less than date
                    {
                        // Log.i("in compare to", first.compareTo(date)+"");
                        TextView textDay1 = null;
                        TextView textDay2 = null;
                        TextView textDay3 = null;
                        TextView textDay4 = null;

                        switch (day) {
                            case 0:
                                textDay1 = (TextView) findViewById(R.id.day1Type1);
                                textDay2 = (TextView) findViewById(R.id.day1Type2);
                                textDay3 = (TextView) findViewById(R.id.day1Type3);
                                textDay4 = (TextView) findViewById(R.id.day1Type4);
                                break;
                            case 1:
                                textDay1 = (TextView) findViewById(R.id.day2Type1);
                                textDay2 = (TextView) findViewById(R.id.day2Type2);
                                textDay3 = (TextView) findViewById(R.id.day2Type3);
                                textDay4 = (TextView) findViewById(R.id.day2Type4);
                                break;
                            case 2:
                                textDay1 = (TextView) findViewById(R.id.day3Type1);
                                textDay2 = (TextView) findViewById(R.id.day3Type2);
                                textDay3 = (TextView) findViewById(R.id.day3Type3);
                                textDay4 = (TextView) findViewById(R.id.day3Type4);
                                break;
                            case 3:
                                textDay1 = (TextView) findViewById(R.id.day4Type1);
                                textDay2 = (TextView) findViewById(R.id.day4Type2);
                                textDay3 = (TextView) findViewById(R.id.day4Type3);
                                textDay4 = (TextView) findViewById(R.id.day4Type4);
                                break;
                            case 4:
                                textDay1 = (TextView) findViewById(R.id.day5Type1);
                                textDay2 = (TextView) findViewById(R.id.day5Type2);
                                textDay3 = (TextView) findViewById(R.id.day5Type3);
                                textDay4 = (TextView) findViewById(R.id.day5Type4);
                                break;
                            case 5:
                                textDay1 = (TextView) findViewById(R.id.day6Type1);
                                textDay2 = (TextView) findViewById(R.id.day6Type2);
                                textDay3 = (TextView) findViewById(R.id.day6Type3);
                                textDay4 = (TextView) findViewById(R.id.day6Type4);
                                break;
                            case 6:
                                textDay1 = (TextView) findViewById(R.id.day7Type1);
                                textDay2 = (TextView) findViewById(R.id.day7Type2);
                                textDay3 = (TextView) findViewById(R.id.day7Type3);
                                textDay4 = (TextView) findViewById(R.id.day7Type4);
                                break;
                            default:
                                break;
                        }


                        if (jsonObj.optString("type").equals("Food")) {

                            switch (day) {
                                case 0:
                                    totalDay1[0] = totalDay1[0] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay1.setText(totalDay1[0] + "");
                                    break;
                                case 1:
                                    totalDay2[0] = totalDay2[0] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay1.setText(totalDay2[0] + "");
                                    break;
                                case 2:
                                    totalDay3[0] = totalDay3[0] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay1.setText(totalDay3[0] + "");
                                    break;
                                case 3:
                                    totalDay4[0] = totalDay4[0] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay1.setText(totalDay4[0] + "");
                                    break;
                                case 4:
                                    totalDay5[0] = totalDay5[0] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay1.setText(totalDay5[0] + "");
                                    break;
                                case 5:
                                    totalDay6[0] = totalDay6[0] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay1.setText(totalDay6[0] + "");
                                    break;
                                case 6:
                                    totalDay7[0] = totalDay7[0] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay1.setText(totalDay7[0] + "");
                                    break;
                                default:
                                    break;

                            }


                        }
                        if (jsonObj.optString("type").equals("Education")) {
                            switch (day) {
                                case 0:
                                    totalDay1[1] = totalDay1[1] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay2.setText(totalDay1[1] + "");
                                    break;
                                case 1:
                                    totalDay2[1] = totalDay2[1] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay2.setText(totalDay2[1] + "");
                                    break;
                                case 2:
                                    totalDay3[1] = totalDay3[1] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay2.setText(totalDay3[1] + "");
                                    break;
                                case 3:
                                    totalDay4[1] = totalDay4[1] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay2.setText(totalDay4[1] + "");
                                    break;
                                case 4:
                                    totalDay5[1] = totalDay5[1] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay2.setText(totalDay5[1] + "");
                                    break;
                                case 5:
                                    totalDay6[1] = totalDay6[1] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay2.setText(totalDay6[1] + "");
                                    break;
                                case 6:
                                    totalDay7[1] = totalDay7[1] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay2.setText(totalDay7[1] + "");
                                    break;
                                default:
                                    break;

                            }

                        }

                        if (jsonObj.optString("type").equals("Transportation")) {
                            switch (day) {
                                case 0:
                                    Log.d("transport total ", totalDay1[2] + "");
                                    totalDay1[2] = totalDay1[2] + Double.parseDouble(jsonObj.optString("amount"));
                                    Log.d("transport addition", totalDay1[2] + "");
                                    Log.d("transport total 1", Double.parseDouble(jsonObj.optString("amount")) + "");
                                    textDay3.setText(totalDay1[2] + "");
                                    break;
                                case 1:
                                    totalDay2[2] = totalDay2[2] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay3.setText(totalDay2[2] + "");
                                    break;
                                case 2:
                                    totalDay3[2] = totalDay3[2] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay3.setText(totalDay3[2] + "");
                                    break;
                                case 3:
                                    totalDay4[2] = totalDay4[2] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay3.setText(totalDay4[2] + "");
                                    break;
                                case 4:
                                    totalDay5[2] = totalDay5[2] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay3.setText(totalDay5[2] + "");
                                    break;
                                case 5:
                                    totalDay6[2] = totalDay6[2] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay3.setText(totalDay6[2] + "");
                                    break;
                                case 6:
                                    totalDay7[2] = totalDay7[2] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay3.setText(totalDay7[2] + "");
                                    break;
                                default:
                                    break;

                            }

                        }

                        if (jsonObj.optString("type").equals("Entertainment")) {
                            switch (day) {
                                case 0:
                                    totalDay1[3] = totalDay1[3] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay4.setText(totalDay1[3] + "");
                                    break;
                                case 1:
                                    totalDay2[3] = totalDay2[3] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay4.setText(totalDay2[3] + "");
                                    break;
                                case 2:
                                    totalDay3[3] = totalDay3[3] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay4.setText(totalDay3[3] + "");
                                    break;
                                case 3:
                                    totalDay4[3] = totalDay4[3] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay4.setText(totalDay4[3] + "");
                                    break;
                                case 4:
                                    totalDay5[3] = totalDay5[3] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay4.setText(totalDay5[3] + "");
                                    break;
                                case 5:
                                    totalDay6[3] = totalDay6[3] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay4.setText(totalDay6[3] + "");
                                    break;
                                case 6:
                                    totalDay7[3] = totalDay7[3] + Double.parseDouble(jsonObj.optString("amount"));
                                    textDay4.setText(totalDay7[3] + "");
                                    break;
                                default:
                                    break;

                            }

                        }

                        if (prevDate == null) {
                            //Log.d("date compare","Prev is null");
                            prevDate = date;
                            TextView day1 = (TextView) findViewById(R.id.day1);
                            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
                            String date1 = sdf1.format(date);
                            day1.setText(date1 + "");

                        } else if (date.compareTo(prevDate) > 0) {
                            // Log.d("date compare",date.compareTo(prevDate)+"");
                            prevDate = date;
                            day++;
                            TextView dayId = null;
                            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
                            String date1 = sdf1.format(date);
                            switch (day) {
                                case 1:
                                    dayId = (TextView) findViewById(R.id.day2);
                                    dayId.setText(date1 + "");
                                case 2:
                                    dayId = (TextView) findViewById(R.id.day3);
                                    dayId.setText(date1 + "");
                                case 3:
                                    dayId = (TextView) findViewById(R.id.day4);
                                    dayId.setText(date1 + "");
                                case 4:
                                    dayId = (TextView) findViewById(R.id.day5);
                                    dayId.setText(date1 + "");
                                case 5:
                                    dayId = (TextView) findViewById(R.id.day6);
                                    dayId.setText(date1 + "");
                                case 6:
                                    dayId = (TextView) findViewById(R.id.day7);
                                    dayId.setText(date1 + "");
                                default:
                                    break;

                            }
                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }


}
