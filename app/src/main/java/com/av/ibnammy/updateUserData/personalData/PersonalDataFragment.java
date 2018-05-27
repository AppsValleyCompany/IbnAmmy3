package com.av.ibnammy.updateUserData.personalData;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.PersonalDataFragmentBinding;
import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.updateUserData.UpdateDataModel;
import com.av.ibnammy.updateUserData.UpdateDataView;
import com.av.ibnammy.updateUserData.User;
import com.av.ibnammy.updateUserData.uploadMedia.FilePath;
import com.av.ibnammy.updateUserData.workData.WorkDataFragment;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class  PersonalDataFragment extends Fragment implements GetCallback.onUpdateFinish ,GetCallback.onUserDataFetched, UpdateDataView {
    PersonalDataFragmentBinding binding;
    public static final int PICK_IMAGE = 1;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    Bundle bundle;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  UpdateDataActivity activity=new UpdateDataActivity();

        bundle=CommonUtils.loadCredentials(getContext());

    }
    private void  getUserData(){
        String phone=bundle.getString(Constants.PHONE_KEY);
        String password=bundle.getString(Constants.PASSWORD_KEY);
        final String data="{'Mobile':"+phone+",'Password':'"+password+"'}";
        //model=new UpdateDataModel();
        UpdateDataModel.get_user_data(data,this);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.personal_data_fragment, container, false);
        View view = binding.getRoot();

        getUserData();

        binding.changeProfileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pick_from_gallery();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        binding.birthDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onSaveData();
            }
        });

        binding.changeProfileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissions()){
                    pick_from_gallery();
                }
            }
        });



        return view;
    }



    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.birthDateEt.setText(sdf.format(myCalendar.getTime()));
    }

    public void pick_from_gallery()
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if(data!=null){
                Uri filePath = data.getData();
            String getPath= FilePath.getPath(getActivity(),filePath);
            if(getPath!=null&& getContext()!=null) {

                Glide.with(getContext())
                        .applyDefaultRequestOptions(new RequestOptions()
                                .fitCenter().transform(new CircleCrop()))
                        .load(data.getData())
                        .into(binding.changeProfileIv);

              //  Picasso.with(getContext()).load(data.getData()).transform(new CropCircleTransformation()).into(binding.changeProfileIv);
                Toast.makeText(getActivity(), "يتم رفع الصورة", Toast.LENGTH_SHORT).show();
                uploadImageToServer(filePath);
            }//TODO: action
            }
        }
    }

    private void uploadImageToServer(Uri imageUri){
        //Uploading code
        try {
            Uri myUri = Uri.parse(imageUri.toString());
            String path = getPathForImage(myUri);
            String uploadId = UUID.randomUUID().toString();
         //   Bundle bundle=CommonUtils.loadCredentials(getContext());
            String id=bundle.getString(Constants.USER_ID);
            //Creating a multi part request  (Image)
            new MultipartUploadRequest(getActivity(), uploadId, ApiClient.BASE_URL +"/AccountServices/Add_IMG/UpdateAccount?accountID="+id)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", "test") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(1)
                    .startUpload() ;
     } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPathForImage(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private boolean checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(),
                listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
            return false;
        }else{
          //  pick_from_gallery();
            return  true;
        }

    }

    @Override
    public void onUpdateSuccess(String status) {
        Toast.makeText(getContext(),status,Toast.LENGTH_SHORT).show();
        binding.progressBar.setVisibility(View.GONE);
        CommonUtils.transitFrag_BackStack(getContext(),new WorkDataFragment());

    }

    @Override
    public void onUpdateFailure(String status) {
        Toast.makeText(getContext(),status,Toast.LENGTH_SHORT).show();
        binding.progressBar.setVisibility(View.GONE);

    }


    @Override
    public void onSaveData() {
        binding.progressBar.setVisibility(View.VISIBLE);
     if(validateForms()){
        String first_name=binding.firstNameEt.getText().toString();
        String second_name=binding.secondNameEt.getText().toString();
        String third_name=binding.thirdNameEt.getText().toString();
        String forth_name=binding.forthNameEt.getText().toString();
        String birth_date=binding.birthDateEt.getText().toString();
        String email=binding.emailEt.getText().toString();
        String gender=binding.genderSpinner.getSelectedItem().toString();
        String marital=binding.maritalSpinner.getSelectedItem().toString();
        String blood_type=binding.bloodTypeSpinner.getSelectedItem().toString();

      //  Bundle bundle=CommonUtils.loadCredentials(getContext());

        String phone=bundle.getString(Constants.PHONE_KEY);
        String password=bundle.getString(Constants.PASSWORD_KEY);

        User user=new User(phone,password,email,first_name,second_name,
                third_name,forth_name,gender,marital,birth_date,blood_type);

         UpdateDataModel.update_user_data(user,PersonalDataFragment.this);
        }
        else {
            Toast.makeText(getContext(),"رجاء اكمال البيانات اولا.",Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean validateForms() {
        String first_name=binding.firstNameEt.getText().toString();
        String second_name=binding.secondNameEt.getText().toString();
        String third_name=binding.thirdNameEt.getText().toString();
        String forth_name=binding.forthNameEt.getText().toString();
        String birth_date=binding.birthDateEt.getText().toString();
        String email=binding.emailEt.getText().toString();
        int gen=binding.genderSpinner.getSelectedItemPosition();
        int marital=binding.maritalSpinner.getSelectedItemPosition();
        int blood=binding.bloodTypeSpinner.getSelectedItemPosition();
        if (first_name.equals("")||second_name.equals("")||third_name.equals("")
                ||forth_name.equals("")||birth_date.equals("")
                || gen==0 || marital==0 || blood==0  )
            return false;
        else
            return true;
    }



    @Override
    public void onGetDataSuccess(User user) {
        binding.progressBar.setVisibility(View.GONE);
        fillForms(user);
    }

    @Override
    public void onGetDataFailure(String status) {
        Toast.makeText(getContext(), status,Toast.LENGTH_SHORT).show();
        binding.progressBar.setVisibility(View.GONE);
    }


    @Override
    public void fillForms(User user){
        binding.firstNameEt.setText(user.getFirst_Name());
        binding.secondNameEt.setText(user.getSecond_Name());
        binding.thirdNameEt.setText(user.getThird_Name());
        binding.forthNameEt.setText(user.getForth_Name());
        binding.birthDateEt.setText(user.getBirthDate());
        binding.emailEt.setText(user.getEmail());

        if(getContext()!=null&&user.getProfile_IMG()!=null)
         Glide.with(getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .fitCenter().transform(new CircleCrop()))
                .load(IMG_URL +user.getProfile_IMG())
                .into(binding.changeProfileIv);


      //  Picasso.with(getContext()).load(IMG_URL +user.getProfile_IMG()).transform(new CropCircleTransformation()).into( binding.changeProfileIv);


        String gender=user.getGender();
        String marital=user.getMarital_Status();
        String blood_type=user.getBlood_Type();

        if (Arrays.asList(getResources().getStringArray(R.array.gender)).contains(gender)){
             binding.genderSpinner.setSelection  (((ArrayAdapter)binding.genderSpinner.getAdapter()).getPosition(gender));
        }

        if (Arrays.asList(getResources().getStringArray(R.array.marital_status_array)).contains(marital)){
            binding.maritalSpinner.setSelection  (((ArrayAdapter)binding.maritalSpinner.getAdapter()).getPosition(marital));
        }

        if (Arrays.asList(getResources().getStringArray(R.array.blood_type_array)).contains(blood_type)){
            binding.bloodTypeSpinner.setSelection (((ArrayAdapter)binding.bloodTypeSpinner.getAdapter()).getPosition(blood_type));
        }

        //binding.progressBar.setVisibility(View.GONE);
    }
}
