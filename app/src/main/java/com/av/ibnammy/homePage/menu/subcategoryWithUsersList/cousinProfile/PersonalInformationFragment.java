package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.av.ibnammy.R;

/**
 * Created by Maiada on 1/3/2018.
 */

public class PersonalInformationFragment  extends Fragment {

    String setTagOfFragment;
    private TextView mUserJob;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_personal_infromation, container, false);

        setTagOfFragment  =    getArguments().getString("typeOfCategory");
        mUserJob          =   rooView.findViewById(R.id.user_job_txt);

        if(setTagOfFragment.equals("1")){

            mUserJob.setText("مدير مالى");

        }else if(setTagOfFragment.equals("2")){
            mUserJob.setText("هندسه");

        }else {
            mUserJob.setText("جراحه عامة");
        }


        return rooView;
    }
}
