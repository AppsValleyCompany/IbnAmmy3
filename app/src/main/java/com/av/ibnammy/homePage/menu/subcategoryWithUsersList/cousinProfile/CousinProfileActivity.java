package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.ActivityCousinProfileBinding;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinProfileFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;

public class CousinProfileActivity extends AppCompatActivity {


    static ActivityCousinProfileBinding cousinProfileBinding;

    CousinAccount cousinAccount;

    private boolean isLocation,isFavourite;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x1;
    private int categoryType;
    private String mobileNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_cousin_profile);

        cousinProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_cousin_profile);

        Setup_UI();


    }

    private void Setup_UI() {
        cousinProfileBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cousinProfileBinding.imgCousinLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCousinLocation();
            }
        });


        cousinProfileBinding.imgCousinFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFavourite){
                    //    changeFragmentForBottomMenu(new MyPageFragment());
                    cousinProfileBinding.imgCousinFav.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favourite));
                    isFavourite=true;
                }else{
                    //  changeFragmentForBottomMenu(new ProfileTabsFragment());
                    cousinProfileBinding.imgCousinFav.setImageDrawable(getResources().getDrawable(R.mipmap.ic_unfavourite));
                    isFavourite=false;
                }
            }
        });

        Update_UI();

    }

    private void Update_UI() {

        categoryType  =  getIntent().getIntExtra("CategoryType",0);
        cousinAccount = (CousinAccount) getIntent().getSerializableExtra("CousinDate");

        if(!cousinAccount.getCousinName().equals(""))
            cousinProfileBinding.tvCousinName.setText(cousinAccount.getCousinName());

        if(!cousinAccount.getCousinJob().equals(""))
            cousinProfileBinding.tvCousinJob.setText(cousinAccount.getCousinJob());

        if(!cousinAccount.getCousinMaritalStatus().equals("")){
            if(cousinAccount.getCousinMaritalStatus().equals("باحث عن زوج(ة)")){
                cousinProfileBinding.tvCousinStatus.setVisibility(View.VISIBLE);
            }else {
                cousinProfileBinding.tvCousinStatus.setVisibility(View.GONE);
            }
        }
            cousinProfileBinding.tvCousinJob.setText(cousinAccount.getCousinJob());

        if(!cousinAccount.getCousinImage().equals("")){
            cousinProfileBinding.imgCousinPhoto.setVisibility(View.VISIBLE);

       /*      Picasso.with(this).load(IMG_URL + cousinAccount.getCousinImage()).transform((new CircleTransform()))
              .placeholder(R.drawable.ic_profile_image).centerCrop().fit().into(cousinProfileBinding.imgCousinPhoto);*/


              Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_profile_image)
                            .fitCenter().transform(new CircleCrop()))
                    .load(IMG_URL+cousinAccount.getCousinImage())
                    .into(cousinProfileBinding.imgCousinPhoto);


            cousinProfileBinding.pbLoadPhotoProfile.setVisibility(View.GONE);


        }else{
            cousinProfileBinding.imgCousinPhoto.setVisibility(View.VISIBLE);

            cousinProfileBinding.imgCousinPhoto.setImageResource(R.drawable.ic_profile_image);
            cousinProfileBinding.pbLoadPhotoProfile.setVisibility(View.GONE);

        }


        changeFragmentForTab(new CousinProfileFragment());


        mobileNumber =  cousinAccount.getCousinMobile();
        if(mobileNumber.length()==10)
            mobileNumber = "0"+mobileNumber;
        else
            mobileNumber = "00"+mobileNumber;

        cousinProfileBinding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissions()){
                      if(!mobileNumber.equals(""))
                        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+mobileNumber)));
                }else {
                    Toast.makeText(CousinProfileActivity.this,"يجب السماح لإجراء اتصال  ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void ShowCousinLocation(){
        if(!isLocation){
            changeFragmentForTab(new MapUserProfileFragment());
            cousinProfileBinding.imgCousinLocation.setImageDrawable(getResources().getDrawable(R.mipmap.ic_marker_orange));
            isLocation=true;
        }else{
            changeFragmentForTab(new CousinProfileFragment());
            cousinProfileBinding.imgCousinLocation.setImageDrawable(getResources().getDrawable(R.mipmap.ic_marker_white));
            isLocation=false;
        }
    }
    private void changeFragmentForTab(Fragment targetFragment) {

                Bundle bundle = new Bundle();
                bundle.putInt("CategoryType",categoryType);
                bundle.putSerializable("CousinData",cousinAccount);
                targetFragment.setArguments(bundle);

                 getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();


    }

    private boolean checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(CousinProfileActivity.this,
                Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
            return false;
        }else{
            return  true;
        }

    }


}
