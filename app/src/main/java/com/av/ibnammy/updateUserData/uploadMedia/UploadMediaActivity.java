package com.av.ibnammy.updateUserData.uploadMedia;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
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
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.av.ibnammy.networkUtilities.ApiClient.BASE_URL;
import static com.av.ibnammy.networkUtilities.ApiClient.WORK_MEDIA_URL;

public class UploadMediaActivity extends AppCompatActivity implements GetCallback.onUpdateFinish,GetCallback.onUserDataFetched,UpdateDataView{

    private ImageView back_icon;
    private ActivityUploadMediaBinding uploadMediaBinding;
    private MediaController mediaController;
    private  int  REQUEST_PICK_IMAGE=1;
    private  int  REQUEST_TAKE_GALLERY_VIDEO=2;

    private static final int STORAGE_PERMISSION_CODE_IMAGE = 123;
    private static final int STORAGE_PERMISSION_CODE_VIDEO = 124;    //ArrayList<Uri>  uriImagesList = new ArrayList<>();

    ArrayList<MultipartBody.Part> parts =new ArrayList<>();
    int position = 0;
    private  Uri uploadVideoUrl = null ;
    String phone,password,id;
    boolean isUpdate ;

    private ProgressDialog progressDialog ;

    ArrayList<WorkPhoto> workPhotos=new ArrayList<>();
    MultipartUploadRequest request;

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
            public void onClick(final View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(UploadMediaActivity.this);
                builder.setMessage("هل تريد مسح الفيديو؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                uploadMediaBinding.videoView.setVisibility(View.GONE);
                                view.setVisibility(View.INVISIBLE);
                                uploadMediaBinding.uploadVideoBtn.setVisibility(View.GONE);
                                if(videoUrl!=null){
                                    deleteVideo(id);
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
            }
        });

        uploadMediaBinding.saveMediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveData();
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

        uploadMediaBinding.uploadVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateVideo(uploadVideoUrl)){
                        uploadVideoToServer(uploadVideoUrl);
                }
                    else Toast.makeText(getApplicationContext(), "اقصى حجم للفيديو هو 50 ميجا بايت.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestStoragePermission(int typeOfPick) {
        if(typeOfPick==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE_IMAGE);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            } else {
                // Android version is lesser than 6.0 or the permission is already granted.
                     pick_from_gallery();
            }

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE_VIDEO);
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

            if(requestCode==STORAGE_PERMISSION_CODE_IMAGE)
                pick_from_gallery();
            else
                chooseVid();

        } else {
            //Displaying another toast if permission is not granted
            Toast.makeText(this, "لرفع الميديا يجب السماح بالدخول الى ملفاتك اولا.", Toast.LENGTH_LONG).show();
        }
    }

    public void chooseVid(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
    }

        public void pick_from_gallery(){
            if(!isMaxPhotosSelected()){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "اختار صورة"), REQUEST_PICK_IMAGE);
                }
                else {
                    Toast.makeText(this, "الحد الاقصى 5 صور.", Toast.LENGTH_SHORT).show();
                }
        }

    public boolean isMaxPhotosSelected(){
            boolean isFull=true;
        for(WorkPhoto photo: workPhotos) {
            if (photo.getPath() == null) {
                isFull=false;
                break;
            }
        }
        return isFull;
    }

    public void addNewPhoto(final WorkPhoto photo){
/*        Bundle bulnde= CommonUtils.loadCredentials(this);
        final String id=bulnde.getString(Constants.USER_ID);*/
            //Add Image
           // position = position + 1;
            final ImageView view1=new ImageView(getApplicationContext());
            view1.setTag(photo.getPos()); //

            view1.setImageResource(R.mipmap.ic_camera);
            view1.setPadding(10,5,10,5);
            view1.setBackgroundColor(getResources().getColor(R.color.light_black1));
            view1.setLayoutParams(new ViewGroup.LayoutParams(
                    150,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            uploadMediaBinding.photosLinearContainer.addView(view1);
            if(photo.isFromServer()) {
                Picasso.with(getApplicationContext()).load(WORK_MEDIA_URL + photo.getPath()).into(view1);
            }else{
                Picasso.with(getApplicationContext()).load(photo.getPath()).into(view1);
            }

             // set Position and Add Url
            view1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
              //  if ((int)view.getTag() == uploadMediaBinding.photosLinearContainer.getChildCount()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadMediaActivity.this);
                    builder.setMessage("هل تريد مسح هذة الصورة؟")
                            .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int getViewDeletedPosition = (int) view.getTag();
                                    /*for (int j = 0; j <= uploadMediaBinding.photosLinearContainer.getChildCount(); j++) {
                                        View view = uploadMediaBinding.photosLinearContainer.getChildAt(j);
                                        int getViewPosition = (int) view.getTag();*/
                                       // if (getViewDeletedPosition == getViewPosition) {
                                            //if (photo.fromServer)
                                                deleteImage(id, photo.getPos());
                                                uploadMediaBinding.photosLinearContainer.removeView(view);

                                            /*uploadMediaBinding.photosLinearContainer.removeViewAt(getViewDeletedPosition-1);
                                            workPhotos.remove(photo);*/
                                            position = position - 1;
                                }
                            })
                            .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                return true;
            }
        });

          //   uploadToParts(path);
     }

     public MultipartBody.Part uploadToParts(Uri uri){
       //  Uri uri = Uri.parse(uriAsPath);
         String path = getPathForImage(uri);
         File file = new File(path);
         RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
         MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        // parts.add(fileToUpload);
        return fileToUpload;
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedVideo  = data.getData();
                String path = getPathForVideo(selectedVideo);
                if(path!=null) {
                    String videoFormat = path.toLowerCase();
                    if (videoFormat.endsWith(".3gp") || videoFormat.endsWith(".mp4")) {
                        uploadMediaBinding.videoView.setVisibility(View.VISIBLE);
                        uploadMediaBinding.deleteVidBtn.setVisibility(View.VISIBLE);
                        uploadMediaBinding.videoView.setVideoURI(selectedVideo);
                        uploadMediaBinding.videoView.start();
                        uploadVideoUrl = selectedVideo;
                        uploadMediaBinding.uploadVideoBtn.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, "صيغة الفيديو غير مدعومة.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            else if(requestCode==REQUEST_PICK_IMAGE){
                final Uri filePath = data.getData();
                final String getPath= FilePath.getPath(this,filePath);
                  if(getPath!=null){

                     // final WorkPhoto photo=new WorkPhoto(String.valueOf(filePath),workPhotos.size(),true);
                      //workPhotos.add(photo);
                       for(final WorkPhoto photo:workPhotos){
                           if (photo.getPath()==null){

                               updateImage(id, photo.getPos(), uploadToParts(filePath), new MediaCallBack.updateMediaImage() {
                                   @Override
                                   public void onSuccess(String success) {
                                       photo.setPath(String.valueOf(filePath));
                                       photo.setFromServer(false);
                                       addNewPhoto(photo);
                                       Toast.makeText(UploadMediaActivity.this, "تم رفع الصورة بنجاح", Toast.LENGTH_SHORT).show();
                                       hideProgressBar();
                                   }

                                   @Override
                                   public void onFailure(String failure) {
                                       Toast.makeText(UploadMediaActivity.this, "حدث خطأ اثناء الرفع", Toast.LENGTH_SHORT).show();
                                       hideProgressBar();
                                   }
                               });
                               break;
                           }
                       }
     /*
                      updateImage(id, workPhotos.size(), uploadToParts(filePath), new MediaCallBack.updateMediaImage() {
                          @Override
                          public void onSuccess(String success) {
                              addNewPhoto(photo);
                              Toast.makeText(UploadMediaActivity.this, "تم رفع الصورة بنجاح", Toast.LENGTH_SHORT).show();
                              hideProgressBar();
                          }

                          @Override
                          public void onFailure(String failure) {
                              Toast.makeText(UploadMediaActivity.this, "حدث خطأ اثناء الرفع", Toast.LENGTH_SHORT).show();
                             hideProgressBar();
                          }
                      });
*/

                     // UploadMediaModel.UpdateMediaImage(this,id,workPhotos.size(), uploadToParts(filePath));

                }
              //  String path = getPathForImage(filePath);


            }
        }
    }

    private void uploadVideoToServer(Uri videoUri){
        //Uploading code
        try {
            String path = getPathForVideo(videoUri);
            final String uploadId = UUID.randomUUID().toString();
            final UploadNotificationConfig notificationConfig= new UploadNotificationConfig();
            progressDialog = new ProgressDialog(UploadMediaActivity.this);
            progressDialog.setMessage("Uploading Your Video....");
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progressDialog.setCancelable(false);

            String UPLOAD_URL ="https://ibnammy.net/admin/api/AccountServices/Add_Video/Work_Video?accountID="+id;

            request = new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addHeader("Content-Type", "multipart/form-data")
                    .addFileToUpload(path, "video")
                     //Adding file
                    .setNotificationConfig(notificationConfig)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            progressDialog.show();
                            progressDialog.setProgress((uploadInfo.getProgressPercent()));
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Toast.makeText(context, "حدث خطأ اثناء الرفع", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            if(serverResponse.getHttpCode()==200)
                                progressDialog.hide();
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            progressDialog.hide();
                        }
                    });

                    request.startUpload();


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
/*
            if(parts.size()!=0){
                uploadMultipleImage(parts,id);
            }*/
        }
    }


    public void uploadMultipleImage(ArrayList<MultipartBody.Part> list,String id){

        showProgressBar();
        UploadMediaModel.UploadMediaImage(new MediaCallBack.uploadMediaImage() {
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

    public boolean updateImage(String id,int imgNumber,MultipartBody.Part part,MediaCallBack.updateMediaImage listener){
        showProgressBar();
        UploadMediaModel.UpdateMediaImage(listener,id,imgNumber,part);

     return  isUpdate;
    }

    public  void  deleteImage(String accountId, final int imgNumber){
        showProgressBar();
        UploadMediaModel.DeleteMediaImage(new MediaCallBack.deleteMediaImage() {
            @Override
            public void onSuccess(String success) {
                hideProgressBar();
                //****
                //uploadMediaBinding.photosLinearContainer.removeViewAt(imgNumber-1);
                //workPhotos.remove(photo);
                workPhotos.get(imgNumber-1).setPath(null);
                Toast.makeText(UploadMediaActivity.this,"تم مسح الصورة" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String failure) {
                Toast.makeText(UploadMediaActivity.this,failure , Toast.LENGTH_SHORT).show();

            }
        },accountId,imgNumber);
    }

    @Override
    public boolean validateForms() {
        return true;
    }

    String  videoUrl ;
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

       ArrayList<WorkPhoto> list = user.getWorkImgList();
            workPhotos.addAll(list);                                     //workPhotos
            for(WorkPhoto photo: workPhotos){
                if(photo.getPath()!=null){
                   addNewPhoto(photo);
                }
            }


        if(user.getWork_Video()!=null){
            videoUrl=WORK_MEDIA_URL+user.getWork_Video();
            uploadMediaBinding.videoView.setVisibility(View.VISIBLE);
            uploadMediaBinding.deleteVidBtn.setVisibility(View.VISIBLE);
            Uri vidUri = Uri.parse(videoUrl);
            uploadMediaBinding.videoView.setVideoURI(vidUri);
            mediaController = new MediaController(this);
            mediaController.setAnchorView(uploadMediaBinding.videoView);
            uploadMediaBinding.videoView.setMediaController(mediaController);
            uploadMediaBinding.videoView.start();
            uploadMediaBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // This is just to show image when loaded
                    int seekTimeMs = (0 * 60 + 1) * 1000; // 0:01
                    mp.seekTo(seekTimeMs);
                    mp.start();
                    mp.pause();
                }
            });
           // uploadMediaBinding.uploadVideoBtn
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

    public boolean validateVideo(Uri url){
        if(url!=null) {
            String path = getPathForVideo(url);
            File file = new File(path);
            long length = file.length() / 1024;
            return (length <= 50*1024);
        }

          /*{
           return true;
       } }
        else return false;//Toast.makeText(getApplicationContext(), "error with video size..", Toast.LENGTH_SHORT).show();*/
            return false;
    }

    public void deleteVideo(String id){
        UploadMediaModel.DeleteMediaVideo(new MediaCallBack.deleteMediaVideo() {
            @Override
            public void onSuccess(String success) {
                Toast.makeText(UploadMediaActivity.this, "تم مسح الفيديو", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String failure) {
                Toast.makeText(UploadMediaActivity.this, "حدث خطا اثناء  مسح الفيديو", Toast.LENGTH_SHORT).show();

            }
        },id);

    }
}
