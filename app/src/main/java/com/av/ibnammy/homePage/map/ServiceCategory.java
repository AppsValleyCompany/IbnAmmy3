package com.av.ibnammy.homePage.map;

/**
 * Created by Maiada on 6/14/2018.
 */

public class ServiceCategory {
    private String serviceCategoryID;
    private String name;

    public ServiceCategory() {
    }

    public ServiceCategory(String name) {
        this.name = name;
    }

    public ServiceCategory(String serviceCategoryID, String name) {
        this.serviceCategoryID = serviceCategoryID;
        this.name = name;
    }

    public String getServiceCategoryID() {
        return serviceCategoryID;
    }

    public void setServiceCategoryID(String serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
