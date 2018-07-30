package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentRateTabBinding;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;

/**
 * Created by Maiada on 1/3/2018.
 */

public class RateFragment extends Fragment implements GetCallback.UpdateAccountRate{

    private FragmentRateTabBinding  fragmentRateTabBinding;

    private CousinProfileModel cousinProfileMode = new CousinProfileModel();
    private   CousinAccount cousinAccount;


    private  String phone,password;
    float rating = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentRateTabBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_rate_tab, container, false);
        View rootView = fragmentRateTabBinding.getRoot();  // getRoot because onCreateView return view

        cousinAccount = (CousinAccount) getArguments().getSerializable("CousinData");

        Bundle bundle = CommonUtils.loadCredentials(getContext());
        phone         = bundle.getString(Constants.PHONE_KEY);
        password      = bundle.getString(Constants.PASSWORD_KEY);


        if(!cousinAccount.getRaty().equals("0.0")){
            fragmentRateTabBinding.ratingBar.setRating(Float.parseFloat(cousinAccount.getRaty()));
        }




        fragmentRateTabBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = fragmentRateTabBinding.ratingBar.getRating();
                if(rating!=0.0){
                    showProgressBar();
                    cousinProfileMode.UpdateAccountRate(RateFragment.this,phone,password,cousinAccount.getCousinId(),rating);

                }
            }
        });




        return rootView;
    }

    @Override
    public void onUpdateRateSuccess() {
        hideProgressBar();
        sneakBar(getResources().getString(R.string.rate_success));
        cousinAccount.setRaty(String.valueOf(rating));

    }

    @Override
    public void onUpdateRateFailure() {
        hideProgressBar();
        sneakBar(getResources().getString(R.string.rate_error));
        if (!cousinAccount.getRaty().equals("0.0"))
            fragmentRateTabBinding.ratingBar.setRating(Float.parseFloat(cousinAccount.getRaty()));

    }


    private void sneakBar(String message){
        if(getView()!=null)
        Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
    }

    private void showProgressBar(){
        fragmentRateTabBinding.pbLoading.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        fragmentRateTabBinding.pbLoading.setVisibility(View.GONE);
    }

}