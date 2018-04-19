package com.av.ibnammy.updateUserData;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.ActivityUpdateUserDataBinding;
import com.av.ibnammy.updateUserData.personalData.PersonalDataFragment;

public class UpdateDataActivity extends AppCompatActivity {

    //StepsView mStepsView;
    private final String[] labels = {"بيانات شخصية", " العمل"," الأسرة"};
    int i=0;
   // Button nextStep_btn;
    ImageView back_btn;

    static ActivityUpdateUserDataBinding binding;
    UpdateDataView dataView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_update_user_data);
         binding= DataBindingUtil.setContentView(this,R.layout.activity_update_user_data);
         dataView=new PersonalDataFragment();
       // mStepsView =  findViewById(R.id.stepsView);

        addStep(i,this);
        replaceFragment1(new PersonalDataFragment());
        back_btn=findViewById(R.id.back_icon);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    public void addStep(int i, Context context)
    {
        binding.stepsView.setLabels(labels)
                .setBarColorIndicator(context.getResources().getColor(R.color.material_blue_grey_800))
                .setProgressColorIndicator(context.getResources().getColor(R.color.light_orange))
                .setLabelColorIndicator(context.getResources().getColor(R.color.light_orange))
                .setCompletedPosition(i)
                .drawView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(binding.stepsView.getCompletedPosition()>0) //or get current fragment and compare
         addStep(binding.stepsView.getCompletedPosition()-1,getApplicationContext());


    }

    private void replaceFragment(Fragment targetFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_holder_update_data, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("")
                .commit();
    }

    private void replaceFragment1(Fragment targetFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_holder_update_data, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
