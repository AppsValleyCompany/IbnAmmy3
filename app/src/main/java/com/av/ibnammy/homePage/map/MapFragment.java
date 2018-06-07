package com.av.ibnammy.homePage.map;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentMapBinding;
import com.av.ibnammy.homePage.menu.MenuListAdapter;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;
import com.av.ibnammy.viewprofile.Profile;
import com.av.ibnammy.viewprofile.ProfileModel;
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

import hollowsoft.slidingdrawer.OnDrawerCloseListener;
import hollowsoft.slidingdrawer.OnDrawerOpenListener;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;


/**
 * Created by Maiada on 12/27/2017.
 */

public class MapFragment extends Fragment implements
        OnMapReadyCallback,OnDrawerOpenListener, OnDrawerCloseListener
{

    public FragmentMapBinding fragmentMapBinding;

    public boolean isClicked =false;
    private Animation animSlideUp,animSlideDown;

    private View mCustomMarkerView;
    private ImageView mMarkerImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentMapBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map, container, false);
        View rootView = fragmentMapBinding.getRoot();  // getRoot because onCreateView return view
        Locale.setDefault(new Locale("ar","EG"));

        mCustomMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_view, null);
        mMarkerImageView = mCustomMarkerView.findViewById(R.id.img_new_marker);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        try{
            FragmentManager manager = getChildFragmentManager();
            FragmentTransaction transaction  = manager.beginTransaction();;
            SupportMapFragment fragment = new SupportMapFragment();
            transaction.add(R.id.fragment_map_view, fragment);
            transaction.commit();
            fragment.getMapAsync( this);
        }
        catch (Exception e)
        {
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
             /*   fragmentMapBinding.tvNotActivatedOne.setVisibility(View.VISIBLE);
                 fragmentMapBinding.tvNotActivatedTwo.setVisibility(View.VISIBLE);*/
                fragmentMapBinding.requestService.setVisibility(View.VISIBLE);
            }
        });

        fragmentMapBinding.btnRequestForHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentMapBinding.btnRequestService.setVisibility(View.GONE);
                fragmentMapBinding.btnRequestForHelp.setVisibility(View.GONE);

                //  fragmentMapBinding.tvNotActivatedTwo.setVisibility(View.VISIBLE);

                fragmentMapBinding.requestHelp.setVisibility(View.VISIBLE);

            }
        });

        animSlideUp= AnimationUtils.loadAnimation( getActivity().getApplicationContext(), R.anim.slide_up);
        animSlideDown = AnimationUtils.loadAnimation( getActivity().getApplicationContext(), R.anim.slide_down);

        fragmentMapBinding.btnSearchService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                fragmentMapBinding.drawer.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestService.setVisibility(View.GONE);*/
                //mTitle.setText("نتائج البحث");
                fragmentMapBinding.tvNotActivatedOne.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestService.setVisibility(View.GONE);

            }
        });

        fragmentMapBinding.btnSearchHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* fragmentMapBinding.drawer.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestHelp.setVisibility(View.GONE);*/
                //    mTitle.setText("نتائج البحث");
                fragmentMapBinding.tvNotActivatedOne.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestHelp.setVisibility(View.GONE);


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


        fragmentMapBinding.spinnerRequestHelp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==4)
                    fragmentMapBinding.frameBloodType.setVisibility(View.VISIBLE);
                else
                    fragmentMapBinding.frameBloodType.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fragmentMapBinding.spinnerRequestList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==3){
                    fragmentMapBinding.spinnerRequestList.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        showProgressBar();

        googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(
                getContext(), R.raw.style_json));

        //  googleMap.setTrafficEnabled(true);

        getData(googleMap);

    }

    @Override
    public void onDrawerClosed() {
        fragmentMapBinding.imgSlideUp.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.arrow_slide_up));
    }

    @Override
    public void onDrawerOpened() {
        fragmentMapBinding.imgSlideUp.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.arrow_slide_down));

    }

    private void  getData(final GoogleMap googleMap){
        Bundle bundle=CommonUtils.loadCredentials(getContext());
        String phone=bundle.getString(Constants.PHONE_KEY);
        String password=bundle.getString(Constants.PASSWORD_KEY);
        ProfileModel.GetProfileData(phone, password, new GetCallback.ProfileCallBack() {
            @Override
            public void onSuccess(Profile profile) {
                hideProgressBar();
                showAddressAndSetProfileImageOnMap(googleMap,profile);
            }

            @Override
            public void onFailure(String error) {
                hideProgressBar();
                if(getActivity()!=null)
                    Toast.makeText(getActivity(), "حدث خطا", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddressAndSetProfileImageOnMap(final GoogleMap googleMap, final Profile profile) {

        try{

            // carrefour 29.982475, 31.274174
            // Gabr   29.977078, 31.282030
            // Me  29.976950, 31.282609

            //test location
           /* profile.setHomeLatitude("29.9749457");
            profile.setHomeLongitude("31.2798031");*/

            if(!profile.getHomeLatitude().equals("")&&!profile.getHomeLongitude().equals("")){
                Double  lat = Double.parseDouble(profile.getHomeLatitude());
                Double  lng = Double.parseDouble(profile.getHomeLongitude());

                final LatLng getLocation = new LatLng( lat,lng);

                if(!profile.getProfileImage().equals("")&&getContext()!=null){
                    Glide.with(getContext())
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.mipmap.male)
                                    .fitCenter().transform(new CircleCrop())).
                            asBitmap().
                            load(IMG_URL + profile.getProfileImage())
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(getLocation)
                                            .title(profile.getFullUserName())
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
                            .title(profile.getFullUserName())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getLocation, 13f));

                }
            }else {

                if(getActivity()!=null)
                    Toast.makeText(getActivity(), "الرجاء تحديث بياناتك اولا ", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showProgressBar(){
        fragmentMapBinding.pbLoading.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        fragmentMapBinding.pbLoading.setVisibility(View.GONE);
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



}
