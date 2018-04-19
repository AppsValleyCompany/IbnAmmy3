package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.MediaFragment;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.PersonalInformationFragment;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.RateFragment;

/**
 * Created by Mina on 1/4/2018.
 */

public class CousinProfileFragment extends Fragment {


   private TabLayout tabLayout ;
   private int tabSelectedPosition;
   private CousinAccount cousinAccount;
   private int categoryType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_cousin_profile, container, false);

        tabLayout = v.findViewById(R.id.tab_host);

        categoryType = getArguments().getInt("CategoryType");
        cousinAccount = (CousinAccount) getArguments().getSerializable("CousinData");

        changeFragmentForTabs(new PersonalInformationFragment());

        if(categoryType==0||categoryType==1){

            tabLayout.addTab(tabLayout.newTab().setText("معلومات"));


        }else {
            tabLayout.addTab(tabLayout.newTab().setText("معلومات"));
            tabLayout.addTab(tabLayout.newTab().setText("ميديا"));
            tabLayout.addTab(tabLayout.newTab().setText("التقييم"));



        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelectedPosition = tabLayout.getSelectedTabPosition();
                if(tabSelectedPosition==0){
                    changeFragmentForTabs(new PersonalInformationFragment());

                }else if(tabSelectedPosition==1){
                    changeFragmentForTabs(new MediaFragment());

                }else {
                    changeFragmentForTabs(new RateFragment());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });






    return  v;

    }

    private void changeFragmentForTabs(Fragment targetFragment){


                 Bundle bundle=new Bundle();
                 bundle.putInt("CategoryType",categoryType);
                 bundle.putSerializable("CousinData",cousinAccount);
                 targetFragment.setArguments(bundle);
                 getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.tab_section, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
