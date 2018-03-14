package com.av.ibnammy.updateUserData;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.av.ibnammy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyDataFragment extends Fragment {


    public FamilyDataFragment() {
        // Required empty public constructor
    }

    Button add_brother_btn,add_sister_btn,add_son_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_family_data, container, false);
        add_brother_btn=v.findViewById(R.id.add_brother_btn);
        add_sister_btn=v.findViewById(R.id.add_sister_btn);
        add_son_btn=v.findViewById(R.id.add_son_btn);

        Typeface font = Typeface.createFromAsset( getActivity().getAssets(), "fontawesome-webfont.ttf" );
        add_brother_btn.setTypeface(font);
        add_sister_btn.setTypeface(font);
        add_son_btn.setTypeface(font);

        return v;
    }

}
