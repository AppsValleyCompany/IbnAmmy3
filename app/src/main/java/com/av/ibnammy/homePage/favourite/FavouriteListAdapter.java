package com.av.ibnammy.homePage.favourite;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.map.SearchResultAccountHolder;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.CousinProfileActivity;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.CousinProfileModel;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;

/**
 * Created by Maiada on 7/21/2018.
 */

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteAccountHolder> {

    ArrayList<CousinAccount> resultArrayList;
    CousinProfileModel cousinProfileModel;
    Context context;
    int categoryType ;

    View getView;
    String phone,password;

    public FavouriteListAdapter(Context context, ArrayList<CousinAccount> resultArrayList, CousinProfileModel cousinProfileModel, String phone, String  password) {
        this.context = context;
        this.resultArrayList = resultArrayList;
        this.cousinProfileModel = cousinProfileModel;
        this.phone = phone;
        this.password =password;
    }

    @Override
    public FavouriteAccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         getView = parent.getRootView();
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_favourite,parent,false);
         return new FavouriteAccountHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavouriteAccountHolder holder, final int position) {
        final   CousinAccount cousinAccount = resultArrayList.get(position);

        if(!cousinAccount.getCousinName().equals(""))
            holder.cousinName.setText(cousinAccount.getCousinName());
        else
            holder.cousinName.setText("لا يوجد");


        holder.favouriteIcon.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_favourite));


        if(!cousinAccount.getCousinJob().equals(""))
            holder.cousinJob.setText(cousinAccount.getCousinJob());
        else
            holder.cousinJob.setText("لا يوجد");


        if (!cousinAccount.getCousinImage().equals("")){

            Glide.with(context)
                    .applyDefaultRequestOptions(new RequestOptions().error(R.mipmap.male))
                    .load(IMG_URL +cousinAccount.getCousinImage()).listener(new RequestListener<Drawable>() {
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


        holder.favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteFavourite(holder,cousinAccount.getCousinId());

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int getCategoryType =  Integer.parseInt(cousinAccount.getCategoryTypeID());

                if(getCategoryType==1){
                    categoryType=0;
                }else if(getCategoryType==2){
                    categoryType = 1;
                }else {
                    categoryType = 2;
                }


                final Intent cousinProfileIntent = new Intent(context, CousinProfileActivity.class);
                cousinProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cousinProfileIntent.putExtra("CousinDate", cousinAccount);
                cousinProfileIntent.putExtra("CategoryType", categoryType);
                cousinAccount.setFavourite(false);

                cousinProfileModel.getAllFavouriteID(new GetCallback.GetAllFavouriteIdAccount() {
                    @Override
                    public void onGetAllFavouriteIdSuccess(ArrayList<String> favouriteAccountIdList) {
                        if(favouriteAccountIdList.size()!=0){

                            for(int i=0;i<favouriteAccountIdList.size();i++){
                                if(favouriteAccountIdList.get(i).equals(cousinAccount.getCousinId()))
                                {
                                    cousinAccount.setFavourite(true);
                                    context.startActivity(cousinProfileIntent);
                                    break;
                                }
                            }
                            if(!cousinAccount.isFavourite())
                                context.startActivity(cousinProfileIntent);


                        }else {
                            context.startActivity(cousinProfileIntent);

                        }

                    }

                    @Override
                    public void onGetAllFavouriteIdFailure() {
                        Toast.makeText(context, "حدث خطأ", Toast.LENGTH_SHORT).show();
                        context.startActivity(cousinProfileIntent);

                    }
                },phone,password);

            }
        });

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }


    private void deleteFavourite(final FavouriteAccountHolder mHolder, String cousinId){

        mHolder.favouriteIcon.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_unfavourite));

        cousinProfileModel.deleteAccountFavourite(new GetCallback.DeleteFavourite() {
            @Override
            public void onDeleteFavouriteSuccess() {
                resultArrayList.remove(mHolder.getAdapterPosition());
                notifyItemRemoved(mHolder.getAdapterPosition());
                notifyDataSetChanged();

                if(resultArrayList.size()==0){
                    disableEditText(FavouriteFragment.fragmentFavouriteBinding.searchBar);
                    FavouriteFragment.fragmentFavouriteBinding.tvNotActivatedOne.setVisibility(View.VISIBLE);

                }

                onDataSuccess(context.getResources().getString(R.string.delete_favourite_success));
            }

            @Override
            public void onDeleteFavouriteFailure() {
                mHolder.favouriteIcon.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_favourite));
                onDataFailure();

            }
        },phone,password,cousinId);

    }


    private void onDataSuccess(String successMessage){
        if(getView!=null)
            Snackbar.make(getView,successMessage,Snackbar.LENGTH_LONG).show();

    }


    private void onDataFailure(){
        if(getView!=null)
            Snackbar.make(getView,context.getResources().getString(R.string.error_favourite),Snackbar.LENGTH_LONG).show();

    }

       private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);

    }

}
