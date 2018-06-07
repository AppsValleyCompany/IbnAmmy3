package com.av.ibnammy.networkUtilities;


import java.util.ArrayList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Mina on 1/10/2018.
 */

public interface ApiInterface {
    //@GET("/api/users?")
    @POST("AccountServices/Signin_Account/Login")
    Call<String> loginApi(@Body String data);

    @POST("AccountServices/SignUp_Account/CreateAccount")
    Call<String> signUpApi(@Body String data);

    @POST("AccountServices/Category_TypeWithService_Category/GetAllCategoriesTypeWithService_Category")
    Call<String> categoryApi();

    @POST ("AccountServices/Update_Account/AccountData")
    Call<String> updateAccountApi(@Body String data);

    @POST ("AccountServices/Service_CategoriesAndCountries/GetAll")
    Call<String> getDDListsApi();

    @POST ("AccountServices/Service_Type/GetAllServiceTypes")
    Call<String> getServiceTypesApi();

    @POST("AccountServices/Service_SubCategoryWithAccount/GetAllSubCategoryWithAccounts")
    Call<String> subCategoryApi(@Query("Service_CategoryID")String categoryId);

    @POST("AccountServices/Account/GetAccountData")
    Call <String>getUserData(@Body String data);

    @POST("AccountServices/Followers/AddFamily")
    Call <String> addFollowers(@Body String data);

    @POST("AccountServices/DeleteFollower/DeleteFollower")//"{'Mobile':1234567890,'Password':'test','Account_FollowerID':5}"
    Call <String> deleteFollower(@Body String data);

    @POST("AccountServices/Follower/GetFollowers")
    Call <String> getFollowers(@Body String data);

    @Multipart
    @POST("AccountServices/Add_IMGs/Work_IMGs")
    Call<String> uploadImagesToServer(@Query("accountID") String id, @Part ArrayList<MultipartBody.Part> file);

    @POST("AccountServices/Forgot/ForgotAccount")
    Call<String> resetPasswordApi(@Body String data);

    @POST("AccountServices/Statistic/StatisticAccount")
    Call<String> getStatisticsApi();

    @POST("AccountServices/Delete_IMGs/Delete_Work_IMGs")
    Call<String> deletePhoto(@Query("accountID")String accountId,@Query("ImgNumber")int imgNumber);

    @Multipart
    @POST("AccountServices/Update_IMGs/Update_Work_IMGs")
    Call<String> updatePhoto(@Query("accountID")String accountId,@Query("ImgNumber")int imgNumber,@Part MultipartBody.Part file);

    @POST("AccountServices/Delete_Video/Delete_Work_Video")
    Call<String> deleteVideo(@Query("accountID")String accountId);

    @POST("AccountServices/ReNewPassword/UpdatePassword")
    Call<String> updatePassword(@Body String data);

}
