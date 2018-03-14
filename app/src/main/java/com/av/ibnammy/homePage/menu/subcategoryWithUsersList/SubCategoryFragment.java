package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.MenuListAdapter;


public class SubCategoryFragment extends Fragment {


    private TextView sectionCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RecyclerView recyclerViewCategory;
    MenuListAdapter menuListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sub_category, container, false);



        sectionCategory = v.findViewById(R.id.txt_section_category);

        String getTypeOfCategory = getArguments().getString("typeOfCategory");
        if(getTypeOfCategory.equals("1")){
            sectionCategory.setText("بنوك خاصه");

        }else if(getTypeOfCategory.equals("2")){
            sectionCategory.setText("جامعة");

        }else {
            sectionCategory.setText("مستشفيات");
        }


        menuListAdapter = new MenuListAdapter(getActivity(),getTypeOfCategory);

        recyclerViewCategory = (RecyclerView) v.findViewById(R.id.category_list);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setAdapter(menuListAdapter);

        return  v;


    }

}
