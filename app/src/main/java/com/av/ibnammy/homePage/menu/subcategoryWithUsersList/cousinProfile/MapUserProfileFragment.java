package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentMapUserBinding;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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

    private View mCustomMarkerView;
    private ImageView mMarkerImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mapUserBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map_user,container,false);
        View rootView  = mapUserBinding.getRoot();
        Locale.setDefault(new Locale("ar","EG"));
        mCustomMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_view, null);
        mMarkerImageView = mCustomMarkerView.findViewById(R.id.img_new_marker);

        getUserLocationAndAddress();

        return rootView;
    }

    private void getUserLocationAndAddress() {


        cousinAccount = (CousinAccount) getArguments().getSerializable("CousinData");

        if(!cousinAccount.getHomeLatitude().equals("")&&!cousinAccount.getHomeLongitude().equals("")){
            showMapAndAddress();
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


        try {


        Double lat = Double.parseDouble(cousinAccount.getHomeLatitude());
        Double lng = Double.parseDouble(cousinAccount.getHomeLongitude());

        final LatLng getLocation = new LatLng( lat,lng);



        if(!cousinAccount.getCousinImage().equals("")) {

            Glide.with(getContext())
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.mipmap.male)
                            .fitCenter().transform(new CircleCrop())).
                    asBitmap().
                    load(IMG_URL + cousinAccount.getCousinImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            googleMap.addMarker(new MarkerOptions()
                                    .position(getLocation)
                                    .title(cousinAccount.getCousinName())
                                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(mCustomMarkerView, bitmap))));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getLocation, 15f));

                        }

                    });

        }else{

            Drawable d = getResources().getDrawable(R.drawable.control_image);
            Bitmap bitmap = drawableToBitmap(d);
            final Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap,Math.round(bitmap.getWidth() *0.7f),
                    Math.round(bitmap.getHeight() * 0.7f),false);
            googleMap.addMarker(new MarkerOptions()
                    .position(getLocation)
                    .title(cousinAccount.getCousinName())
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getLocation, 15f));
        }

            CousinAddress();

        }

         catch (Exception e) {
            e.printStackTrace();
        }

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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map_view, fragment);
        transaction.commit();
        fragment.getMapAsync(this);
    }

    private Bitmap getMarkerBitmapFromView(View view, Bitmap bitmap) {

        mMarkerImageView.setImageBitmap(bitmap);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return returnedBitmap;
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





}