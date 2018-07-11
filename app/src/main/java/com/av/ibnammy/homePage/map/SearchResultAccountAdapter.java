package com.av.ibnammy.homePage.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.MenuListAdapter;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.CousinProfileActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;

/**
 * Created by Maiada on 6/17/2018.
 */

public class SearchResultAccountAdapter extends RecyclerView.Adapter<SearchResultAccountHolder> {

    ArrayList<CousinAccount> resultArrayList;
    Context context;
    int categoryType ;

    public SearchResultAccountAdapter(Context context, ArrayList<CousinAccount> resultArrayList) {
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public SearchResultAccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_search,parent,false);
        return new SearchResultAccountHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchResultAccountHolder holder, int position) {

      final   CousinAccount searchResult = resultArrayList.get(position);

        if(!searchResult.getCousinName().equals(""))
            holder.cousinName.setText(searchResult.getCousinName());
        else
            holder.cousinName.setText("لا يوجد");



        if(!searchResult.getCousinJob().equals(""))
           holder.cousinJob.setText(searchResult.getCousinJob());
        else
            holder.cousinJob.setText("لا يوجد");

        if(!searchResult.getCousinDistance().equals(""))
        holder.cousinDistance.setText(String.valueOf(Math.round(Float.parseFloat(searchResult.getCousinDistance())))+" km ");


        if (!searchResult.getCousinImage().equals("")){

            Glide.with(context)
                    .applyDefaultRequestOptions(new RequestOptions().error(R.mipmap.male))
                    .load(IMG_URL +searchResult.getCousinImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.cousinImage);


        }else {
            holder.cousinImage.setImageResource(R.mipmap.male);
            holder.progressBar.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int getCategoryType =  Integer.parseInt(searchResult.getCategoryTypeID());

                if(getCategoryType==1){
                    categoryType=0;
                }else if(getCategoryType==2){
                    categoryType = 1;
                }else {
                    categoryType = 2;
                }


                Intent cousinProfileIntent = new Intent(context, CousinProfileActivity.class);
                cousinProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cousinProfileIntent.putExtra("CousinDate", searchResult);
                cousinProfileIntent.putExtra("CategoryType", categoryType);
                context.startActivity(cousinProfileIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }
}