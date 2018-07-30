package com.av.ibnammy.homePage.favourite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.av.ibnammy.R;

/**
 * Created by Maiada on 7/21/2018.
 */

public class FavouriteAccountHolder extends RecyclerView.ViewHolder {
    TextView cousinName;
    TextView    cousinJob;
    ImageView cousinImage,favouriteIcon;
    ProgressBar progressBar;

    public FavouriteAccountHolder(View itemView) {
        super(itemView);

        cousinName     = itemView.findViewById(R.id.txt_user_name);
        cousinJob      = itemView.findViewById(R.id.txt_job_user_name);
        cousinImage    = itemView.findViewById(R.id.img_profile_photo);
        progressBar    = itemView.findViewById(R.id.pb_load_photo);
        favouriteIcon  = itemView.findViewById(R.id.favourite_icon);
    }
}
