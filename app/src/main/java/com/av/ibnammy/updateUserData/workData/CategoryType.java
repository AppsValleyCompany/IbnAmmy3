package com.av.ibnammy.updateUserData.workData;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mina on 3/25/2018.
 */

public class CategoryType {
    private String Name,Category_TypeID;

    @SerializedName(value = "classService_Categories")
    ArrayList<ServiceCategory> categories;

    public String getName() {
        return Name;
    }

    public String getCategory_TypeID() {
        return Category_TypeID;
    }

    public ArrayList<ServiceCategory> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
