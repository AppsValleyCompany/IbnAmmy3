package com.av.ibnammy.updateUserData.familyData;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;

import java.util.ArrayList;
/**
 * Created by Mina on 12/31/2017.
 */

public class FamilyListAdapter extends RecyclerView.Adapter<FamilyListAdapter.ViewHolder> {

  //  RowFamilyListBinding binding;

    private ArrayList<Follower> followers;
    private Context context;
    String phone,password;
    public FamilyListAdapter( ArrayList<Follower> followers,Context context) {
        this.followers=followers;
        this.context=context;
        Bundle bundle= CommonUtils.loadCredentials(context);
        phone=bundle.getString(Constants.PHONE_KEY);
        password=bundle.getString(Constants.PASSWORD_KEY);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_family_list, parent, false);

     //   binding= DataBindingUtil.bind(itemView);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       final Follower fol=followers.get(position);
       String des=fol.getDescription();
            holder.name.setText(fol.getFollower_Name());
            holder.des.setText(fol.getDescription());
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fol.Account_FollowerID!=null) {
                        removeFollower(fol.Account_FollowerID);
                    }
                    followers.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

            holder.prof_img.setImageResource(fol.getPhoto());

    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView name=itemView.findViewById(R.id.follower_name_tv);
        TextView des=itemView.findViewById(R.id.follower_desc_tv);
        ImageView del=itemView.findViewById(R.id.delete_follower_btn);
        ImageView prof_img=itemView.findViewById(R.id.follower_prof_iv);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void add(Follower f){
        followers.add(f);
        notifyDataSetChanged();
        Toast.makeText(context,"تم اضافة تابع الى القائمة.",Toast.LENGTH_SHORT).show();
    }

   public ArrayList<Follower> getNewFamilyList(){
        ArrayList<Follower> newList=new ArrayList<>();
        for(Follower f: this.followers){
            if(f.Account_FollowerID==null){
                newList.add(f);
            }
        }
        return  newList;
   }
    public void removeFollower(String followerId) {
        FamilyModel.deleteFollower(phone, password, followerId, new FamilyCallBack.DeleteFollower() {
            @Override
            public void onDeleteFollowerSuccess(String status) {
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteFollowerFailure(String status) {
                Toast.makeText(context,status,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
