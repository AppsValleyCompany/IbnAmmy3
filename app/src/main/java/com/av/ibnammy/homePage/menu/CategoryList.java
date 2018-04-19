package com.av.ibnammy.homePage.menu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Maiada on 3/17/2018.
 */

public class CategoryList implements Serializable {
    public ArrayList<Category> categoryArrayList ;

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public void setCategoryArrayList(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }
}
