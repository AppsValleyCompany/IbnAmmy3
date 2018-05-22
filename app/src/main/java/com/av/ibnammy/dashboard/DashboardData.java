package com.av.ibnammy.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mina on 4/19/2018.
 */
public class DashboardData {
    String AccountsSignUp,AccountsUpdate,Followers,TotalAccountAndFollowers;

    @SerializedName("ListCountry")
    ArrayList<CountryListItem> countriesData;

    public String getAccountsSignUp() {
        return AccountsSignUp;
    }

    public String getAccountsUpdate() {
        return AccountsUpdate;
    }

    public String getFollowers() {
        return Followers;
    }

    public String getTotalAccountAndFollowers() {
        return TotalAccountAndFollowers;
    }

    public ArrayList<CountryListItem> getCountriesData() {
        return countriesData;
    }
}
