package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentMapUserBinding;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;

/**
 * Created by Aya Mamhoud on 1/3/2018.
 */

public class MapUserProfileFragment  extends Fragment implements
        OnMapReadyCallback {


    MapStyleOptions mapStyleOptions;
    FragmentMapUserBinding mapUserBinding;
    CousinAccount cousinAccount ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mapUserBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map_user,container,false);
        View rootView  = mapUserBinding.getRoot();


        getUserLocationAndAddress();

        return rootView;
    }

    private void getUserLocationAndAddress() {


        cousinAccount = (CousinAccount) getArguments().getSerializable("CousinData");

        if(!cousinAccount.getHomeLatitude().equals("")&&!cousinAccount.getHomeLongitude().equals("")){
            showMapAndAddress();
            Locale.setDefault(new Locale("ar","EG"));
            setCousinImage();
            changeToMapFragment();
        }else{
            hideMapAndAddress();
        }



    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(getActivity().getApplicationContext(),R.raw.style_json);
        // mMap = googleMap;
        googleMap.setMapStyle(mapStyleOptions);


      //  CousinProfileActivity.setCousinImage(getActivity());
        // Get Item id to change with my new drawable
        // control_image.xml  have two id  id:img_frame_profile and id:img_profile_image
        // To change one of them ex: change img_profile_image
        LayerDrawable layer = (LayerDrawable)getActivity().getResources().getDrawable(R.drawable.control_image);
        // Call drawable
        layer.setDrawableByLayerId(R.id.img_cousin_photo,mapUserBinding.imgMarkerImage.getDrawable()); // replace and add

        // You need to convert drawable to bitmap coz marker options take only bitmap
        Drawable d = getResources().getDrawable(R.drawable.control_image);
        Bitmap bitmap = drawableToBitmap(d);

        // control size of marker icon
        final Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap,Math.round(bitmap.getWidth() *0.5f),
                Math.round(bitmap.getHeight() * 0.5f),false);

        Double lat = Double.parseDouble(cousinAccount.getHomeLatitude());
        Double lng = Double.parseDouble(cousinAccount.getHomeLongitude());

        final LatLng getLocation = new LatLng( lat,lng);
        googleMap.addMarker(new MarkerOptions().position(getLocation).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        float zoom = 15f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLocation, zoom);
        googleMap.animateCamera(cameraUpdate);

        CousinAddress();


    }

    private void CousinAddress() {

        StringBuilder cousinAddress = new StringBuilder();
        if(!cousinAccount.getHomeStreet().equals("")){
            cousinAddress.append(cousinAccount.getHomeStreet());
        }

        if(!cousinAccount.getHomeCity().equals("")){
            cousinAddress.append(" , ");
            cousinAddress.append(cousinAccount.getHomeCity());
        }

        if(!cousinAccount.getHomeDistrict().equals("")){
            cousinAddress.append(" , ");
            cousinAddress.append(cousinAccount.getHomeDistrict());
        }

        if(!cousinAccount.getHomeCountry().equals("")){
            cousinAddress.append(" , ");
            cousinAddress.append(cousinAccount.getHomeCountry());
        }

        if(cousinAddress.length()!=0){
            mapUserBinding.tvAddress.setText(cousinAddress.toString());
        }
    }


    private  void  changeToMapFragment(){
        setCousinImage();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map_view, fragment);
        transaction.commit();
        fragment.getMapAsync(this);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void showMapAndAddress(){
        mapUserBinding.tvNoDataFound.setVisibility(View.GONE);

        mapUserBinding.mapView.setVisibility(View.VISIBLE);
        mapUserBinding.tvMarkerAddress.setVisibility(View.VISIBLE);
    }

    private void hideMapAndAddress(){
        mapUserBinding.tvNoDataFound.setVisibility(View.VISIBLE);

        mapUserBinding.mapView.setVisibility(View.GONE);
        mapUserBinding.tvMarkerAddress.setVisibility(View.GONE);
    }

    public  void setCousinImage(){
        if(!cousinAccount.getCousinImage().equals("")){

        /*    Picasso.with(getActivity()).load(IMG_URL + cousinAccount.getCousinImage()).transform((new CircleTransform()))
                    .placeholder(R.drawable.ic_profile_image).centerCrop().fit().into(mapUserBinding.imgMarkerImage);
*/
                  Glide.with(getActivity())
                  .applyDefaultRequestOptions(new RequestOptions()
                         .placeholder(R.drawable.ic_profile_image)
                         .fitCenter().transform(new CircleCrop()))
                  .load(IMG_URL+cousinAccount.getCousinImage())
                  .into(mapUserBinding.imgMarkerImage);


        }else{
            mapUserBinding.imgMarkerImage.setImageResource(R.drawable.ic_profile_image);

        }
    }


}