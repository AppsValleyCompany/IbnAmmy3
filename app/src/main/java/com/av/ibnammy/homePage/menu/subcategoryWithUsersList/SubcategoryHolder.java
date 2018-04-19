package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.av.ibnammy.R;

/**
 * Created by Maiada on 3/21/2018.
 */

public class SubcategoryHolder extends RecyclerView.ViewHolder {

    TextView subCategoryName;

    public SubcategoryHolder(View itemView) {
        super(itemView);
        subCategoryName = itemView.findViewById(R.id.tv_subcategory_name);
    }



}
