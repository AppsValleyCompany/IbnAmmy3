package com.av.ibnammy.Services;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.activities.SplashScreenActivity;
import com.av.ibnammy.homePage.map.MapFragment;
import com.av.ibnammy.login.LoginActivity;
import com.av.ibnammy.viewprofile.Profile;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    private final static int REQUEST_ID_CALL_PHONE_PERMISSIONS=0x1;

    Profile getProfileData ;
    String mobileNumber;

    Toolbar toolbar;
    ImageView backButton;

    View parentLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeMapToArabic();
        setContentView(R.layout.activity_notification);


        parentLayout = findViewById(android.R.id.content);

        toolbar = findViewById(R.id.toolbar);
        backButton =findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationActivity.this, SplashScreenActivity.class));
            }
        });




        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        getProfileData = (Profile) getIntent().getSerializableExtra("ProfileData");


        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       // finish();
    }

    public  PendingIntent getDismissIntent(int notificationId, Context context,Profile profile) {
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        intent.putExtra("ProfileData",profile);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return dismissIntent;
    }

    private void changeMapToArabic() {
        String languageToLoad = "ar_EG";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));

      //  changeMapToArabic();

        Double lat = Double.parseDouble(getProfileData.getHomeLatitude());
        Double lng = Double.parseDouble(getProfileData.getHomeLongitude());

        final LatLng getLocation = new LatLng(lat, lng);


        googleMap.addMarker(new MarkerOptions().position(getLocation).title(getProfileData.getSubscriptionStatus()));
        float zoom = 15f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLocation, zoom);
        googleMap.animateCamera(cameraUpdate);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showDetailsBox();
                return false;
            }
        });


    }

    private void showDetailsBox() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View alertView = inflater.inflate(R.layout.details_box_notification, null);

        TextView  cousinName    = alertView.findViewById(R.id.tv_cousin_name);
        TextView  cousinRequest = alertView.findViewById(R.id.tv_cousin_request);
        TextView  cousinMobile  = alertView.findViewById(R.id.tv_cousin_mobile);
        ImageView imgChat       = alertView.findViewById(R.id.img_chat);

        Button   btnCall = alertView.findViewById(R.id.btn_call);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(alertView);

        //Creating an alert dialog
        final AlertDialog   alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


         mobileNumber =  getProfileData.getMobile();
        if(mobileNumber.length()==10)
            mobileNumber = "+20"+mobileNumber;
        else
            mobileNumber = "+"+mobileNumber;

        cousinName.setText(getProfileData.getUserName());
        cousinRequest.setText(getProfileData.getSubscriptionStatus());
        cousinMobile.setText(mobileNumber);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(mobileNumber);
                alertDialog.cancel();
            }
        });

       imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCousinWhatAppChat(mobileNumber);
                alertDialog.cancel();
            }
        });
    }


    private void makeCall(String mobileNumber){
        if(checkPermissionsCallPhone()){
            if(mobileNumber!=null)
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+mobileNumber)));
        }else {
            if(parentLayout!=null)
                Snackbar.make(parentLayout,getResources().getString(R.string.allow_to_call), Snackbar.LENGTH_LONG).show();
        }

    }

    private boolean checkPermissionsCallPhone(){

        int permissionLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_CALL_PHONE_PERMISSIONS);
            }
            return false;
        } else {
            return true;
        }

    }

    private void openCousinWhatAppChat(String mobileNumber) {

        boolean isInstalled = whatsAppInstalledOrNot("com.whatsapp");
        if(isInstalled) {
                startChat(mobileNumber);
        }else {
            if (parentLayout!= null)
                Snackbar.make(parentLayout, getResources().getString(R.string.install_whatsapp), Snackbar.LENGTH_LONG).show();

        }

    }

    private boolean whatsAppInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void startChat(String mobileNumber){
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone="+mobileNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
