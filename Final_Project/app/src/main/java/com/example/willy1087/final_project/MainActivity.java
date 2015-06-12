package com.example.willy1087.final_project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;


@SuppressWarnings("deprications")
public class MainActivity extends Activity implements ActionBar.TabListener{

    ActionBar.Tab firstL, secondL;
    Fragment firstList;
    Fragment secondList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starApp();

    }

    private void starApp(){

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Input")
                        .setTabListener(this));

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Budget")
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
            return true;
        }

        //noinspection SimplifiableIfStatement
        else if (id == R.id.weekly_menu_button) {


            Intent intent = new Intent(MainActivity.this,Weekly.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            //return true;
        }else if (id == R.id.monthly_menu_button){

            //goes to monthly activity
            Intent intent = new Intent(MainActivity.this,MonthlyReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

            //return true;
        }else if (id == R.id.search_menu_button){

            //goes to search activity
            Intent intent = new Intent(MainActivity.this,search_activity.class);
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


            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction FragTrans) {
        firstList = new Input_Fragment_left();
        secondList = new Input_Fragment_right();
        //secondList = new DemoFragment2();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if(tab.getPosition()==0) {

            ft.replace(R.id.container, firstList);
            firstList.setRetainInstance(true);
            ft.commit();
        }
       else{
            ft.replace(R.id.container, secondList);
            secondList.setRetainInstance(true);
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
