package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;

/**
 * Created by Maiada on 1/3/2018.
 */

public class ProfileTabsFragment extends Fragment {

    String Tabs_name_user[] = {"معلومات"};
    String Tabs_name[] = {"التقييم", " ميديا", "معلومات"};

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    String getTypeOfCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_tabs, container, false);

        getTypeOfCategory = getArguments().getString("typeOfCategory");


        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        mViewPager =  rooView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout =  rooView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        changeFragmentForTab(new RateFragment());


        return rooView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(getTypeOfCategory.equals("3")){
                switch (position) {
                    case 0:

                        return new RateFragment();
                    case 1:


                        return new MediaFragment();
                    case 2:

                        PersonalInformationFragment personalInformationFragment = new PersonalInformationFragment();
                        Bundle bundlePersonalInformationFragment = new Bundle();
                        bundlePersonalInformationFragment.putString("typeOfCategory",getTypeOfCategory);
                        personalInformationFragment.setArguments(bundlePersonalInformationFragment);

                        return personalInformationFragment;

                    default:
                        return null;
                }
            }else  {
                switch (position) {
                    case 0:
                        PersonalInformationFragment personalInformationFragment = new PersonalInformationFragment();
                        Bundle bundlePersonalInformationFragment = new Bundle();
                        bundlePersonalInformationFragment.putString("typeOfCategory",getTypeOfCategory);
                        personalInformationFragment.setArguments(bundlePersonalInformationFragment);

                        return personalInformationFragment;

                    default:
                        return null;
                }
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if(getTypeOfCategory.equals("3")){
                return Tabs_name.length;

            }else{
                return Tabs_name_user.length;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(getTypeOfCategory.equals("3")) {
                return Tabs_name[position];

            }else{
                return Tabs_name_user[position];

            }
        }
    }

    private void changeFragmentForTab(Fragment targetFragment) {

        // setTypeOfCategory
        Bundle bundle_category = new Bundle();
        bundle_category.putString("typeOfCategory",getTypeOfCategory);
        targetFragment.setArguments(bundle_category);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();


    }
}