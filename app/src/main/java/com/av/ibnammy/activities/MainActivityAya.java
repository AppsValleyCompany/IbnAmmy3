package com.av.ibnammy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.dashboard.DashBoardFragment;
import com.av.ibnammy.homePage.HomePageFragment;
import com.av.ibnammy.viewprofile.ProfileFragment;
import com.av.ibnammy.payment.PaymentActivity;
import com.av.ibnammy.updateUserData.UpdateUserData;

public class MainActivityAya extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static TextView mTitle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragmentForSideMenu1(new HomePageFragment());



        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // set My custom toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);// Display title name

         mTitle = (TextView)findViewById(R.id.txt_title);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);// to access any item on navigation view

    }
    public  void openDrawer(View v){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerVisible(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }


    @Override
    public void onBackPressed() {
        Fragment myFragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.left_side_fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       else if (myFragment != null && myFragment.isVisible()) {
                if(!(myFragment instanceof HomePageFragment))
                {
                    changeFragmentForSideMenu1(new HomePageFragment());
                    navigationView.getMenu().getItem(0).setChecked(true);
                    mTitle.setText(navigationView.getMenu().getItem(0).getTitle());
                }else {
                    super.onBackPressed();
                }
                         }
        else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home_page) {
            // Handle the camera action
            mTitle.setText(item.getTitle());
           changeFragmentForSideMenu1(new HomePageFragment());

        }else if(id == R.id.nav_my_page) {
            //  changeFragmentForSideMenu(new MyPageFragment());
            mTitle.setText(item.getTitle());
            changeFragmentForSideMenu1(new ProfileFragment());
        }
        else if(id == R.id.nav_settings) {
            //  changeFragmentForSideMenu(new MyPageFragment());
            //mTitle.setText("تعديل البيانات");
            startActivity(new Intent(MainActivityAya.this,UpdateUserData.class));
        }
        else if(id == R.id.nav_my_ad) {
            //  changeFragmentForSideMenu(new MyPageFragment());
            //mTitle.setText("دفع الاشتراك");
            startActivity(new Intent(MainActivityAya.this,PaymentActivity.class));
        }
        else if(id == R.id.nav_my_alert) {
            //  changeFragmentForSideMenu(new MyPageFragment());
            mTitle.setText(item.getTitle());
            //startActivity(new Intent(MainActivityAya.this,DashBoardActivity.class));
            changeFragmentForSideMenu1(new DashBoardFragment());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragmentForSideMenu(Fragment targetFragment){
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.left_side_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("")
                .commit();
    }
    private void changeFragmentForSideMenu1(Fragment targetFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.left_side_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    //

}
