package com.example.willy1087.final_project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * Created by Gayatri D on 5/23/2015.
 */
public class weekly_activity  extends Activity {

    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_activity);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if (id == R.id.weekly_menu_button) {


            return true;
        } else if (id == R.id.monthly_menu_button) {

            //goes to monthly activity

            return true;
        } else if (id == R.id.search_menu_button) {

            //goes to search activity

            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}





