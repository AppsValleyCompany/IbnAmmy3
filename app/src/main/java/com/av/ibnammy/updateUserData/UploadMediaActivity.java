package com.av.ibnammy.updateUserData;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.av.ibnammy.R;
import com.squareup.picasso.Picasso;

public class UploadMediaActivity extends AppCompatActivity {
    TextView discount_ratio_tv;
    ImageView upload_video_iv,add_new_photo_iv,back_icon,delete_vid_btn;
    int  REQUEST_TAKE_GALLERY_VIDEO=2;
    int PICK_IMAGE=1;
    VideoView videoView;
    private MediaController mediaController;
    LinearLayout photos_linear_container;
    public static int PHOTO_COUNT=0;
    SeekBar seekBar;
    Button save_media_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_media);

        discount_ratio_tv=findViewById(R.id.discount_ratio_tv);
        seekBar=  findViewById(R.id.discount_seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                discount_ratio_tv.setText(i+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        upload_video_iv=findViewById(R.id.upload_video_iv);
        upload_video_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseVid();
            }
        });
        videoView=findViewById(R.id.videoView);
        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(UploadMediaActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(videoView);


            // Set MediaController for VideoView
            videoView.setMediaController(mediaController);
        }
        videoView.requestFocus();

        photos_linear_container=findViewById(R.id.photos_linear_container);

        add_new_photo_iv=findViewById(R.id.add_new_photo_iv);
        add_new_photo_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PHOTO_COUNT<5) {
                    pick_from_gallery();

                }
                else {
                    Toast.makeText(getApplicationContext(),"لقد وصلت للحد الاقصى من الصور",Toast.LENGTH_SHORT).show();
                }
            }
        });
        back_icon=findViewById(R.id.back_btn);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        delete_vid_btn=findViewById(R.id.delete_vid_btn);
        delete_vid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.setVisibility(View.GONE);
                view.setVisibility(View.INVISIBLE);
            }
        });

        save_media_btn=findViewById(R.id.save_media_btn);
        save_media_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void chooseVid(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
    }
    public void pick_from_gallery(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void addNewPhoto(Uri url){
        ImageView view1=new ImageView(getApplicationContext());
        view1.setImageResource(R.mipmap.ic_camera);
        view1.setPadding(10,10,10,10);
        view1.setBackgroundColor(getResources().getColor(R.color.light_black1));
        view1.setLayoutParams(new ViewGroup.LayoutParams(
                150,
                ViewGroup.LayoutParams.MATCH_PARENT));
        photos_linear_container.addView(view1);
        Picasso.with(getApplicationContext()).load(url).into(view1);

        view1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                //Toast.makeText(getApplicationContext(),view.getId()+"",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(UploadMediaActivity.this);

                builder.setMessage("هل تريد مسح هذة الصورة؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                photos_linear_container.removeViewInLayout(view);
                                PHOTO_COUNT--;

                            }
                        })
                        .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();

                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();
                videoView.setVisibility(View.VISIBLE);
                delete_vid_btn.setVisibility(View.VISIBLE);
                videoView.setVideoURI(selectedImageUri);
                videoView.start();
            }
            else if(requestCode==PICK_IMAGE){
                addNewPhoto(data.getData());
                PHOTO_COUNT++;
            }
        }
    }


}
