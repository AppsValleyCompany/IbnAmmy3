package com.av.ibnammy.homePage.favourite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentFavouriteBinding;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.SubCategory;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.SubCategorySectionAdapter;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.CousinProfileModel;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maiada on 1/2/2018.
 */

public class FavouriteFragment extends Fragment implements
        GetCallback.GetAllFavouriteAccount {

     static FragmentFavouriteBinding fragmentFavouriteBinding;
     private View rootView;


     private FavouriteListAdapter favouriteListAdapter;

     CousinProfileModel  cousinProfileModel = new CousinProfileModel();
    //RecyclerView recyclerViewFavourite;

     private String phone,password;


    private ArrayList<CousinAccount> favouriteList;
    private ArrayList<CousinAccount> favouriteListFiltered;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFavouriteBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_favourite,container,false);
        rootView = fragmentFavouriteBinding.getRoot();



        Setup_UI();




        return rootView;
    }

    private void Setup_UI() {
        Bundle bundle = CommonUtils.loadCredentials(getContext());
        phone         = bundle.getString(Constants.PHONE_KEY);
        password      = bundle.getString(Constants.PASSWORD_KEY);

        disableEditText(fragmentFavouriteBinding.searchBar);

        showProgressBar();

        cousinProfileModel.getAllFavourite(this,phone,password);

        fragmentFavouriteBinding.listFavourite.setLayoutManager(new LinearLayoutManager(getActivity()));

        fragmentFavouriteBinding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

               searchOnFavouriteList(s.toString());

            }
        });



    }

    @Override
    public void onGetAllFavouriteSuccess(ArrayList<CousinAccount> cousinAccounts) {
        hideProgressBar();
        if(cousinAccounts.size()!=0){

            favouriteList = cousinAccounts;
            favouriteListFiltered = cousinAccounts;

            enableEditText(fragmentFavouriteBinding.searchBar);


            fragmentFavouriteBinding.tvNotActivatedOne.setVisibility(View.GONE);

            favouriteListAdapter = new FavouriteListAdapter(getContext(),cousinAccounts,cousinProfileModel,phone,password);
            fragmentFavouriteBinding.listFavourite.setAdapter(favouriteListAdapter);
            favouriteListAdapter.notifyDataSetChanged();


        }else {
            fragmentFavouriteBinding.tvNotActivatedOne.setVisibility(View.VISIBLE);
            disableEditText(fragmentFavouriteBinding.searchBar);

        }

    }

    @Override
    public void onGetAllFavouriteFailure() {
        onDataFailure();
    }

    private void onDataFailure(){
        fragmentFavouriteBinding.tvNotActivatedOne.setVisibility(View.VISIBLE);
        hideProgressBar();
        if(fragmentFavouriteBinding.getRoot().getRootView()!=null)
            Snackbar.make(fragmentFavouriteBinding.getRoot().getRootView(),getResources().getString(R.string.error_favourite),Snackbar.LENGTH_LONG).show();

    }

    private void searchOnFavouriteList(String searchText) {
        ArrayList<CousinAccount> cousinAccountsFilterList = getFilter(searchText.toString());

        favouriteListAdapter = new FavouriteListAdapter(getContext(),cousinAccountsFilterList,cousinProfileModel,phone,password);
        fragmentFavouriteBinding.listFavourite.setAdapter(favouriteListAdapter);
        favouriteListAdapter.notifyDataSetChanged();


        if (cousinAccountsFilterList.size() != 0) {
            fragmentFavouriteBinding.tvSearchResult.setVisibility(View.GONE);
        } else {
            fragmentFavouriteBinding.tvSearchResult.setVisibility(View.VISIBLE);
        }


    }

    public ArrayList<CousinAccount> getFilter(String searchText) {

        String charString = searchText.toString();
        if (charString.isEmpty()) {
            favouriteListFiltered = favouriteList;
        } else {
            ArrayList<CousinAccount> filteredList = new ArrayList<>();
            for (CousinAccount row : favouriteList) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (row.getCousinName().contains(searchText) || row.getCousinJob().contains(searchText)) {
                    filteredList.add(row);
                }
            }

            favouriteListFiltered = filteredList;
        }
        return  favouriteListFiltered;
    }

    private void showProgressBar(){
        fragmentFavouriteBinding.pbLoading.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        fragmentFavouriteBinding.pbLoading.setVisibility(View.GONE);
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);

    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setFocusableInTouchMode(true);

    }
}
