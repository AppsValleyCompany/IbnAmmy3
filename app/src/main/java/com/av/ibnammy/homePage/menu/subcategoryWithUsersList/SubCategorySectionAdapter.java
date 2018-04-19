package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.CousinProfileActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.List;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;

/**
 * Created by Maiada on 3/21/2018.
 */

public class SubCategorySectionAdapter extends SectionRecyclerViewAdapter<SubCategory,CousinAccount,SubcategoryHolder,CousinAccountHolder> /*implements Filterable*/ {
    int categoryType;
    Context context;
    List<SubCategory> sectionItemList;



    public SubCategorySectionAdapter(Context context, List<SubCategory> sectionItemList, int categoryType) {
        super(context, sectionItemList);

        this.context = context;
        this.sectionItemList = sectionItemList;
        this.categoryType = categoryType;



    }




    @Override
    public SubcategoryHolder onCreateSectionViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_list_subcategory, viewGroup, false);
        return new SubcategoryHolder(view);

    }

    @Override
    public CousinAccountHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cousin_account, viewGroup, false);
        return new CousinAccountHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SubcategoryHolder subcategoryHolder, int i, SubCategory subCategory) {
        subcategoryHolder.subCategoryName.setText(subCategory.getSubCategoryName());

    }

    @Override
    public void onBindChildViewHolder(final CousinAccountHolder cousinAccountHolder, int i, int i1, final CousinAccount cousinAccount) {


        final boolean checkAccount = cousinAccount.isHasAccount();


        if (checkAccount) {
            cousinAccountHolder.cousinData.setVisibility(View.VISIBLE);
            cousinAccountHolder.noCousinData.setVisibility(View.GONE);

            if (!cousinAccount.getCousinName().equals(""))
                cousinAccountHolder.cousinName.setText(cousinAccount.getCousinName());

            if (!cousinAccount.getCousinJob().equals(""))
                cousinAccountHolder.cousinJob.setText(cousinAccount.getCousinJob());

            if (!cousinAccount.getCousinImage().equals("")){

                Glide.with(context).load(IMG_URL +cousinAccount.getCousinImage()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        cousinAccountHolder.cousinImage.setVisibility(View.VISIBLE);
                        cousinAccountHolder.cousinImage.setImageResource(R.drawable.ic_profile_image);
                        cousinAccountHolder.progressBar.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        cousinAccountHolder.progressBar.setVisibility(View.GONE);
                        cousinAccountHolder.cousinImage.setVisibility(View.VISIBLE);

                        return false;
                    }
                }).into(cousinAccountHolder.cousinImage);



            }else {
                cousinAccountHolder.cousinImage.setVisibility(View.VISIBLE);
                cousinAccountHolder.cousinImage.setImageResource(R.drawable.ic_profile_image);
                cousinAccountHolder.progressBar.setVisibility(View.GONE);

            }




        } else {
            cousinAccountHolder.cousinData.setVisibility(View.GONE);
            cousinAccountHolder.noCousinData.setVisibility(View.VISIBLE);
            cousinAccountHolder.noMemberFound.setText(context.getString(R.string.no_member));


        }

        cousinAccountHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkAccount){
                    Intent cousinProfileIntent = new Intent(context, CousinProfileActivity.class);
                    cousinProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cousinProfileIntent.putExtra("CousinDate", cousinAccount);
                    cousinProfileIntent.putExtra("CategoryType", categoryType);
                    context.startActivity(cousinProfileIntent);
                }



            }
        });

    }






  /*  @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    sectionItemListSearch = getSectionItemList;
                } else {
                    List<SubCategory> filteredList = new ArrayList<>();
                        for (SubCategory row : getSectionItemList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSubCategoryName().toLowerCase().contains(charString.toLowerCase())) {

                            ArrayList<CousinAccount>  get = row.getCousinAccounts();
                            row.setCousinAccounts(get);
                            filteredList.add(row);

                        }
                    }

                    sectionItemListSearch = filteredList;
                }

                FilterResults results = new FilterResults();
                results.values = sectionItemListSearch;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sectionItemListSearch = (ArrayList<SubCategory>) results.values;

                // refresh the list with filtered data
                  notifyDataSetChanged();
            }
        };

   *//* @Override
    public Filter getFilter() {
        return null;
    }*//*
    }*/
}