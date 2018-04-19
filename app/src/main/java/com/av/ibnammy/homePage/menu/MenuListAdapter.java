package com.av.ibnammy.homePage.menu;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.ibnammy.R;


/**
 * Created by Maiada on 12/25/2017.
 */

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder>  {

     boolean isFavourite;
     Activity  a;
     String typeOfCategory;
    public MenuListAdapter(Activity categoryListActivity,String typeOfCategory) {
        a=categoryListActivity;
        this.typeOfCategory = typeOfCategory;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_subcategory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        if(typeOfCategory.equals("1")){

          holder.nameOfSpecialization.setText("مدير مالى");

        }else if(typeOfCategory.equals("2")){
            holder.nameOfSpecialization.setText("هندسه");

        }else {
            holder.nameOfSpecialization.setText("جراحه عامة");
        }

            holder.getUnCheckFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.getUnCheckFavourite.getVisibility()==View.VISIBLE){
                    holder.getUnCheckFavourite.setVisibility(View.GONE);
                    holder.getCheckFavourite.setVisibility(View.VISIBLE);

                }

            }
        });

        holder.getCheckFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.getCheckFavourite.getVisibility()==View.VISIBLE){
                    holder.getUnCheckFavourite.setVisibility(View.VISIBLE);
                    holder.getCheckFavourite.setVisibility(View.GONE);

                }

            }
        });

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            // subcategoryListActivity.changeFragmentForSideMenu(new CousinProfileFragment());

         }
     });


    }



    @Override
    public int getItemCount() {
        return 10;
    }





    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView   getUnCheckFavourite;
        ImageView   getCheckFavourite;
        TextView    nameOfSpecialization;

        public MyViewHolder(View itemView) {
            super(itemView);
            getUnCheckFavourite  =  itemView.findViewById(R.id.unCheckFavourite);
            getCheckFavourite    =  itemView.findViewById(R.id.checkFavourite);
            nameOfSpecialization =  itemView.findViewById(R.id.txt_job_user_name);
        }


    }


}
