package com.av.ibnammy.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.MapUserProfileFragment;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.ProfileTabsFragment;


public class ProfilePageActivity extends AppCompatActivity {


    private ImageView userLocationImage,userFavourite;
    private boolean isLocation,isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        changeFragmentForBottomMenu(new ProfileTabsFragment());
        userLocationImage = findViewById(R.id.img_user_location);
        userFavourite     = findViewById(R.id.img_favourite);
        userLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(!isLocation){

                       changeFragmentForBottomMenu(new MapUserProfileFragment());
                       userLocationImage.setImageDrawable(getResources().getDrawable(R.mipmap.ic_marker_orange));
                       isLocation=true;
                 }else{
                      changeFragmentForBottomMenu(new ProfileTabsFragment());
                      userLocationImage.setImageDrawable(getResources().getDrawable(R.mipmap.ic_marker_white));
                     isLocation=false;


                 }
            }
        });
         userFavourite.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(!isFavourite){
                 //    changeFragmentForBottomMenu(new MyPageFragment());
                     userFavourite.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favourite));
                     isFavourite=true;
                 }else{
                   //  changeFragmentForBottomMenu(new ProfileTabsFragment());
                     userFavourite.setImageDrawable(getResources().getDrawable(R.mipmap.ic_unfavourite));
                     isFavourite=false;

                 }
             }
         });



    }

    private void changeFragmentForBottomMenu(Fragment targetFragment){
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tab_section, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
