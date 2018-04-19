package com.av.ibnammy.updateUserData.workData;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mina on 3/25/2018.
 */

public class ServiceCategory {

    String Service_CategoryID,Category_TypeID,Name;

    @SerializedName(value = "classService_SubCategories")
    ArrayList<ServiceSubcategory> subcategories;

    public String getService_CategoryID() {
        return Service_CategoryID;
    }

    public String getCategory_TypeID() {
        return Category_TypeID;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<ServiceSubcategory> getSubcategories() {
        return subcategories;
    }
    @Override
    public String toString() {
        return this.getName();
    }
}
