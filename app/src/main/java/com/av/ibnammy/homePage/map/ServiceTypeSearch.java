package com.av.ibnammy.homePage.map;

/**
 * Created by Maiada on 6/14/2018.
 */

public class ServiceTypeSearch {
    private String serviceTypeID;
    private String name;

    public ServiceTypeSearch() {
    }

    public ServiceTypeSearch(String name) {
        this.name = name;
    }

    public ServiceTypeSearch(String serviceTypeID, String name) {
        this.serviceTypeID = serviceTypeID;
        this.name = name;
    }

    public String getService_typeID() {
        return serviceTypeID;
    }

    public void setService_typeID(String serviceCategoryID) {
        this.serviceTypeID = serviceCategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
