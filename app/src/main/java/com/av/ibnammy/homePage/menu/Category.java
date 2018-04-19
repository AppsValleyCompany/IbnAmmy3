package com.av.ibnammy.homePage.menu;

import java.io.Serializable;

/**
 * Created by Maiada on 3/17/2018.
 */

public class Category implements Serializable{

    public String categoryName;
    public String NumberOfMembers_ServiceCategory;
    public String Service_CategoryID;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNumberOfMembers_ServiceCategory() {
        return NumberOfMembers_ServiceCategory;
    }

    public void setNumberOfMembers_ServiceCategory(String numberOfMembers_ServiceCategory) {
        NumberOfMembers_ServiceCategory = numberOfMembers_ServiceCategory;
    }

    public String getService_CategoryID() {
        return Service_CategoryID;
    }

    public void setService_CategoryID(String service_CategoryID) {
        Service_CategoryID = service_CategoryID;
    }
}
