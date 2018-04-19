package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.av.ibnammy.R;

/**
 * Created by Maiada on 3/21/2018.
 */

public class CousinAccountHolder extends RecyclerView.ViewHolder {

    RelativeLayout cousinData,noCousinData;
    ImageView cousinImage;
    TextView  cousinName,cousinJob,noMemberFound;
    ProgressBar progressBar;
    public CousinAccountHolder(View itemView) {
        super(itemView);
        cousinImage    =  itemView.findViewById(R.id.img_cousin);
        cousinName     =  itemView.findViewById(R.id.tv_cousin_name);
        cousinJob      =  itemView.findViewById(R.id.tv_cousin_job);
        cousinData     =  itemView.findViewById(R.id.rl_cousin_data);
        noCousinData   =  itemView.findViewById(R.id.rl_no_cousin_data);
        noMemberFound  =  itemView.findViewById(R.id.tv_no_member_found);
        progressBar    =  itemView.findViewById(R.id.pb_load_photo);

    }
}
