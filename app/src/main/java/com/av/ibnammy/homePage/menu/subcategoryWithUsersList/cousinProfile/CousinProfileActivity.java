package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

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
    private String gender = "";


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
                    Toast.makeText(CousinProfileActivity.this, getString(R.string.not_activated), Toast.LENGTH_SHORT).show();
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

     //   Toast.makeText(this, cousinAccount.getCousinId(), Toast.LENGTH_SHORT).show();

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
                            .error(R.mipmap.male)
                            .fitCenter().transform(new CircleCrop()))
                    .load(IMG_URL+cousinAccount.getCousinImage())
                    .listener(new RequestListener<Drawable>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                      cousinProfileBinding.pbLoadPhotoProfile.setVisibility(View.GONE);
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                      cousinProfileBinding.pbLoadPhotoProfile.setVisibility(View.GONE);
                                      return false;
                                  }
                              }
                    )
                    .into(cousinProfileBinding.imgCousinPhoto);


        }else{
            cousinProfileBinding.imgCousinPhoto.setImageResource(R.mipmap.male);
            cousinProfileBinding.pbLoadPhotoProfile.setVisibility(View.GONE);

        }


        changeFragmentForTab(new CousinProfileFragment());

        gender  = cousinAccount.getGender();

      //  Toast.makeText(CousinProfileActivity.this, gender, Toast.LENGTH_SHORT).show();

        mobileNumber =  cousinAccount.getCousinMobile();
         if(mobileNumber.length()==10)
            mobileNumber = "+20"+mobileNumber;
        else
            mobileNumber = "+"+mobileNumber;

        cousinProfileBinding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if((gender.equals("أنثي")&&categoryType==2)||gender.equals("ذكر"))
                     makeCall(mobileNumber);
                 else
                     Toast.makeText(CousinProfileActivity.this, " غير مسموح بإجراء مكالمه ", Toast.LENGTH_SHORT).show();

            }
        });


        cousinProfileBinding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkIfWhatsAppInstalledAndStartChat(mobileNumber);

            }
        });


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

    private void makeCall(String mobileNumber){
        if(checkPermissions()){
            if(!mobileNumber.equals(""))
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+mobileNumber)));
        }else {
            Toast.makeText(CousinProfileActivity.this,"يجب السماح لإجراء اتصال  ", Toast.LENGTH_SHORT).show();
        }

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

    private void checkIfWhatsAppInstalledAndStartChat(String  mobileNumber){
        boolean isInstalled = whatsAppInstalledOrNot("com.whatsapp");
        if(isInstalled) {

            if ((gender.equals("أنثي") && categoryType == 2) || gender.equals("ذكر")) {

                //if (mobileNumber.startsWith("+20"))
                    startChat(mobileNumber);
               /* else
                    Toast.makeText(CousinProfileActivity.this, " غير فعال للمصريين خارج البلد  ", Toast.LENGTH_SHORT).show();
*/
            } else
                Toast.makeText(CousinProfileActivity.this, " غير مسموح بإجراء محادثة ", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(CousinProfileActivity.this, "يجب تنزيل تطبيق واتساب لاجراء محادثة", Toast.LENGTH_SHORT).show();

        }
    }
    private void startChat(String mobileNumber){
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone="+mobileNumber);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);

    }
    private boolean whatsAppInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }



}
