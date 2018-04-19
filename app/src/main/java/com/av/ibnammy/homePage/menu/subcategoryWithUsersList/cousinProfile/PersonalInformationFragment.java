package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentPersonalInformationBinding;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;

/**
 * Created by Maiada on 1/3/2018.
 */

public class PersonalInformationFragment  extends Fragment {


    private  FragmentPersonalInformationBinding personalInformationBinding;
    private  CousinAccount cousinAccount;
    private  int categoryType ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        personalInformationBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_personal_information,container,false);
        View rooView = personalInformationBinding.getRoot();


        categoryType  =  getArguments().getInt("CategoryType");
        cousinAccount = (CousinAccount) getArguments().getSerializable("CousinData");

        if(categoryType==2)
        {

            showMediaField();
            MediaDescription();

        }
        else
       {
              hideMediaField();
        }





        PersonalInformation();

        return rooView;
    }


    private void  PersonalInformation(){



        if(!cousinAccount.getServiceCategory().equals(""))
            personalInformationBinding.tvServiceCategory.setText(" نوع العمل :  "+" "+cousinAccount.getServiceCategory());
        else
            personalInformationBinding.tvServiceCategory.setText(" نوع العمل : لا يوجد ");


        if(!cousinAccount.getServiceSubcategory().equals(""))
            personalInformationBinding.tvServiceSubcategory.setText(" نوع المؤسسه : "+" "+cousinAccount.getServiceSubcategory());
        else
            personalInformationBinding.tvServiceSubcategory.setText(" نوع المؤسسه : لا يوجد ");


        if(!cousinAccount.getServiceName().equals(""))
            personalInformationBinding.tvServiceName.setText(" اسم المؤسسه : "+" "+cousinAccount.getServiceName());
        else
            personalInformationBinding.tvServiceName.setText(" اسم المؤسسه : لا يوجد ");


        if(!cousinAccount.getCousinMaritalStatus().equals(""))
            personalInformationBinding.tvMaritalStatus.setText(" الحالة الاجتماعية : "+" "+cousinAccount.getCousinMaritalStatus());
        else
            personalInformationBinding.tvMaritalStatus.setText(" الحالة الاجتماعية : لا يوجد ");


        if(!cousinAccount.getBirthDate().equals(""))
            personalInformationBinding.tvCousinBirthday.setText(" تاريخ الميلاد: "+" "+cousinAccount.getBirthDate());
        else
            personalInformationBinding.tvCousinBirthday.setText(" تاريخ الميلاد: لا يوجد " );



    }
    private void  MediaDescription(){

        if(!cousinAccount.getServiceDescription().equals(""))
            personalInformationBinding.tvServiceDescription.setText("وصف الخدمة: "+" "+cousinAccount.getServiceDescription());
        else
            personalInformationBinding.tvServiceDescription.setText("وصف الخدمة: لا يوجد " );


        if(!cousinAccount.getPrice().equals(""))
            personalInformationBinding.tvPrice.setText("سعر الخدمة : "+" "+cousinAccount.getPrice());
        else
            personalInformationBinding.tvPrice.setText("سعر الخدمة : لا يوجد ");


        if(!cousinAccount.getDiscount().equals(""))
            personalInformationBinding.tvDiscount.setText("نسبه الخصم: "+" "+cousinAccount.getDiscount()+"%");
        else
            personalInformationBinding.tvDiscount.setText("نسبه الخصم: لا يوجد " );

    }
    private void  showMediaField(){
        personalInformationBinding.tvServiceDescription.setVisibility(View.VISIBLE);
        personalInformationBinding.tvPrice.setVisibility(View.VISIBLE);
        personalInformationBinding.tvDiscount.setVisibility(View.VISIBLE);
    }
    private void  hideMediaField(){
        personalInformationBinding.tvServiceDescription.setVisibility(View.GONE);
        personalInformationBinding.tvPrice.setVisibility(View.GONE);
        personalInformationBinding.tvDiscount.setVisibility(View.GONE);
    }

}
