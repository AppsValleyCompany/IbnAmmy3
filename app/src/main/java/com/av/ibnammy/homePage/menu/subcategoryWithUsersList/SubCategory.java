package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aya Mahmoud  on 3/20/2018.
 */

public class SubCategory implements Section<CousinAccount> {
    public String  subCategoryName ;
    public ArrayList<CousinAccount> cousinAccounts;

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public ArrayList<CousinAccount> getCousinAccounts() {
        return cousinAccounts;
    }

    public void setCousinAccounts(ArrayList<CousinAccount> cousinAccounts) {
        this.cousinAccounts = cousinAccounts;
    }

    public ArrayList<CousinAccount> filterCousins(String searchText){
        ArrayList<CousinAccount> cousinAccounts2=new ArrayList<>();
        for(CousinAccount cousinAccount : getCousinAccounts()){
            if (cousinAccount.getCousinName().toLowerCase().contains(searchText) ||
                    cousinAccount.getCousinJob().toLowerCase().contains(searchText))
            {
                cousinAccounts2.add(cousinAccount);
            }

        }
        return cousinAccounts2;
    }

    @Override
    public List<CousinAccount> getChildItems() {
        return cousinAccounts;
    }
}
