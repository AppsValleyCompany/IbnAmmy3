package com.av.ibnammy.networkUtilities;

import android.os.Bundle;

import com.av.ibnammy.updateUserData.User;
import com.av.ibnammy.updateUserData.workData.DDListResponse;
import com.av.ibnammy.updateUserData.workData.ServiceType;

import java.util.ArrayList;

/**
 * Created by Mina on 2/20/2018.
 */

public abstract class GetCallback {
    public interface onLoginFinish{
        void onSuccess(Bundle bundle);
        void onFailure(String s);
    }

    public interface onSignUpFinish{
        void onSuccess(String status,String phone);
        void onFailure(String s);
    }

    public interface onUpdateFinish{
        void onUpdateSuccess(String status);
        void onUpdateFailure(String status);

    }
    public interface onDDListsFetched{
        void onDDListsFetchSuccess(DDListResponse response);
        void onDDListsFetchFailure(String s);
    }
    public interface onServiceTypesFetched{
        void onTypesFetchSuccess(ArrayList<ServiceType> response);
        void onTypesFetchFailure(String s);
    }

    public interface onUserDataFetched {
        void onGetDataSuccess(User user);
        void onGetDataFailure(String status);
    }
    public interface onResetPasswordFinish {
        void onResetPasswordSuccess(String status);
        void onResetPasswordFailure(String status);
    }
}
