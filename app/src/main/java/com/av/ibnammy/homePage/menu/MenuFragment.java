package com.av.ibnammy.homePage.menu;

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

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private  TabLayout tabLayout;
    private  ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_menu, container, false);

        viewPager = rooView.findViewById(R.id.viewpager);
        tabLayout =  rooView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        changeFragmentForTab(new CategoriesFragment());


        return rooView;
    }

        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(new CategoriesFragment(), "موظفين",1);
            adapter.addFragment(new CategoriesFragment(), "غير موظفين",2);
            adapter.addFragment(new CategoriesFragment(), "اصحاب اعمال/حرف",3);
            viewPager.setAdapter(adapter);
      }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public void addFragment(Fragment fragment, String title,int type) {
            Bundle bundle = new Bundle();
            bundle.putInt("CategoryType",type);
            fragment.setArguments(bundle);
            mFragmentTitleList.add(title);
            mFragmentList.add(fragment);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void changeFragmentForTab(Fragment targetFragment) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("CategoryType",1);
            targetFragment.setArguments(bundle);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.viewpager, targetFragment, "fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
