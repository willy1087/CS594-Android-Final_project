package com.example.willy1087.final_project;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by williamsalinas on 5/10/15.
 */
public class Inflow_input_fragment_left extends Fragment {

    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inflow_input_fragment_left, container, false);


        //do the work here

        submit = (Button)view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "this", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(),Inflow_input_Table_left.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        return view;
    }
}
