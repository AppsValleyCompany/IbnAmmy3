package com.av.ibnammy.homePage.menu;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.AdapterViewBindingAdapter;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.RowListCategoryBinding;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.subcategoryListActivity;

import java.util.ArrayList;

/**
 * Created by Maiada on 12/27/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    ArrayList<Category> categoryArrayList ;
    private Fragment activity;
    int getTypeOfCategory;
    RowListCategoryBinding rowListCategoryBinding;
  //  int icons [] ;

    public CategoriesAdapter(Fragment fragment, ArrayList<Category> categories, int typeOfCategory) {
        activity =fragment;
        this.getTypeOfCategory = typeOfCategory;
        categoryArrayList = categories;
       // icons = getIcons;
            }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_category,parent,false);
        rowListCategoryBinding = DataBindingUtil.bind(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        rowListCategoryBinding.txtCategoryName.setText(categoryArrayList.get(position).getCategoryName().replace("\r\n",""));
        rowListCategoryBinding.txtMemberCategory.setText(categoryArrayList.get(position).getNumberOfMembers_ServiceCategory()+" عضو ");
     //   rowListCategoryBinding.imgItemCategory.setImageResource(icons[position]);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    int checkNumberOfMembers =Integer.parseInt(categoryArrayList.get(position).getNumberOfMembers_ServiceCategory());

                    if(checkNumberOfMembers != 0){
                        Intent subCategoryIntent = new Intent(activity.getContext(), subcategoryListActivity.class);
                        subCategoryIntent.putExtra("CategoryID",categoryArrayList.get(position).getService_CategoryID());
                        subCategoryIntent.putExtra("CategoryType",getTypeOfCategory);
                        subCategoryIntent.putExtra("CategoryName",categoryArrayList.get(position).getCategoryName());
                        activity.startActivity(subCategoryIntent);
                    }else {
                        Toast.makeText(activity.getContext(),activity.getString(R.string.no_member), Toast.LENGTH_SHORT).show();
                    }


            }
        });


    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

     /*  TextView categoryName;
        ImageView iconOfItem;
        TextView  subCategoryNumber;*/

        public MyViewHolder(View itemView) {
            super(itemView);
         /*   categoryName          =   itemView.findViewById(R.id.txt_category_name);
            iconOfItem            =   itemView.findViewById(R.id.img_item_category);
            subCategoryNumber     =   itemView.findViewById(R.id.txt_member_category);*/
        }


    }



}
