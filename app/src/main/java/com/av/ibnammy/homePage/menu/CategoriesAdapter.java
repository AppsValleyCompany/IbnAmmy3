package com.av.ibnammy.homePage.menu;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.subcategoryListActivity;

/**
 * Created by Maiada on 12/27/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    String [] setItemNames;
    int [] setItemIcons;
    private Fragment activity;
    String getTypeOfCategory;
    public CategoriesAdapter(Fragment fragment, String[] getItemNames, int[] getItemIcons, String typeOfCategory) {
        activity =fragment;
        setItemNames= getItemNames;
        setItemIcons= getItemIcons;
        this.getTypeOfCategory = typeOfCategory;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_provide_service,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.nameOFItem.setText(setItemNames[position]);
        holder.iconOfItem.setImageResource(setItemIcons[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(activity.getActivity().getApplicationContext(),setItemNames[position],Toast.LENGTH_SHORT).show();

                if(position==0)
                {
                    Intent h = new Intent(activity.getContext(), subcategoryListActivity.class);
                    h.putExtra("CategoryName",setItemNames[position]);
                    h.putExtra("TypeOfCategory",getTypeOfCategory);
                     activity.startActivity(h);
                 }
            }
        });


    }


    @Override
    public int getItemCount() {
        return setItemIcons.length;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameOFItem;
        ImageView iconOfItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameOFItem =   itemView.findViewById(R.id.txt_item_category);
            iconOfItem =   itemView.findViewById(R.id.img_item_category);
        }


    }

}
