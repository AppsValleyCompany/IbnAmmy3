package com.av.ibnammy.updateUserData.uploadMedia;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.ActivityUploadMediaBinding;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.updateUserData.UpdateDataModel;
import com.av.ibnammy.updateUserData.UpdateDataView;
import com.av.ibnammy.updateUserData.User;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.av.ibnammy.networkUtilities.ApiClient.BASE_URL;

public class UploadMediaActivity extends AppCompatActivity implements GetCallback.onUpdateFinish,GetCallback.onUserDataFetched,UpdateDataView{


    private ImageView back_icon;
    private ActivityUploadMediaBinding uploadMediaBinding;
    private MediaController mediaController;
    private  int  REQUEST_PICK_IMAGE=1;
    private  int  REQUEST_TAKE_GALLERY_VIDEO=2;
    private static final int STORAGE_PERMISSION_CODE = 123;
    //ArrayList<Uri>  uriImagesList = new ArrayList<>();

    ArrayList<MultipartBody.Part> parts =new ArrayList<>();


    int position = 0;
    private  Uri uploadVideoUrl = null ;
    String phone,password,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = CommonUtils.loadCredentials(getApplicationContext());
        phone = bundle.getString(Constants.PHONE_KEY);
        password = bundle.getString(Constants.PASSWORD_KEY);
        id=bundle.getString(Constants.USER_ID);
        uploadMediaBinding = DataBindingUtil.setContentView(this,R.layout.activity_upload_media);
        Setup_UI();
        final String data="{'Mobile':"+phone+",'Password':'"+password+"'}";
        UpdateDataModel.get_user_data(data,this);
    }

    private void Setup_UI() {
        uploadMediaBinding.addNewPhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission(1);
            }
        });

        uploadMediaBinding.uploadVideoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission(0);

            }
        });

        uploadMediaBinding.discountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                uploadMediaBinding.discountRatioTv.setText(i+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(UploadMediaActivity.this);
            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(uploadMediaBinding.videoView);
            // Set MediaController for VideoView
            uploadMediaBinding.videoView.setMediaController(mediaController);
        }
        uploadMediaBinding.videoView.requestFocus();
        back_icon=findViewById(R.id.back_btn);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        uploadMediaBinding.deleteVidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMediaBinding.videoView.setVisibility(View.GONE);
                view.setVisibility(View.INVISIBLE);
            }
        });

        uploadMediaBinding.saveMediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(UploadMediaActivity.this);
                builder.setMessage("هل تريد اضافه هذة الخدمة؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onSaveData();
                                dialogInterface.dismiss();
                                // onBackPressed();
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
            }




               /* if(uriImagesList.size()!=0||uploadVideoUrl!=null){
                    AlertDialog.Builder builder=new AlertDialog.Builder(UploadMediaActivity.this);
                    builder.setMessage("هل تريد اضافه هذة الخدمة؟")
                            .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(uriImagesList.size()!=0){
                                       for(int k=0;k<uriImagesList.size();k++){
                                            Uri uri = uriImagesList.get(k);
                                            uploadImageToServer(uri);
                                       }
                                    }else  if(uploadVideoUrl!=null){
                                        uploadVideoToServer(uploadVideoUrl);
                                    }
                                    dialogInterface.dismiss();
                                   // onBackPressed();
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
                }else {
                     onBackPressed();
                }
            }*/
        });
    }

    private void requestStoragePermission(int typeOfPick) {
        if(typeOfPick==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            } else {
                // Android version is lesser than 6.0 or the permission is already granted.
                     pick_from_gallery();
            }

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            } else {
                // Android version is lesser than 6.0 or the permission is already granted.
                chooseVid();
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //If permission is granted
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
       //     Toast.makeText(this, "Permission granted now you can select img or video", Toast.LENGTH_LONG).show();
            pick_from_gallery();
        } else {
            //Displaying another toast if permission is not granted
            Toast.makeText(this, "To select Image or video you must allow Permission ", Toast.LENGTH_LONG).show();
        }
    }

    public void chooseVid(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
    }

    public void pick_from_gallery(){

        if(parts.size()<=4){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
        }else {
            Toast.makeText(this, "Just 5 Image", Toast.LENGTH_SHORT).show();

        }
    }

    public void addNewPhoto(Uri url){
            //Add Image
            final ImageView view1=new ImageView(getApplicationContext());
            view1.setTag(position); //
            view1.setImageResource(R.mipmap.ic_camera);
            view1.setPadding(10,10,10,10);
            view1.setBackgroundColor(getResources().getColor(R.color.light_black1));
            view1.setLayoutParams(new ViewGroup.LayoutParams(
                    150,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            uploadMediaBinding.photosLinearContainer.addView(view1);
            Picasso.with(getApplicationContext()).load(url).into(view1);
             // set Position and Add Url
        String path = getPathForImage(url);
        File file = new File(path);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

        parts.add(fileToUpload);
             position = position + 1;
             view1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(UploadMediaActivity.this);
                builder.setMessage("هل تريد مسح هذة الصورة؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int getViewDeletedPosition = (int) view.getTag();
                                for(int j=0;j<=uploadMediaBinding.photosLinearContainer.getChildCount();j++){
                                      View  view = uploadMediaBinding.photosLinearContainer.getChildAt(j);
;                                      int getViewPosition = (int) view.getTag();
                                    if(getViewDeletedPosition == getViewPosition){
                                        uploadMediaBinding.photosLinearContainer.removeViewAt(j);
                                        parts.remove(j);
                                        position = position - 1;
                                        break;
                                    }
                                }
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
                Uri selectedVideo  = data.getData();
                uploadMediaBinding.videoView.setVisibility(View.VISIBLE);
                uploadMediaBinding.deleteVidBtn.setVisibility(View.VISIBLE);
                uploadMediaBinding.videoView.setVideoURI(selectedVideo );
                uploadMediaBinding.videoView.start();
                uploadVideoToServer(selectedVideo);
            }
            else if(requestCode==REQUEST_PICK_IMAGE){
                Uri filePath = data.getData();
                String getPath= FilePath.getPath(this,filePath);
                if(getPath!=null){
                    addNewPhoto(filePath);
                }

            }
        }
    }

    private void uploadVideoToServer(Uri videoUri){
        //Uploading code
        try {
            String path = getPathForVideo(videoUri);
            String uploadId = UUID.randomUUID().toString();
            // Creating a multi part request (Video)

            // http://197.45.12.35/ibn-ammey/api/AccountServices/Add_Video/Work_Video?accountID=10
            new MultipartUploadRequest(this, uploadId,BASE_URL+"AccountServices/Add_Video/Work_Video?accountID="+id)
                    .addFileToUpload(path, "video") //Adding file
                    .addParameter("name", "test") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(1)
                    .startUpload();
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadImageToServer(Uri imageUri){
         try {
            Uri myUri = Uri.parse(imageUri.toString());
            String path = getPathForImage(myUri);
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request (Video)
           // http://197.45.12.35/ibn-ammey/api/AccountServices/Add_IMGs/Work_IMGs?accountID=10
            new MultipartUploadRequest(this, uploadId,BASE_URL+"AccountServices/Add_IMGs/Work_IMGs?accountID="+id)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", "test") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(1)
                    .startUpload() ;
            //Starting the upload


        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public String getPathForVideo(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Video.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();
        return path;
    }

    public String getPathForImage(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    @Override
    public void onUpdateSuccess(String status) {
        Toast.makeText(getApplicationContext(),"تم رفع البيانات بنجاح.",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void onUpdateFailure(String status) {
        Toast.makeText(getApplicationContext(), status,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveData() {
        if(validateForms()) {
            String des = uploadMediaBinding.etServiceDescription.getText().toString();
            String price = uploadMediaBinding.etServicePrice.getText().toString();
            int disc = uploadMediaBinding.discountSeekBar.getProgress();

            User user = new User(phone, password, des, price, disc);
            UpdateDataModel.update_user_data(user, this);

            //***Media Part**//
           /* if (parts.size() != 0 || uploadVideoUrl != null) {
                if (parts.size() != 0) {
                    for (int k = 0; k < parts.size(); k++) {
                        Uri uri = parts.get(k);
                        uploadImageToServer(uri);
                    }
                }*//*else  if(uploadVideoUrl!=null){
                                    uploadVideoToServer(uploadVideoUrl);}*//*
            }*/
           Bundle bulnde= CommonUtils.loadCredentials(this);
           String id=bulnde.getString(Constants.USER_ID);

            if(parts.size()!=0){
                uploadMultipleImage(parts,id);
            }

        }
    }

    public void uploadMultipleImage(ArrayList<MultipartBody.Part> list,String id){

        showProgressBar();
        UploadMediaModel.UploadMedia(new MediaCallBack.uploadMediaImage() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(UploadMediaActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                hideProgressBar();
                onBackPressed();
            }

            @Override
            public void onFailure() {

            }
        },id,list);

    }

    @Override
    public boolean validateForms() {

        return true;
    }

    @Override
    public void fillForms(User user) {

        String desc=user.getService_Description();
        String price=user.getPrice();
        if(desc!=null)
            uploadMediaBinding.etServiceDescription.setText(desc);
        if(price!=null)
            uploadMediaBinding.etServicePrice.setText(price);
        if(user.getDiscount()!=null){
            int dis= user.getDiscount();
            uploadMediaBinding.discountSeekBar.setProgress(dis);
        }
    }

    @Override
    public void onGetDataSuccess(User user) {
        fillForms(user);
    }

    @Override
    public void onGetDataFailure(String status) {
        Toast.makeText(getApplicationContext(), status,Toast.LENGTH_SHORT).show();
    }

    private void showProgressBar() {
        uploadMediaBinding.pbLoading.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        uploadMediaBinding.pbLoading.setVisibility(View.INVISIBLE);
    }
}
