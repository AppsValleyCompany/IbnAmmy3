package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.av.ibnammy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static com.av.ibnammy.networkUtilities.ApiClient.WORK_MEDIA_URL;

/**
 * Created by 2ta on 04/01/2018.
 */

public class SlideAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<String> image_arraylist;
    View view;
    MediaController videoControl;
    VideoView videoView;
    String videoUrl;
    boolean isVideo= true;
    public SlideAdapter(Activity activity, ArrayList<String> image_arraylist) {
        this.activity = activity;
        if(image_arraylist.get(0).equals("")){
            image_arraylist.remove(0);
            isVideo=false;
        }
        this.image_arraylist = image_arraylist;
    }
    @Override
         public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            videoUrl = image_arraylist.get(0);

        if(position==0&&isVideo){

            view = layoutInflater.inflate(R.layout.slider_video, container, false);
            final ProgressBar progressBar = view.findViewById(R.id.progress_bar);
            videoView = view.findViewById(R.id.vv_media);
            final Uri vidUri = Uri.parse(WORK_MEDIA_URL +image_arraylist.get(position));
            videoView.setVideoURI(vidUri);
            videoView.seekTo(100);
            videoControl = new MediaController(activity);
            videoControl.setAnchorView(videoView);
            videoView.setMediaController(videoControl);
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    // This is just to show image when loaded
                    int seekTimeMs = (0 * 60 + 1) * 1000; // 0:01
                    mp.seekTo(seekTimeMs);
                    progressBar.setVisibility(View.GONE);
                    mp.start();
                    mp.pause();

                }
            });

   /*          videoView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //videoPopup(vidUri);
                 //   showVideotDialog();
                     activity.startActivity(new Intent(activity,FullScreenVideoActivity.class));
                    return false;
                }
            });
*/
        }else {
             view = layoutInflater.inflate(R.layout.slider, container, false);
            final ImageView im_slider = view.findViewById(R.id.im_slider);
            final ProgressBar progressBar = view.findViewById(R.id.pb_loading);


            Glide.with(activity.getApplicationContext())
                    .applyDefaultRequestOptions(new RequestOptions()
                            .error(R.mipmap.ic_logo)
                     )
                    .load(WORK_MEDIA_URL + image_arraylist.get(position))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(im_slider)
            ;
        }
        container.addView(view);
        return view;
    }

    private void showVideotDialog() {
        android.app.FragmentManager fm = activity.getFragmentManager();
        PreviewVideoDialogFrag editNameDialogFragment = PreviewVideoDialogFrag.newInstance(videoUrl);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    public void imgPopup(String url){

            View view = activity.getLayoutInflater().inflate(R.layout.preview_image_layout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            final AlertDialog dialog=builder.setView(view).create();
            ImageView imageView=view.findViewById(R.id.bigger_img_iv);
            Glide.with(activity.getApplicationContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.mipmap.ic_logo))
                .load(url)
                .into(imageView);
             ImageView dismiss=view.findViewById(R.id.dismiss_dialog_iv);
             dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }

    public void videoPopup(Uri url){
        View view = activity.getLayoutInflater().inflate(R.layout.preview_video_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog=builder.setView(view).create();
        VideoView videoView=view.findViewById(R.id.video_view);
        videoView.setVideoURI(url);
        videoControl = new MediaController(activity);
        videoControl.setAnchorView(videoView);
        videoView.setMediaController(videoControl);
        videoView.start();

        //ImageView dismiss=view.findViewById(R.id.dismiss_dialog_iv);
 /*       dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.setCancelable(false);
        dialog.show();
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


    public void stopVideoView(){
        if(videoUrl!=null&&videoView!=null){
            videoView.stopPlayback(); // stop video
            videoControl.hide();      // hide media controll
        }
        //notifyDataSetChanged();
    }

}
