package com.av.ibnammy.updateUserData.familyData;

import java.util.ArrayList;

/**
 * Created by Mina on 3/31/2018.
 */

public interface FamilyCallBack {

     interface AddFamily{
        void onAddFamilySuccess(String status);
        void onAddFamilyFailure(String status);
    }

    interface GetFamily{
        void onGetFamilySuccess(ArrayList<Follower> followers);
        void onGetFamilyFailure(String status);
    }


    interface DeleteFollower{
         void onDeleteFollowerSuccess(String status);
         void onDeleteFollowerFailure(String status);
    }
}
