package com.av.ibnammy.updateUserData.workData;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mina on 3/25/2018.
 */

public class DDListResponse {
    @SerializedName(value = "ClassCategory_Types")
    ArrayList<CategoryType> types;

    public ArrayList<CategoryType> getTypes() {
        return types;
    }
}
