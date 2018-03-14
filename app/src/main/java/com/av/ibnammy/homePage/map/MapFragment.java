package com.av.ibnammy.homePage.map;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.MenuListAdapter;
import com.av.ibnammy.databinding.FragmentMapBinding;
import com.av.ibnammy.homePage.menu.MenuFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import hollowsoft.slidingdrawer.OnDrawerCloseListener;
import hollowsoft.slidingdrawer.OnDrawerOpenListener;


/**
 * Created by Maiada on 12/27/2017.
 */

public class MapFragment extends Fragment implements
        OnMapReadyCallback,OnDrawerOpenListener, OnDrawerCloseListener
{


    public FragmentMapBinding fragmentMapBinding;
    public RecyclerView recyclerView;
    public boolean isClicked =false;
    private Animation animSlideUp,animSlideDown;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentMapBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map, container, false);
        View rootView = fragmentMapBinding.getRoot();  // getRoot because onCreateView return view
        setRetainInstance(false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        try{

        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction  = manager.beginTransaction();;
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.fragment_map_view, fragment);
        transaction.commit();
        fragment.getMapAsync( this);

         } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
               }
        Setup_UI();

        return rootView;
    }

    private void Setup_UI() {
        fragmentMapBinding.drawer.setOnDrawerCloseListener(this);
        fragmentMapBinding.drawer.setOnDrawerOpenListener(this);

        fragmentMapBinding.btnRequestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentMapBinding.btnRequestService.setVisibility(View.GONE);
                fragmentMapBinding.btnRequestForHelp.setVisibility(View.GONE);
                fragmentMapBinding.requestService.setVisibility(View.VISIBLE);

            }
        });

        fragmentMapBinding.btnRequestForHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentMapBinding.btnRequestService.setVisibility(View.GONE);
                fragmentMapBinding.btnRequestForHelp.setVisibility(View.GONE);
                fragmentMapBinding.requestHelp.setVisibility(View.VISIBLE);

            }
        });

        animSlideUp= AnimationUtils.loadAnimation( getActivity().getApplicationContext(), R.anim.slide_up);
        animSlideDown = AnimationUtils.loadAnimation( getActivity().getApplicationContext(), R.anim.slide_down);

        fragmentMapBinding.btnSearchService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentMapBinding.drawer.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestService.setVisibility(View.GONE);
                //mTitle.setText("نتائج البحث");
            }
        });

        fragmentMapBinding.btnSearchHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentMapBinding.drawer.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestHelp.setVisibility(View.GONE);
            //    mTitle.setText("نتائج البحث");
            }
        });

        fragmentMapBinding.listItemSearch.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        fragmentMapBinding.listItemSearch.setHasFixedSize(true);
        fragmentMapBinding.listItemSearch.setAdapter(new RecyclerView.Adapter() {
            @Override
            public MenuListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_search,parent,false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        fragmentMapBinding.userBoxDetails.setVisibility(View.VISIBLE);
                        fragmentMapBinding.listItemSearch.setVisibility(View.GONE);
                        fragmentMapBinding.drawer.setVisibility(View.GONE);
                        fragmentMapBinding.mapFrame.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                        fragmentMapBinding.mapFrame.setAlpha((float) 0.8);

                    }
                });
                return new MenuListAdapter.MyViewHolder(view);

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 8;
            }
        });


        fragmentMapBinding.searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(fragmentMapBinding.getRoot().getContext(),"isClicked",Toast.LENGTH_LONG).show();
            }
        });
        fragmentMapBinding.checkboxAdvanceSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean isChecked =fragmentMapBinding.checkboxAdvanceSearch.isChecked();
                if(isChecked){

                    fragmentMapBinding.advanceSearch.setVisibility(View.VISIBLE);
                    fragmentMapBinding.advanceSearch.startAnimation(animSlideUp);

                }else{

                    fragmentMapBinding.advanceSearch.setVisibility(View.GONE);
                    fragmentMapBinding.advanceSearch.startAnimation(animSlideDown);

                }
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        // mMap = googleMap;
       try{
        googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(
                getContext(), R.raw.style_json));


        // Get Item id to change with my new drawable
        // control_image.xml  have two id  id:img_frame_profile and id:img_profile_image
        // To change one of them ex: change img_profile_image
        //  LayerDrawable layer = (LayerDrawable)getActivity().getResources().getDrawable(R.drawable.control_image); // Call drawable
        // layer.setDrawableByLayerId(R.id.img_profile_photo, getActivity().getResources().getDrawable(R.mipmap.ic_doctors)); // replace and add

        // You need to convert drawable to bitmap coz marker options take only bitmap
        Drawable d = getResources().getDrawable(R.drawable.control_image);
        Bitmap bitmap = drawableToBitmap(d);


        // control size of marker icon
        final Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap,Math.round(bitmap.getWidth() *0.7f),
                Math.round(bitmap.getHeight() * 0.7f),false);

        final LatLng getLocation = new LatLng( 29.978124,31.284781);

        googleMap.addMarker(new MarkerOptions().position(getLocation).title("The Courtyard").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        float zoom = 10f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLocation, zoom);
        googleMap.animateCamera(cameraUpdate);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("The Courtyard").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }
        });
       } catch (Exception e) {
           e.printStackTrace();
       }

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


 /*   @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
      *//*  if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
             //   onBackButtonPressed();
                return true;
            }
        }
*//*
        return false;
    }*/

    private void onBackButtonPressed() {


        if(fragmentMapBinding.userBoxDetails.getVisibility()==View.VISIBLE){

            fragmentMapBinding.userBoxDetails.setVisibility(View.GONE);
            fragmentMapBinding.mapFrame.setVisibility(View.GONE);
            fragmentMapBinding.drawer.setVisibility(View.VISIBLE);

        }else  if(fragmentMapBinding.drawer.getVisibility()==View.VISIBLE){

            fragmentMapBinding.drawer.setVisibility(View.GONE);
            fragmentMapBinding.btnRequestForHelp.setVisibility(View.VISIBLE);
            fragmentMapBinding.btnRequestService.setVisibility(View.VISIBLE);


        }else if(fragmentMapBinding.requestHelp.getVisibility()==View.VISIBLE
                ||
                fragmentMapBinding.requestService.getVisibility()==View.VISIBLE){

            fragmentMapBinding.requestHelp.setVisibility(View.GONE);
            fragmentMapBinding.requestService.setVisibility(View.GONE);

            fragmentMapBinding.btnRequestForHelp.setVisibility(View.VISIBLE);
            fragmentMapBinding.btnRequestService.setVisibility(View.VISIBLE);

        }
        else {

              getActivity(). getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottom_side_fragment, new MenuFragment(), "mapFragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();          //  navigation.getMenu().getItem(0).setChecked(true);

        }
      /*  else {

                  *//*  getActivity(). getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottom_side_fragment, new MenuFragment(), "mapFragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();*//*




    }*/

    }

    @Override
    public void onDrawerClosed() {
        fragmentMapBinding.imgSlideUp.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.arrow_slide_up));
    }

    @Override
    public void onDrawerOpened() {
        fragmentMapBinding.imgSlideUp.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.arrow_slide_down));

    }
}
