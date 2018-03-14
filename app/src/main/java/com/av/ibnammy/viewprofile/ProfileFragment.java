package com.av.ibnammy.viewprofile;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.updateUserData.UpdateUserData;
import com.av.ibnammy.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }
FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View v= inflater.inflate(R.layout.fragment_profile, container, false);

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        View v=binding.getRoot();

       // btn_go_to_update_page=v.findViewById(R.id.btn_go_to_update_page);

        binding.btnGoToUpdatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateUserData.class));
            }
        });


        return v;
    }




}
