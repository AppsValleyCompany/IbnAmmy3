package com.av.ibnammy.homePage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.chat.ChatFragment;
import com.av.ibnammy.homePage.favourite.FavouriteFragment;
import com.av.ibnammy.homePage.map.MapFragment;
import com.av.ibnammy.homePage.menu.MenuFragment;

/**
 * Created by Maiada on 12/25/2017.
 */

public class HomePageFragment extends Fragment implements View.OnKeyListener  {

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // mTitle.setText("القائمة");
                    changeFragmentForBottomMenu(new MenuFragment());
                    return true;
                case R.id.navigation_map:
                   // mTitle.setText("الخريطة");
                    changeFragmentForBottomMenu(new MapFragment());
                    return true;
                case R.id.navigation_notification:
                   // mTitle.setText("محادثة");
                    changeFragmentForBottomMenu(new ChatFragment());
                    return true;
                case R.id.navigation_favorite:
                   // mTitle.setText("المفضلة");
                    changeFragmentForBottomMenu(new FavouriteFragment());
                    return true;
            }
            return false;
        }

    };
    BottomNavigationView navigation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        navigation = rootView
                .findViewById(R.id.nav_bottom);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(navigation.getSelectedItemId()==R.id.navigation_map){

            navigation.setSelectedItemId(R.id.navigation_home);
        }

        rootView.setOnKeyListener(this); // to access back button
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();


        changeFragmentForBottomMenu(new MenuFragment());
        return rootView;
    }



    private void changeFragmentForBottomMenu(Fragment targetFragment){
        try{
        getActivity(). getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottom_side_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onBackButtonPressed();
                return true;
            }
        }

        return false;
    }

    private void onBackButtonPressed() {
        if( navigation.getSelectedItemId()!=R.id.navigation_home)
        {
            changeFragmentForBottomMenu(new MenuFragment());
            navigation.getMenu().getItem(0).setChecked(true);
        }
        else getActivity().onBackPressed();
    }

}
