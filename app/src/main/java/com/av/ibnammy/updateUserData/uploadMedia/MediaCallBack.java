package com.av.ibnammy.updateUserData.uploadMedia;

/**
 * Created by Maiada on 4/11/2018.
 */

public interface MediaCallBack {

    interface  uploadMediaImage{
        void  onSuccess(String success);
        void  onFailure();
    }


    interface  deleteMediaImage{
        void  onSuccess(String success);
        void  onFailure(String failure);
    }

    interface  updateMediaImage{
        void  onSuccess(String success);
        void  onFailure(String failure);
    }


    interface  deleteMediaVideo{
        void  onSuccess(String success);
        void  onFailure(String failure);
    }
}
