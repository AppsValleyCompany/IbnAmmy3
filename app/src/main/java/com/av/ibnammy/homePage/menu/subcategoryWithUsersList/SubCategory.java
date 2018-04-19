package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maiada on 3/20/2018.
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



    @Override
    public List<CousinAccount> getChildItems() {
        return cousinAccounts;
    }
}
