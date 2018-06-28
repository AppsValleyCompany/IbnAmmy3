package com.av.ibnammy.homePage.map;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.av.ibnammy.R;

/**
 * Created by Maiada on 6/17/2018.
 */

public class SearchResultAccountHolder extends RecyclerView.ViewHolder {
    TextView    cousinName;
    TextView    cousinJob;
    TextView    cousinDistance;
    ImageView   cousinImage;
    ProgressBar progressBar;

    public SearchResultAccountHolder(View itemView) {
        super(itemView);

        cousinName     = itemView.findViewById(R.id.txt_user_name);
        cousinJob      = itemView.findViewById(R.id.txt_job_user_name);
        cousinDistance = itemView.findViewById(R.id.txt_distance_user_name);
        cousinImage    = itemView.findViewById(R.id.img_profile_photo);
        progressBar      =  itemView.findViewById(R.id.pb_load_photo);


    }
}
