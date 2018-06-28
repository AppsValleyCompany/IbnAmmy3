package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentRateTabBinding;

/**
 * Created by Maiada on 1/3/2018.
 */

public class RateFragment extends Fragment {

    private FragmentRateTabBinding  fragmentRateTabBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentRateTabBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_rate_tab, container, false);
        View rootView = fragmentRateTabBinding.getRoot();  // getRoot because onCreateView return view

  /*      fragmentRateTabBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(), "Play"+rating, Toast.LENGTH_SHORT).show();

            }
        });
*/




        return rootView;
    }
}