package com.av.ibnammy.networkUtilities;

import android.os.Bundle;

import com.av.ibnammy.dashboard.DashboardData;
import com.av.ibnammy.homePage.map.SearchResult;
import com.av.ibnammy.homePage.map.ServiceCategory;
import com.av.ibnammy.homePage.map.ServiceTypeSearch;
import com.av.ibnammy.homePage.menu.CategoryList;
import com.av.ibnammy.homePage.menu.CategoryType;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.SubCategory;
import com.av.ibnammy.updateUserData.User;
import com.av.ibnammy.updateUserData.workData.DDListResponse;
import com.av.ibnammy.updateUserData.workData.ServiceSubcategory;
import com.av.ibnammy.updateUserData.workData.ServiceType;
import com.av.ibnammy.viewprofile.Profile;

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


    public interface onGetStatistic{
        void onGetStatisticSuccess(DashboardData data);
        void onGetStatisticFailure(String s);
    }


    public   interface  CategoryCallBack{
        void onSuccess(ArrayList<CategoryType> categoryTypeArrayList, ArrayList<CategoryList> categoryLists);
        void onFailure(String throwable);
    }

    public  interface  SubCategoryCallBack{
        void onSuccess(ArrayList<SubCategory> subCategoryArrayList);
        void onFailure(String throwable);
    }

    public interface ProfileCallBack{
        void  onSuccess(Profile profile);
        void  onFailure(String error);
    }

    public interface onUpdatePassword {
        void onUpdatePasswordSuccess();
        void onUpdatePasswordError();
        void onUpdatePasswordFailure();
    }


    public interface ProfileData{
        void  onProfileDataSuccess(Profile profile);
     //   void  onProfileDataError();
        void  onProfileDataFailure();
    }

    public  interface DDServiceCategorySearch{
        void onDDServiceCategorySearchSuccess(ArrayList<ServiceCategory> serviceCategories);
   //     void onDDServiceCategorySearchError();
        void onDDServiceCategorySearchFailure();

    }
    public interface DDServiceTypeSearch{
        void onTypesFetchSuccess(ArrayList<ServiceTypeSearch> response);
        void onTypesFetchFailure();
    }
    public interface DDCountry{
        void onDDCountrySuccess(ArrayList<String> response);
        void onDDCountryFailure();
    }

    public interface DistrictsCountry{
        void onDistrictsCountrySuccess(ArrayList<String> response);
        void onDistrictsCountryFailure();
    }

    public interface AllSearchResult{
        void onSearchResultSuccess(ArrayList<CousinAccount> searchResultArrayList);
        void onSearchResultError();
        void onSearchResultFailure();
    }

    public interface AddRequestHelp{
        void onAddRequestHelpSuccess();
        void onAddRequestHelpFailure();
    }
}
