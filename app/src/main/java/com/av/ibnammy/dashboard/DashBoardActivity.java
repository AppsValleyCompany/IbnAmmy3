package com.av.ibnammy.dashboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.av.ibnammy.R;

import java.util.ArrayList;
import java.util.List;



public class DashBoardActivity extends AppCompatActivity {
    private ProgressWheel pwtwo,pwthree,pwfour;
    List<String> spinner_data=new ArrayList<>();
    AutoCompleteTextView multiAutoCompleteTextView;

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_activity_layout);
/*
        spinner_data.add("القاهرة ");
        spinner_data.add("الجيزة ");
        spinner_data.add("الاسكندرية ");

        multiAutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.spinner_items,spinner_data);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiAutoCompleteTextView.showDropDown();

            }
        });


        pwtwo = (ProgressWheel) findViewById(R.id.progressBarTwo);
        pwthree = (ProgressWheel) findViewById(R.id.progressBarthree);
        pwfour = (ProgressWheel) findViewById(R.id.progressBarfour);

        pwtwo.incrementProgress(220);
        pwthree.incrementProgress(180);
        pwfour.incrementProgress(90);
*/


    }
}
