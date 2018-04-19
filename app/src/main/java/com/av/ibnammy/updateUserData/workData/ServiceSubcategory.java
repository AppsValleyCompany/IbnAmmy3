package com.av.ibnammy.updateUserData.workData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mina on 3/25/2018.
 */

public class ServiceSubcategory {

    @SerializedName(value = "Service_SubCategoryID")
    String Service_SubCategoryID;

    String Name;


    public String getService_SubCategoryID() {
        return Service_SubCategoryID;
    }

    public String getName() {
        return Name;
    }
    @Override
    public String toString() {
        return this.getName();
    }
}
