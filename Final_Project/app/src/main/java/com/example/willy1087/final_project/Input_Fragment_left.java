package com.example.willy1087.final_project;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by mark on 4/23/15.
 */
public class Input_Fragment_left extends Fragment {

    private Button clear;
    private EditText amount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_fragment_left, container, false);


        clear = (Button)view.findViewById(R.id.clear);
        amount  = (EditText)view.findViewById(R.id.amount);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
            }
        });

        return view;
    }

}
