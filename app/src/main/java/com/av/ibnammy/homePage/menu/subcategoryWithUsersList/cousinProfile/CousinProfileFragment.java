package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.ibnammy.R;

/**
 * Created by Mina on 1/4/2018.
 */

public class CousinProfileFragment extends Fragment {
    private ImageView userLocationImage,userFavourite;
    private TextView mUserJob;
    private boolean isLocation,isFavourite;
    String setTagOfFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_profile_page, container, false);

       setTagOfFragment = getArguments().getString("typeOfCategory");

        changeFragmentForBottomMenu(new ProfileTabsFragment());
        userLocationImage = (ImageView) v.findViewById(R.id.img_user_location);
        userFavourite     = (ImageView) v.findViewById(R.id.img_favourite);
        mUserJob          =  (TextView) v.findViewById(R.id.user_job);


        if(setTagOfFragment.equals("1")){

            mUserJob.setText("مدير مالى");

        }else if(setTagOfFragment.equals("2")){
            mUserJob.setText("هندسه");

        }else {
            mUserJob.setText("جراحه عامة");
        }

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

    return  v;

    }

    private void changeFragmentForBottomMenu(Fragment targetFragment){

        // setTypeOfCategory
        Bundle bundle_category = new Bundle();
        bundle_category.putString("typeOfCategory",setTagOfFragment);
        targetFragment.setArguments(bundle_category);

              getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tab_section, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
