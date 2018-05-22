package com.av.ibnammy.viewprofile;


import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentProfileBinding;
import com.av.ibnammy.homePage.menu.GetCallBack;
import com.av.ibnammy.updateUserData.UpdateDataActivity;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }
    FragmentProfileBinding binding;
    String imageName,check_null;
    AlertDialog.Builder builder;
    ArrayList<String>datalist;

    Profile getProfileData ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        View v=binding.getRoot();

        Update_UI();
        return v;
    }

    private void Update_UI() {

        getData();

        binding.btnGoToUpdatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateDataActivity.class));
            }
        });

    }

    private void showProgressBar(){
        binding.pbLoading.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        binding.pbLoading.setVisibility(View.GONE);
    }

   private void  getData(){
       showProgressBar();
       Bundle bundle= CommonUtils.loadCredentials(getContext());
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
                hideProgressBar();
                builder=new AlertDialog.Builder(getContext());
                View mview=getLayoutInflater().inflate(R.layout.no_internt_dialog,null);
                builder.setView(mview);
                final AlertDialog dialog=builder.create();
                dialog.show();

                Button ok=dialog.findViewById(R.id.ok_bt_dialog);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

   }

   private void updateData(Profile profile){

       hideProgressBar();
       if(profile!=null){

           if(!profile.getProfileImage().equals("")){
               if(getContext()!=null)
                   Glide.with(getContext()).load(IMG_URL + profile.getProfileImage())
                           .listener(new RequestListener<Drawable>() {
                               @Override
                               public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                   binding.pbLoadingPhoto.setVisibility(View.GONE);
                                   return false;
                               }

                               @Override
                               public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                   binding.pbLoadingPhoto.setVisibility(View.GONE);
                                   return false;
                               }
                           }).into(binding.profileImageProfileFrag);
           }

            else
               binding.pbLoadingPhoto.setVisibility(View.GONE);






           if(!profile.getFullUserName().equals(""))
           binding.nameTvProfileFrag.setText(profile.getFullUserName());
           else
           binding.nameTvProfileFrag.setText(" لا يوجد");

           binding.phoneTvProfileFrag.setText(profile.getMobile());
           binding.idTvProfileFrag.setText(profile.getAccountId());
           binding.accStatusTvProfileFrag.setText(profile.getSubscriptionStatus());
           binding.payStatusTvProfileFrag.setText(profile.getActive());
           binding.numOfFollowersTvProfileFrag.setText(profile.getFollowersNumber());

       }
   }


       /* stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data_get=response.body();
                hideProgressBar();
                try
                {
                    JSONObject jsonObject=new JSONObject(data_get);
                 *//*
                    String check[]={"First_Name","AccountID",
                            "Mobile","Subscription_status","Active","Followers_Number"};
                    TextView ui[]={
                            binding.nameTvProfileFrag, binding.idTvProfileFrag,
                            binding.phoneTvProfileFrag, binding.accStatusTvProfileFrag,
                            binding.payStatusTvProfileFrag,binding.numOfFollowersTvProfileFrag };

                    for(int i=0;i<=jsonObject.length();i++)
                    {
                        if(jsonObject.has(check[i])==true)
                        {
                            check_null=jsonObject.getString(check[i]);
                            if(!check_null.equals("null"))
                            {
                                ui[i].setText(check_null);
                            }
                            else
                            {
                                ui[i].setText("لايوجد");}}}*//*


                    if(!jsonObject.getString("First_Name").equals("null")||
                        !jsonObject.getString("Second_Name").equals("null")
                            ){
                        binding.nameTvProfileFrag.setText(jsonObject.getString("First_Name")+" "
                                +jsonObject.getString("Second_Name")+" "+jsonObject.getString("Third_Name")+" "
                                +jsonObject.getString("Forth_Name"));
                    }else {
                        binding.nameTvProfileFrag.setText("لا يوجد");
                    }


                    binding.idTvProfileFrag.setText(jsonObject.getString("AccountID"));
                    if(!jsonObject.getString("Mobile").equals("null"))
                    {
                        binding.phoneTvProfileFrag.setText(jsonObject.getString("Mobile"));
                    }
                    else
                    {
                        binding.phoneTvProfileFrag.setText("لا يوجد");
                    }
                    //
                    if(!jsonObject.getString("Subscription_status").equals(false))
                    {
                        binding.accStatusTvProfileFrag.setText("تم التفعيل ");
                    }
                    else
                    {
                        binding.accStatusTvProfileFrag.setText(" لم يتم التفعيل");
                    }
                    //
                    if(!jsonObject.getString("Active").equals(false))
                    {
                        binding.payStatusTvProfileFrag.setText("تم التفعيل ");
                    }
                    else
                    {
                        binding.payStatusTvProfileFrag.setText("لم يتم التفعيل ");
                    }
                    //
                    if(!jsonObject.getString("Followers_Number").equals("null"))
                    {
                        binding.numOfFollowersTvProfileFrag.setText(jsonObject.getString("Followers_Number"));
                    }
                    else
                    {
                        binding.numOfFollowersTvProfileFrag.setText("لا يوجد ");
                    }

                    imageName=jsonObject.getString("Profile_IMG");

                  //  Picasso.with(getContext()).load(IMG_URL + imageName).placeholder(R.mipmap.profile_icon).into(binding.profileImageProfileFrag);



                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {


            }
        });*/

}
