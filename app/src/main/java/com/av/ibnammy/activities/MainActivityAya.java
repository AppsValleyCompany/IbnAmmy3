package com.av.ibnammy.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.changePassword.ChangePasswordFragment;
import com.av.ibnammy.dashboard.DashBoardFragment;
import com.av.ibnammy.homePage.HomePageFragment;
import com.av.ibnammy.updateUserData.UpdateDataActivity;
import com.av.ibnammy.viewprofile.Profile;
import com.av.ibnammy.viewprofile.ProfileFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;

public class MainActivityAya extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static TextView mTitle;
    NavigationView navigationView;
    private Profile getProfileData;
    private TextView  textViewVersionName,userName;
    private ImageView userImage;
    private Button sign_out_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();

        changeFragmentForSideMenu1(new HomePageFragment());
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // set My custom toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);// Display title name

        mTitle = (TextView)findViewById(R.id.txt_title);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);// to access any item on navigation view
       // getData();
        View view            = navigationView.getHeaderView(0);
        userName             = view.findViewById(R.id.tv_user_name);
        userImage            = view.findViewById(R.id.img_user);
        textViewVersionName  = findViewById(R.id.tv_version_name);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pInfo.versionName;
            textViewVersionName.setText("الإصدار "+versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        sign_out_btn=findViewById(R.id.sign_out_btn);


        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityAya.this.finish();
            }
        });


        Bundle data=intent.getBundleExtra("Login_Data");
           if (data != null) {
               String fName = data.getString("fName").replaceAll("\n","");
               String sName = data.getString("sName").replaceAll("\n","");
               if (!fName.equals("") || !sName.equals("")) {
                   String photo = data.getString("img");
                   String name = fName + " " + sName;
                   userName.setText(name);
                   Glide.with(this).applyDefaultRequestOptions(new RequestOptions().error(R.mipmap.male)).load(IMG_URL + photo).into(userImage);
               }

           }
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
            startActivity(new Intent(MainActivityAya.this,UpdateDataActivity.class));
        }
/*        else if(id == R.id.nav_my_ad) {
            //  changeFragmentForSideMenu(new MyPageFragment());
            //mTitle.setText("دفع الاشتراك");
            startActivity(new Intent(MainActivityAya.this,PaymentActivity.class));
        }*/
        else if(id == R.id.nav_my_alert) {
            //  changeFragmentForSideMenu(new MyPageFragment());
            mTitle.setText(item.getTitle());
            //startActivity(new Intent(MainActivityAya.this,DashBoardActivity.class));
            changeFragmentForSideMenu1(new DashBoardFragment());
        }else if(id==R.id.nav_change_password){
              mTitle.setText(item.getTitle());
              changeFragmentForSideMenu1(new ChangePasswordFragment());

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


   /* private void  getData(){

        Bundle bundle=CommonUtils.loadCredentials(this);
        String phone=bundle.getString(Constants.PHONE_KEY);
        String password=bundle.getString(Constants.PASSWORD_KEY);

        ProfileModel.GetProfileData(phone, password, new GetCallBack.ProfileCallBack() {
            @Override
            public void onSuccess(Profile profile) {
                getProfileData = profile;
                updateData(getProfileData);
            }

            @Override
            public void onFailure(String error) {
            }
        });

    }

    private void updateData(Profile profile){

        if(profile!=null){

            if(!profile.getProfileImage().equals("")) {



                Glide.with(this).load(IMG_URL + profile.getProfileImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                //binding.pbLoadingPhoto.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                // binding.pbLoadingPhoto.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userImage);
            }

            userName.setText(profile.getUserName());


        }
    }*/

}
