package com.example.willy1087.final_project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;


public class result_activity extends Activity implements ActionBar.TabListener {
    ActionBar.Tab tab_left, tab_right;
    Fragment result_fragment_left;
    Fragment result_fragment_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Table")
                        .setTabListener(this));

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Graph")
                        .setTabListener(this));
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
            Intent intent = new Intent(result_activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        else if (id == R.id.weekly_menu_button) {


            Intent intent = new Intent(result_activity.this,Weekly.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            //return true;
        }else if (id == R.id.monthly_menu_button){

            //goes to monthly activity
            Intent intent = new Intent(result_activity.this,MonthlyReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            //return true;
        }else if (id == R.id.search_menu_button){

            //goes to search activity
            Intent intent = new Intent(result_activity.this,search_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
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


            Intent intent = new Intent(result_activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction FragTrans) {
        result_fragment_left = new Result_Fragment_left();
        result_fragment_right = new Result_Fragment_right();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if(tab.getPosition() == 0) {
            ft.replace(R.id.container, result_fragment_left);
            result_fragment_left.setRetainInstance(true);
            ft.commit();
        }
        else{
            ft.replace(R.id.container, result_fragment_right);
            result_fragment_right.setRetainInstance(true);
            ft.commit();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
