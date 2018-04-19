package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.av.ibnammy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.av.ibnammy.networkUtilities.ApiClient.WORK_MEDIA_URL;

/**
 * Created by 2ta on 04/01/2018.
 */

public class SlideAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<String> image_arraylist;

    public SlideAdapter(Activity activity, ArrayList<String> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slider, container, false);

        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);

           Glide.with(activity.getApplicationContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.mipmap.ic_logo))
                .load(WORK_MEDIA_URL+image_arraylist.get(position))
                .into(im_slider);


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
