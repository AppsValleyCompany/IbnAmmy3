package com.av.ibnammy.homePage.menu;

import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.SubCategory;
import com.av.ibnammy.viewprofile.Profile;

import java.util.ArrayList;

/**
 * Created by Maiada on 3/17/2018.
 */

public abstract class GetCallBack {


    public   interface  CategoryCallBack{
        void onSuccess(ArrayList<CategoryType> categoryTypeArrayList, ArrayList<CategoryList> categoryLists);
        void onFailure(String throwable);
    }

    public  interface  SubCategoryCallBack{
        void onSuccess(ArrayList<SubCategory> subCategoryArrayList);
        void onFailure(String throwable);
    }

    public interface ProfileCallBack{
        void  onSuccess(Profile profile);
        void  onFailure(String error);
    }
}
