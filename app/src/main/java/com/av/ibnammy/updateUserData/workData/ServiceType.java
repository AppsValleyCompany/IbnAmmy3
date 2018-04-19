package com.av.ibnammy.updateUserData.workData;

/**
 * Created by Mina on 3/25/2018.
 */

public class ServiceType {
    String Service_TypeID,Name;

    public String getService_TypeID() {
        return Service_TypeID;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
