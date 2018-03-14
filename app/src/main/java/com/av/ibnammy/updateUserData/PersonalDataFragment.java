package com.av.ibnammy.updateUserData;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.PersonalDataFragmentBinding;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalDataFragment extends Fragment {
    PersonalDataFragmentBinding binding;
     public static final int PICK_IMAGE = 1;
        ImageView change_profile_iv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View v=inflater.inflate(R.layout.personal_data_fragment, container, false);
      //  mStepsView=  v.findViewById(R.id.stepsView);
        binding= DataBindingUtil.inflate(inflater,R.layout.personal_data_fragment, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();


       // change_profile_iv=v.findViewById(R.id.change_profile_iv);
        binding.changeProfileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pick_from_gallery();
            }
        });
        return view;
    }

        public void pick_from_gallery()
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
           // Uri uri=;
            if(data!=null) {
                Picasso.with(getContext()).load(data.getData()).transform(new CropCircleTransformation()).into(binding.changeProfileIv);
            }//TODO: action
        }
    }
}
