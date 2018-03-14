package com.av.ibnammy.homePage.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;


/**
 * Created by Maiada on 12/26/2017.
 */

public class CategoriesFragment extends Fragment {

    RecyclerView recyclerViewProvideService;
    CategoriesAdapter provideServiceAdapter;

    int [] itemOFIconsEmployee = {
            R.mipmap.ic_accountants,
            R.mipmap.ic_logo,
            R.mipmap.ic_engineers,
            R.mipmap.ic_doctors,
            R.mipmap.ic_lawyers,
            R.mipmap.ic_logo
    };

    int [] itemOFIconsBusiness = {
            R.mipmap.ic_doctors,
            R.mipmap.ic_logo,
            R.mipmap.ic_engineers,
            R.mipmap.ic_logo,
            R.mipmap.ic_accountants,
            R.mipmap.ic_engineers
    };

    int [] itemOFIconsUnEmployee = {
            R.mipmap.ic_accountants,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
            R.mipmap.ic_logo,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_provide_service, container, false);

        int categoryType = getArguments().getInt("CategoryType");
/*

        Toast.makeText(getContext(), getTypeOfCategoryType, Toast.LENGTH_SHORT).show();*/

        recyclerViewProvideService = rooView.findViewById(R.id.list_provide_service);
        recyclerViewProvideService.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),2));
        recyclerViewProvideService.setHasFixedSize(true);

        provideServiceAdapter = new CategoriesAdapter( this,getResources().getStringArray(R.array.EmplyeeList),itemOFIconsEmployee,"1");
        recyclerViewProvideService.setAdapter(provideServiceAdapter);

        if(categoryType==1){
          /* provideServiceAdapter = new ProvideServiceAdapter(this,getResources().getStringArray(R.array.EmplyeeList),itemOFIconsEmployee,"1");
           recyclerViewProvideService.setAdapter(provideServiceAdapter);*/
            provideServiceAdapter.notifyDataSetChanged();

        }else{
            if(categoryType==2){
                provideServiceAdapter = new CategoriesAdapter(this,getResources().getStringArray(R.array.UnEmplyeeList),itemOFIconsUnEmployee,"2");
                recyclerViewProvideService.setAdapter(provideServiceAdapter);
            }else{

                provideServiceAdapter = new CategoriesAdapter(this,getResources().getStringArray(R.array.BusinessManList),itemOFIconsBusiness,"3");
                recyclerViewProvideService.setAdapter(provideServiceAdapter);
            }

        }

        return rooView;
    }


    public static CategoriesFragment newInstance(int setTypeOfCategoryType) {

        CategoriesFragment f = new CategoriesFragment();
        Bundle b = new Bundle();
        b.putInt("CategoryType", setTypeOfCategoryType);
        f.setArguments(b);
        return  f;
    }
}
