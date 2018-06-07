package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.av.ibnammy.R;
import com.av.ibnammy.databinding.ActivitySubcategoryBinding;
import com.av.ibnammy.networkUtilities.GetCallback;

import java.util.ArrayList;
import java.util.List;


public class subcategoryListActivity extends AppCompatActivity {

    private ActivitySubcategoryBinding activitySubcategoryBinding;
    private SubCategorySectionAdapter subCategorySectionAdapter;
    private int categoryType;

    private List<SubCategory> sectionItemListSearch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_subcategory);

        activitySubcategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_subcategory);

        Setup_UI();

    }

    private void Setup_UI() {

        setSupportActionBar(activitySubcategoryBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        activitySubcategoryBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        activitySubcategoryBinding.txtTitle.setText(getIntent().getStringExtra("CategoryName"));

        activitySubcategoryBinding.rvSubcategory.setLayoutManager(new LinearLayoutManager(this));
        activitySubcategoryBinding.rvSubcategory.setHasFixedSize(true);

        categoryType = getIntent().getIntExtra("CategoryType", 0);

        Update_UI();

    }

    private void Update_UI() {


        String getCategoryID = getIntent().getStringExtra("CategoryID");

        GetAllSubCategoryWithServiceType(getCategoryID);
        disableEditText(activitySubcategoryBinding.searchBar);

        activitySubcategoryBinding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                   if(s.length()!=0)
                   searchOnSubcategoryList(s.toString());

            }
        });

    }

    private void GetAllSubCategoryWithServiceType(String categoryId) {

        showProgressBar();

        SubcategoryModel.GetAllSubCategory(new GetCallback.SubCategoryCallBack() {
            @Override
            public void onSuccess(ArrayList<SubCategory> subCategoryArrayList) {
                hideProgressBar();

                if (subCategoryArrayList.size() != 0) {
                    activitySubcategoryBinding.tvErrorData.setVisibility(View.GONE);
                    sectionItemListSearch = subCategoryArrayList;
                    subCategorySectionAdapter = new SubCategorySectionAdapter(getApplicationContext(), subCategoryArrayList, categoryType);
                    activitySubcategoryBinding.rvSubcategory.setAdapter(subCategorySectionAdapter);
                    subCategorySectionAdapter.notifyDataSetChanged();
                    enableEditText(activitySubcategoryBinding.searchBar);
                } else {
                    activitySubcategoryBinding.tvErrorData.setVisibility(View.VISIBLE);
                    disableEditText(activitySubcategoryBinding.searchBar);
                }


            }

            @Override
            public void onFailure(String throwable) {
                hideProgressBar();
                activitySubcategoryBinding.tvErrorData.setVisibility(View.VISIBLE);

            }
        }, categoryId);

    }

    private void showProgressBar() {
        activitySubcategoryBinding.pbLoading.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        activitySubcategoryBinding.pbLoading.setVisibility(View.GONE);
    }

    private void searchOnSubcategoryList(String searchText) {

        List<SubCategory> subCategories = getFilter(searchText.toString());

        subCategorySectionAdapter = new SubCategorySectionAdapter(this, subCategories, categoryType);
        activitySubcategoryBinding.rvSubcategory.setAdapter(subCategorySectionAdapter);
        subCategorySectionAdapter.notifyDataSetChanged();

        if (subCategories.size() != 0) {
            activitySubcategoryBinding.tvSearchResult.setVisibility(View.GONE);
        } else {
            activitySubcategoryBinding.tvSearchResult.setVisibility(View.VISIBLE);
        }


    }

    public List<SubCategory> getFilter(String searchText) {
        List<SubCategory> filteredList = new ArrayList<>();
        if (searchText.isEmpty()) {
            return sectionItemListSearch;
        } else {
            for (SubCategory row : sectionItemListSearch) {
                if (row.getSubCategoryName().contains(searchText)) {
                    filteredList.add(row);
                } else {
                    if(row.filterCousins(searchText).size()>0) {
                        SubCategory subCategory =new SubCategory();
                        subCategory.setCousinAccounts(row.filterCousins(searchText));
                        subCategory.setSubCategoryName(row.getSubCategoryName());
                        filteredList.add(subCategory);
                    }
                }
            }
        }

        return filteredList;
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