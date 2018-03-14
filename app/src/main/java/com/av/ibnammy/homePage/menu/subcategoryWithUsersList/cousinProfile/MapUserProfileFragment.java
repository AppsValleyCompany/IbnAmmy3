package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Maiada on 1/3/2018.
 */

public class MapUserProfileFragment  extends Fragment implements
        OnMapReadyCallback {


    MapStyleOptions mapStyleOptions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_map_user, container, false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map_view, fragment);
        transaction.commit();
        fragment.getMapAsync(this);

        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(getActivity().getApplicationContext(),R.raw.style_json);


        return rooView;
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        // mMap = googleMap;
        googleMap.setMapStyle(mapStyleOptions);

        // Get Item id to change with my new drawable
        // control_image.xml  have two id  id:img_frame_profile and id:img_profile_image
        // To change one of them ex: change img_profile_image
        LayerDrawable layer = (LayerDrawable)getActivity().getResources().getDrawable(R.drawable.control_image); // Call drawable
     //   layer.setDrawableByLayerId(R.id.img_profile_photo, getActivity().getResources().getDrawable(R.mipmap.ic_chefs)); // replace and add

        // You need to convert drawable to bitmap coz marker options take only bitmap
        Drawable d = getResources().getDrawable(R.drawable.control_image);
        Bitmap bitmap = drawableToBitmap(d);

        // control size of marker icon
        final Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap,Math.round(bitmap.getWidth() *0.7f),
                Math.round(bitmap.getHeight() * 0.7f),false);

        final LatLng getLocation = new LatLng( 29.978124,31.284781);

        googleMap.addMarker(new MarkerOptions().position(getLocation).title("The Courtyard").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        float zoom = 15f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLocation, zoom);
        googleMap.animateCamera(cameraUpdate);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("The Courtyard").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }
        });

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
}