package com.av.ibnammy.dashboard;

/**
 * Created by Mina on 4/19/2018.
 */

public class CountryListItem {
    String Name,NumberAccounts,NumberFollowers,TotalAccountAndFollowers;

    public String getName() {
        return Name;
    }

    public String getNumberAccounts() {
        return NumberAccounts;
    }

    public String getNumberFollowers() {
        return NumberFollowers;
    }

    public String getTotalAccountAndFollowers() {
        return TotalAccountAndFollowers;
    }

    @Override
    public String toString() {
        return getName();
    }
}
