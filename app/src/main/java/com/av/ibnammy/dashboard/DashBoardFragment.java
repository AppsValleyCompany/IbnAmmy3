package com.av.ibnammy.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.av.ibnammy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mina on 1/3/2018.
 */

public class DashBoardFragment extends Fragment {

    private ProgressWheel pwtwo,pwthree,pwfour;
    List<String> spinner_data=new ArrayList<>();
    AutoCompleteTextView multiAutoCompleteTextView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v=inflater.inflate(R.layout.dash_board_activity_layout, container, false);

              spinner_data.add("القاهرة ");
              spinner_data.add("الجيزة ");
              spinner_data.add("الاسكندرية ");

        multiAutoCompleteTextView=(AutoCompleteTextView)v.findViewById(R.id.multiAutoCompleteTextView);
            ArrayAdapter adapter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                adapter = new ArrayAdapter(getContext(), R.layout.spinner_items,spinner_data);
            }

            multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiAutoCompleteTextView.showDropDown();

            }
        });


        pwtwo = (ProgressWheel)v. findViewById(R.id.progressBarTwo);
        pwthree = (ProgressWheel)v. findViewById(R.id.progressBarthree);
        pwfour = (ProgressWheel) v.findViewById(R.id.progressBarfour);

        pwtwo.incrementProgress(220);
        pwthree.incrementProgress(180);
        pwfour.incrementProgress(90);


        return v;
    }
}
