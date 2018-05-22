package com.av.ibnammy.homePage.menu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentCategoriesBinding;

import java.util.ArrayList;


/**
 * Created by Maiada on 12/26/2017.
 */

public class CategoriesFragment extends Fragment {

    CategoriesAdapter categoriesAdapter;
    private FragmentCategoriesBinding fragmentCategoriesBinding;

 /*   int [] itemOFIconsEmployee = {
            R.mipmap.ic_accountants,
            R.mipmap.ic_lawyers,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_engineers,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_doctors,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
    };

    int [] itemOFIconsUnEmployee = {
            R.mipmap.ic_accountants,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
    };

    int [] itemOFIconsBusiness = {
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_engineers,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_accountants,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_engineers,
            R.mipmap.ic_doctors,
            R.mipmap.ic_doctors,
            R.mipmap.ic_accountants
    };*/




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCategoriesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories,container,false);
        View rooView = fragmentCategoriesBinding.getRoot();

        ArrayList<Category> categoryArrayList = (ArrayList<Category>) getArguments().getSerializable("CategoryList");
        int categoryType = (int) getArguments().getSerializable("CategoryType");
        if(categoryArrayList!=null) {
            fragmentCategoriesBinding.categoryList.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
            fragmentCategoriesBinding.categoryList.setHasFixedSize(true);
            if(categoryType==0){
                categoriesAdapter = new CategoriesAdapter(this,categoryArrayList ,categoryType);
                fragmentCategoriesBinding.categoryList.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
            } else  if(categoryType==1){
               categoriesAdapter = new CategoriesAdapter(this,categoryArrayList,categoryType);
                fragmentCategoriesBinding.categoryList.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
            } else {
                categoriesAdapter = new CategoriesAdapter(this,categoryArrayList,categoryType);
                fragmentCategoriesBinding.categoryList.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
            }


        }


        return rooView;
    }




}
