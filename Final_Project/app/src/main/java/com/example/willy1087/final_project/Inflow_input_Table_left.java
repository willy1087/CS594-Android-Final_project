package com.example.willy1087.final_project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by williamsalinas on 5/10/15.
 */
public class Inflow_input_Table_left extends Activity implements ActionBar.TabListener{

    ActionBar.Tab firstL, secondL;
    Fragment firstList;
    Fragment secondList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inflow_input_fragment_left_table);


        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Search")
                        .setTabListener(this));

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Add")
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if(id== R.id.dashboard){
            //go to weekly layout
            return true;
        }

        if(id == R.id.inflow){
            return true;
        }

        if(id == R.id.outflow){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction FragTrans) {
        firstList = new Inflow_input_fragment_left();
        secondList = new Inflow_input_fragment_right();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if(tab.getPosition() == 1){

            Intent intent = new Intent(Inflow_input_Table_left.this,Inflow_input_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

}
