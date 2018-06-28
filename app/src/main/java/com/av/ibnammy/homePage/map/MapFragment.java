package com.av.ibnammy.homePage.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import android.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.location.Location;

import android.net.Uri;

import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.av.ibnammy.R;

import com.av.ibnammy.databinding.FragmentMapBinding;
import com.av.ibnammy.databinding.SearchDetailsBoxBinding;

import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile.CousinProfileActivity;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;
import com.av.ibnammy.viewprofile.Profile;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hollowsoft.slidingdrawer.OnDrawerCloseListener;
import hollowsoft.slidingdrawer.OnDrawerOpenListener;

import static com.av.ibnammy.networkUtilities.ApiClient.BASE_URL;
import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;


/**
 * Created by Maiada on 12/27/2017.
 */

public class MapFragment extends Fragment implements
        OnMapReadyCallback,
        View.OnKeyListener,
        OnDrawerOpenListener,
        OnDrawerCloseListener,
        GetCallback.DDServiceCategorySearch,
        GetCallback.DDCountry,
        GetCallback.DistrictsCountry,
        GetCallback.DDServiceTypeSearch,
        GetCallback.AllSearchResult,
        View.OnTouchListener,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener



{

    private FragmentMapBinding fragmentMapBinding;
    private SearchDetailsBoxBinding searchDetailsBoxBinding;


    private Animation animSlideUp,animSlideDown;
    private View rootView;
    private View mCustomMarkerView;
    private ImageView mMarkerImageView;

    private GoogleMap mGoogleMap;

    private SearchResultAccountAdapter searchResultAccountAdapter;


    private Profile mProfile;
    private MapSearchModel mapSearchModel;
    private SearchMap searchMap ;

    private String phone,password,userId,imgProfile,lat,lng ="";
    private int categoryType = 0;
    private String mobileNumber="";
    private String gender="";
    private String currentLat,currentLng;

    private final static int REQUEST_ID_CALL_PHONE_PERMISSIONS=0x1;
    private final static int REQUEST_ID_FINE_LOCATION_PERMISSIONS=0x2;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x3;

    ArrayList<CousinAccount> searchResults = new ArrayList<>();

    CousinAccount searchResult = new CousinAccount();

    private Location myLocation;
    private GoogleApiClient googleApiClient;
    private  int TAG_SERVICE_OR_HELP  = 0;
    private  int TAG_CURRENT_LOCATION = 0;

    private  boolean isRegistered = false  ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentMapBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_map, container, false);
        rootView = fragmentMapBinding.getRoot();  // getRoot because onCreateView return view
        Locale.setDefault(new Locale("ar","EG"));

        changeToMapFragment();

        Setup_UI();

        return rootView;
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.map_location))
                .setPositiveButton(getResources().getString(R.string.current_location),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        showProgressBar();
                        requestPermissionLocation();


                    }
                })
                .setNegativeButton(getResources().getString(R.string.register_location),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        showProgressBar();
                        getData();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private void changeToMapFragment() {
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
            Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
        }
    }

    private void Setup_UI() {


        mapSearchModel = new MapSearchModel();
        searchMap  = new SearchMap();

        Bundle bundle = CommonUtils.loadCredentials(getContext());

        phone         = bundle.getString(Constants.PHONE_KEY);
        password      = bundle.getString(Constants.PASSWORD_KEY);
        userId        = bundle.getString(Constants.USER_ID);
        imgProfile    = bundle.getString(Constants.USER_IMG);
        lat           = bundle.getString(Constants.USER_LAT);
        lng           = bundle.getString(Constants.USER_LNG);

        searchResult.setCousinId(userId);
        searchResult.setCousinImage(imgProfile);

        searchResults.add(searchResult);

        searchMap.setMobile(phone);
        searchMap.setPassword(password);

        animSlideUp   = AnimationUtils.loadAnimation( getActivity().getApplicationContext(), R.anim.slide_up);
        animSlideDown = AnimationUtils.loadAnimation( getActivity().getApplicationContext(), R.anim.slide_down);

        mCustomMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_view, null);
        mMarkerImageView  = mCustomMarkerView.findViewById(R.id.img_new_marker);

        SetupUiVisibleOrGone();

    }


    private void SetupUiVisibleOrGone() {


        fragmentMapBinding.serviceOrHelpLayout.btnRequestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getContext()!=null){
                    TAG_SERVICE_OR_HELP = 1;
                    showAlertDialog();
                }

            }
        });

        fragmentMapBinding.serviceOrHelpLayout.btnRequestForHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getContext()!=null){
                    TAG_SERVICE_OR_HELP = 2;
                    showAlertDialog();

                }

            }
        });


        fragmentMapBinding.requestServiceLayout.btnSearchService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.GONE);
            }
        });

        fragmentMapBinding.requestHelpLayout.btnSearchHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    fragmentMapBinding.requestHelpLayout.requestHelp.setVisibility(View.GONE);
                if(getView()!=null)
                    Snackbar.make(getView(),getResources().getString(R.string.not_activated),Snackbar.LENGTH_LONG).show();
            }
        });


        fragmentMapBinding.requestServiceLayout.checkboxAdvanceSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean isChecked =fragmentMapBinding.requestServiceLayout.checkboxAdvanceSearch.isChecked();
                if(isChecked){
                    fragmentMapBinding.requestServiceLayout.advanceSearchLayout.advanceSearchBox.setVisibility(View.VISIBLE);
                    fragmentMapBinding.requestServiceLayout.advanceSearchLayout.advanceSearchBox.startAnimation(animSlideUp);
                }else{
                    fragmentMapBinding.requestServiceLayout.advanceSearchLayout.advanceSearchBox.setVisibility(View.GONE);
                    fragmentMapBinding.requestServiceLayout.advanceSearchLayout.advanceSearchBox.startAnimation(animSlideDown);
                    resetAllDDList();
                }
            }
        });

        fragmentMapBinding.btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLat = null;
                currentLng = null;
                if(getActivity()!=null)
                 getMyLocation();
            }
        });

        fragmentMapBinding.searchListLayout.slideDrawer.setOnDrawerCloseListener(this);
        fragmentMapBinding.searchListLayout.slideDrawer.setOnDrawerOpenListener(this);

        if(getActivity()!=null)
         requestPermissionLocation();


        onBackPressed();
        fillDDListSearch();
        fetchDDFromSpinner();

    }

    private void fetchDDFromSpinner(){

        fragmentMapBinding.requestHelpLayout.spinnerRequestHelp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==4)
                    fragmentMapBinding.requestHelpLayout.frameBloodType.setVisibility(View.VISIBLE);
                else
                    fragmentMapBinding.requestHelpLayout.frameBloodType.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fragmentMapBinding.requestServiceLayout.btnSearchService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = fragmentMapBinding.requestServiceLayout.searchBarName.getText().toString();
                if(firstName.length()!=0)
                    searchMap.setFirst_Name(firstName);
                else
                    searchMap.setFirst_Name("");

                String searchText = fragmentMapBinding.requestServiceLayout.searchBarText.getText().toString();
                if(searchText.length()!=0)
                    searchMap.setService_SubcategoryName(searchText);
                else
                    searchMap.setService_SubcategoryName("");


                int countryPosition = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCountry.getSelectedItemPosition();
                String country = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCountry.getItemAtPosition(countryPosition).toString();
                if(countryPosition!=0)
                    searchMap.setHome_Country(country);
                else
                    searchMap.setHome_Country("");

                int districtPosition = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCity.getSelectedItemPosition();
                String district = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCity.getItemAtPosition(districtPosition).toString();
                if(districtPosition!=0)
                    searchMap.setHome_District(district);
                else
                    searchMap.setHome_District("");

                int genderPosition = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddGender.getSelectedItemPosition();
                String gender = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddGender.getItemAtPosition(genderPosition).toString();
                if(genderPosition!=0)
                    searchMap.setGender(gender);
                else
                    searchMap.setGender("");

                int bloodTypePosition = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddBloodType.getSelectedItemPosition();
                String bloodType = fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddBloodType.getItemAtPosition(bloodTypePosition).toString();
                if(bloodTypePosition!=0)
                    searchMap.setBlood_Typ(bloodType);
                else
                    searchMap.setBlood_Typ("");

                String searchMapJson = new Gson().toJson(searchMap);

               Toast.makeText(getContext(), searchMapJson, Toast.LENGTH_LONG).show();
                getAllSearchOnMap(searchMapJson);


            /*    */


            }
        });

    }

    private void getAllSearchOnMap(String searchMapJson) {
        mGoogleMap.clear();
        hideSoftKeyboard();
        showProgressBar();
        fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.GONE);
        mapSearchModel.SearchResultAccounts(this,searchMapJson);

    }

    private void fillDDListSearch() {
        showProgressBar();
        mapSearchModel.ServiceCategorySearch(this);
        mapSearchModel.ServiceTypeSearch(this);
        mapSearchModel.DDCountry(this);

    }

    private void  resetAllDDList(){
        fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCountry.setSelection(0);
        fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCity.setSelection(0);
        fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddServiceType.setSelection(0);
        fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddGender.setSelection(0);
        fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddBloodType.setSelection(0);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(
                getContext(),R.raw.style_json));

        changeGoogleMapToolbarPosition();

        mGoogleMap.setOnMarkerClickListener(this);

    }

    private void changeGoogleMapToolbarPosition() {
        View googleMapToolbar = fragmentMapBinding.fragmentMapView.findViewWithTag("GoogleMapToolbar");
        RelativeLayout.LayoutParams glLayoutParams = (RelativeLayout.LayoutParams)googleMapToolbar.getLayoutParams();
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        glLayoutParams.setMargins(0,0,0,135);
        googleMapToolbar.setLayoutParams(glLayoutParams);
    }


    private void  getData(){

        if(lat.equals("")&&lng.equals("")){
            if(getView()!=null)
                Snackbar.make(getView(),getResources().getString(R.string.no_location),Snackbar.LENGTH_LONG).show();

            hideProgressBar();

        }else {

            isRegistered = true;

            searchResults.get(0).setHomeLatitude(lat);
            searchResults.get(0).setHomeLongitude(lng);

            searchMap.setHome_Latitude(lat);
            searchMap.setHome_Longitude(lng);


            searchForServiceOrHelp();
            setCousinLocationsSearchResultOnMap();
            hideProgressBar();

        }
    }

    private void showProgressBar(){
        fragmentMapBinding.pbLoading.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        fragmentMapBinding.pbLoading.setVisibility(View.GONE);
    }

    private void onDataFailure(){
        hideProgressBar();
        if(getView()!=null)
            Snackbar.make(getView(),getResources().getString(R.string.error),Snackbar.LENGTH_LONG).show();

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
    @Override
    public void onDrawerClosed() {
        fragmentMapBinding.btnCurrentLocation.setVisibility(View.VISIBLE);
        fragmentMapBinding.bgList.setBackgroundColor(0);
        fragmentMapBinding.searchListLayout.imgSlideUp.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.arrow_slide_up));
    }

    @Override
    public void onDrawerOpened() {
        fragmentMapBinding.btnCurrentLocation.setVisibility(View.GONE);
        fragmentMapBinding.searchListLayout.imgSlideUp.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.arrow_slide_down));
        fragmentMapBinding.bgList.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        fragmentMapBinding.bgList.setAlpha((float) 0.9);        //fragmentMapBinding.bgList.setAlpha((float) 0.8);
        //    fragmentMapBinding.searchListLayout.rowItemLayout.rowItemSearch.setVisibility(View.GONE);

    }

    private void onBackPressed(){
        rootView.setOnTouchListener(this);
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if(fragmentMapBinding.searchListLayout.slideDrawer.getVisibility()==View.VISIBLE&&
                    fragmentMapBinding.requestServiceLayout.requestService.getVisibility()==View.VISIBLE){

                fragmentMapBinding.bgList.setBackgroundColor(0);
                fragmentMapBinding.searchListLayout.slideDrawer.setVisibility(View.GONE);
                fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.VISIBLE);
                searchResults.subList(1,searchResults.size()).clear();
                setCousinLocationsSearchResultOnMap();
                TAG_CURRENT_LOCATION = 0 ;


            }else
            if(fragmentMapBinding.requestServiceLayout.requestService.getVisibility()==View.VISIBLE){

                fragmentMapBinding.serviceOrHelpLayout.serviceOrHelp.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.GONE);

            }else if(fragmentMapBinding.requestHelpLayout.requestHelp.getVisibility()==View.VISIBLE) {

                fragmentMapBinding.serviceOrHelpLayout.serviceOrHelp.setVisibility(View.VISIBLE);
                fragmentMapBinding.requestHelpLayout.requestHelp.setVisibility(View.GONE);

            }else if(fragmentMapBinding.searchListLayout.slideDrawer.getVisibility()==View.VISIBLE) {

                fragmentMapBinding.bgList.setBackgroundColor(0);
                fragmentMapBinding.searchListLayout.slideDrawer.setVisibility(View.GONE);
                fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.VISIBLE);
                searchResults.subList(1,searchResults.size()).clear();
                TAG_CURRENT_LOCATION = 0 ;
                setCousinLocationsSearchResultOnMap();


            }else {

                rootView.clearFocus();
                googleApiClient.disconnect();
            }


            return true;
        }
        return false;
    }

    @Override
    public void onDDServiceCategorySearchSuccess(final ArrayList<ServiceCategory> categoryArrayList) {
        hideProgressBar();

        if(getContext()!=null){


            ArrayList<String> serviceCategoryList = new ArrayList<>();

            for(ServiceCategory serviceTypeSearch : categoryArrayList){
                serviceCategoryList.add(serviceTypeSearch.getName());
            }


            ArrayAdapter<ServiceCategory> serviceCategoryArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, serviceCategoryList);
            fragmentMapBinding.requestServiceLayout.spinnerRequestList.setAdapter(serviceCategoryArrayAdapter);

            fragmentMapBinding.requestServiceLayout.spinnerRequestList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=0)
                        searchMap.setService_CategoryID(categoryArrayList.get(position).getServiceCategoryID());
                    else
                        searchMap.setService_CategoryID("");


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    @Override
    public void onDDServiceCategorySearchFailure() {
        onDataFailure();
    }

    @Override
    public void onDDCountrySuccess(ArrayList<String> countryList) {
        hideProgressBar();

        if(getContext()!=null) {
            ArrayAdapter<String> countryListAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, countryList);
            fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCountry.setAdapter(countryListAdapter);

            fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=0){
                        String country =  fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCountry.getItemAtPosition(position).toString();
                        getDistrictOfCountry(country);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void onDDCountryFailure() {
        onDataFailure();
    }

    protected void getDistrictOfCountry(String country) {
        showProgressBar();
        mapSearchModel.DistrictsOfCountry(this,country);
    }

    @Override
    public void onDistrictsCountrySuccess(ArrayList<String> districtList) {
        hideProgressBar();

        if(getContext()!=null) {
            ArrayAdapter<String> districtListAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, districtList);
            fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddCity.setAdapter(districtListAdapter);
        }
    }

    @Override
    public void onDistrictsCountryFailure() {
        onDataFailure();
    }

    @Override
    public void onTypesFetchSuccess(final ArrayList<ServiceTypeSearch> serviceTypeArrayList) {

        hideProgressBar();

        ArrayList<String> serviceTypeList = new ArrayList<>();

        for(ServiceTypeSearch serviceTypeSearch : serviceTypeArrayList){
            serviceTypeList.add(serviceTypeSearch.getName());
        }

        if(getContext()!=null) {

            ArrayAdapter<ServiceTypeSearch> categoryTypeArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, serviceTypeList);
            fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddServiceType.setAdapter(categoryTypeArrayAdapter);


            fragmentMapBinding.requestServiceLayout.advanceSearchLayout.ddServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=0)
                        searchMap.setService_TypeID(serviceTypeArrayList.get(position).getService_typeID());
                    else
                        searchMap.setService_TypeID("");

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

    }

    @Override
    public void onTypesFetchFailure() {
        onDataFailure();
    }

    @Override
    public void onSearchResultSuccess(ArrayList<CousinAccount> searchResultArrayList) {

        for(int i=0;i<searchResultArrayList.size();i++){
            CousinAccount searchResult = searchResultArrayList.get(i);
            searchResults.add(searchResult);
            TAG_CURRENT_LOCATION = 1;
        }
        hideProgressBar();
        setCousinSearchResultList(searchResultArrayList);
        setCousinLocationsSearchResultOnMap();


    }

    private void setCousinSearchResultList(ArrayList<CousinAccount> searchResultArrayList) {

        fragmentMapBinding.searchListLayout.slideDrawer.setVisibility(View.VISIBLE);
        fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.GONE);

 /*       SearchResult  searchResult = searchResultArrayList.get(0);
        fragmentMapBinding.searchListLayout.rowItemLayout.txtUserName.setText(searchResult.getCousinName());*/

        fragmentMapBinding.searchListLayout.resultValue.setText(""+searchResultArrayList.size());

        fragmentMapBinding.searchListLayout.listItemSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMapBinding.searchListLayout.listItemSearch.setHasFixedSize(true);

        searchResultAccountAdapter = new SearchResultAccountAdapter(getContext(),searchResultArrayList);
        fragmentMapBinding.searchListLayout.listItemSearch.setAdapter(searchResultAccountAdapter);

        searchResultAccountAdapter.notifyDataSetChanged();

    }


    private void setCousinLocationsSearchResultOnMap() {
        try {

            mGoogleMap.clear();
            for (int i=0;i<=searchResults.size();i++) {
                final CousinAccount searchResult = searchResults.get(i);

                if (getContext() != null&&!searchResult.getHomeLatitude().equals("") && !searchResult.getHomeLongitude().equals("")) {
                    Double lat = Double.parseDouble(searchResult.getHomeLatitude());
                    Double lng = Double.parseDouble(searchResult.getHomeLongitude());

                    final LatLng getLocation = new LatLng(lat, lng);

                    if(i==0&&!imgProfile.equals("")){
                        Glide.with(getContext())
                                .applyDefaultRequestOptions(new RequestOptions()
                                        .error(R.mipmap.male)
                                        .fitCenter().transform(new CircleCrop())).
                                 asBitmap()
                                .load(IMG_URL+imgProfile)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                        Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                                .position(getLocation)
                                                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(mCustomMarkerView, bitmap))));
                                        marker.setTag(searchResult);

                                        float zoom = 15f;
                                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLocation, zoom);
                                        mGoogleMap.animateCamera(cameraUpdate);
                                    }

                                });
                    }
                    else{
                        final int finalI = i;
                        Glide.with(getContext())
                                .applyDefaultRequestOptions(new RequestOptions()
                                        .fitCenter().transform(new CircleCrop())).
                                asBitmap()
                                .load(R.mipmap.male)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                        Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                                .position(getLocation)
                                                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(mCustomMarkerView, bitmap))));
                                        marker.setTag(searchResult);
                                        if(finalI == 0){
                                            float zoom = 15f;
                                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLocation, zoom);
                                            mGoogleMap.animateCamera(cameraUpdate);
                                        }else if(finalI+1 == searchResults.size()){

                                            Double lat = Double.parseDouble(searchMap.getHome_Latitude());
                                            Double lng = Double.parseDouble(searchMap.getHome_Longitude());
                                            final LatLng getLocation = new LatLng(lat, lng);
                                            float zoom = 7f;
                                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLocation, zoom);
                                            mGoogleMap.animateCamera(cameraUpdate);
                                            hideProgressBar();

                                        }

                                    }

                                });
                    }


                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSearchResultError() {
        hideProgressBar();
        fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.VISIBLE);
        if(getView()!=null)
            Snackbar.make(getView(),getResources().getString(R.string.no_search_result),Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSearchResultFailure() {
        fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.VISIBLE);
        onDataFailure();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        final CousinAccount cousinAccount = (CousinAccount) marker.getTag();
        if(!userId.equals(cousinAccount.getCousinId())&&getContext()!=null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View alertView = inflater.inflate(R.layout.search_details_box, null);

            searchDetailsBoxBinding = DataBindingUtil.bind(alertView);

            //Creating an alertdialog builder
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

            //Adding our dialog box to the view of alert dialog
            alert.setView(alertView);

            //Creating an alert dialog
            final AlertDialog   alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            // fragmentMapBinding.detailsBoxLayout.searchBoxDetails.setVisibility(View.VISIBLE);

            gender = cousinAccount.getGender();

            mobileNumber =  cousinAccount.getCousinMobile();
            if(mobileNumber.length()==10)
                mobileNumber = "+20"+mobileNumber;
            else
                mobileNumber = "+"+mobileNumber;

            setCousinDetailsOnDetailsBox(cousinAccount);

            searchDetailsBoxBinding.txtCallService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeCall(mobileNumber);
                    alertDialog.cancel();
                }
            });


            searchDetailsBoxBinding.imgChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openCousinWhatAppChat(mobileNumber,gender);
                    alertDialog.cancel();
                }
            });


            searchDetailsBoxBinding.imgProfileDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openCousinProfileFromDetailsBox(cousinAccount);
                    alertDialog.cancel();

                }
            });


        }




        return false;
    }


    private synchronized void setUpGClient()
    {

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0, this)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        if(!googleApiClient.isConnected())
           googleApiClient.connect();


        // Toast.makeText(getActivity(), "googleApiClient is connected", Toast.LENGTH_LONG).show();

    }

    private boolean requestPermissionLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ID_FINE_LOCATION_PERMISSIONS);

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            hideProgressBar();

            return false;
        } else {
            if(googleApiClient==null&&getActivity()!=null)
                setUpGClient();
            else
                searchForServiceOrHelp();

            return  true;
        }

    }
    private void getMyLocation(){

        showProgressBar();
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected())
            {
                int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    myLocation =     LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    int permissionLocation = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED)
                                    {
                                        myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                                        if(TAG_CURRENT_LOCATION==0)
                                        searchForServiceOrHelp();
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try
                                    {
                                        status.startResolutionForResult(getActivity(),REQUEST_CHECK_SETTINGS_GPS);
                                        hideProgressBar();

                                    }
                                    catch (IntentSender.SendIntentException e)
                                    {
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    hideProgressBar();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && getActivity()!=null) {
            int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionLocation == PackageManager.PERMISSION_GRANTED){
                setUpGClient();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CHECK_SETTINGS_GPS) {
            if (resultCode == Activity.RESULT_OK) {
                if(getActivity()!=null)
                  getMyLocation();
            } else {
                requestPermissionLocation();
            }
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null && getActivity()!=null&&googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && getActivity()!=null&&googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(getActivity()!=null&&googleApiClient!=null)
          getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        hideProgressBar();
        myLocation = location;
        if (myLocation != null) {
            Double latitude = myLocation.getLatitude();
            Double longitude = myLocation.getLongitude();

            searchMap.setHome_Latitude(String.valueOf(latitude));
            searchMap.setHome_Longitude(String.valueOf(longitude));

            if(currentLat!=null&&currentLng!=null){

                if(!isRegistered){

                    currentLat = String.valueOf(latitude);
                    currentLng = String.valueOf(longitude);

                    searchResults.get(0).setHomeLatitude(currentLat);
                    searchResults.get(0).setHomeLongitude(currentLng);

                }


               // setCousinLocationsSearchResultOnMap();


            }else{


                currentLat = String.valueOf(latitude);
                currentLng = String.valueOf(longitude);

                searchResults.get(0).setHomeLatitude(currentLat);
                searchResults.get(0).setHomeLongitude(currentLng);

                fragmentMapBinding.btnCurrentLocation.setVisibility(View.VISIBLE);

                setCousinLocationsSearchResultOnMap();

            }





        }
    }


    private void openCousinWhatAppChat(String mobileNumber, String  gender) {

        boolean isInstalled = whatsAppInstalledOrNot("com.whatsapp");
        if(isInstalled) {
            if ((gender.equals(getResources().getString(R.string.female)) && categoryType == 2) ||gender.equals(getResources().getString(R.string.male))) {
                startChat(mobileNumber);
            } else
            if (getView()!= null)
                Snackbar.make(getView(), getResources().getString(R.string.not_allow_to_chat), Snackbar.LENGTH_LONG).show();

        }else {
            if (getView()!= null)
                Snackbar.make(getView(), getResources().getString(R.string.install_whatsapp), Snackbar.LENGTH_LONG).show();

        }

    }

    private void openCousinProfileFromDetailsBox(CousinAccount cousinAccount) {
        int getCategoryType =  Integer.parseInt(cousinAccount.getCategoryTypeID());

        if(getCategoryType==1){
            categoryType=0;
        }else if(getCategoryType==2){
            categoryType = 1;
        }else {
            categoryType = 2;
        }


        Intent cousinProfileIntent = new Intent(getContext(), CousinProfileActivity.class);
        cousinProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cousinProfileIntent.putExtra("CousinDate", cousinAccount);
        cousinProfileIntent.putExtra("CategoryType", categoryType);
        getContext().startActivity(cousinProfileIntent);

    }

    private void makeCall(String mobileNumber){
        if(checkPermissionsCallPhone()){
            if(!mobileNumber.equals(""))
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+mobileNumber)));
        }else {
            if(getView()!=null)
                Snackbar.make(getView(),getResources().getString(R.string.allow_to_call), Snackbar.LENGTH_LONG).show();
        }

    }

    private boolean checkPermissionsCallPhone(){


        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(),
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_CALL_PHONE_PERMISSIONS);
            }
            return false;
        } else {
            return true;
        }

    }

    private void startChat(String mobileNumber){
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone="+mobileNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }
    private boolean whatsAppInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
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

    private void setCousinDetailsOnDetailsBox(CousinAccount cousinAccount) {

        if(!cousinAccount.getCousinName().equals(""))
            searchDetailsBoxBinding.tvCousinName.setText(cousinAccount.getCousinName());

        if(!cousinAccount.getCousinJob().equals(""))
            searchDetailsBoxBinding.tvCousinJob.setText(cousinAccount.getCousinJob());

        if(!cousinAccount.getHomeCountry().equals("")||!cousinAccount.getHomeDistrict().equals(""))
            searchDetailsBoxBinding.tvCousinAddress.setText(cousinAccount.getHomeCountry()+" "+cousinAccount.getHomeDistrict());


        if(!cousinAccount.getCousinImage().equals("")){
            Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .error(R.mipmap.male)
                            .fitCenter().transform(new CircleCrop()))
                    .load(IMG_URL+cousinAccount.getCousinImage())
                    .into(searchDetailsBoxBinding.imgCousin) ;

        }


        if(!cousinAccount.getCousinImage().equals("")){
            Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .error(R.mipmap.male)
                            .fitCenter().transform(new CircleCrop()))
                    .load(IMG_URL+cousinAccount.getCousinImage())
                    .listener(new RequestListener<Drawable>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                      searchDetailsBoxBinding.pbLoadPhotoProfile.setVisibility(View.GONE);
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                      searchDetailsBoxBinding.pbLoadPhotoProfile.setVisibility(View.GONE);
                                      return false;
                                  }
                              }
                    )
                    .into(searchDetailsBoxBinding.imgCousin);


        }else{
            searchDetailsBoxBinding.pbLoadPhotoProfile.setVisibility(View.GONE);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager)getActivity().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    public void  disableButton(){
        fragmentMapBinding.serviceOrHelpLayout.btnRequestService.setEnabled(false);
        fragmentMapBinding.serviceOrHelpLayout.btnRequestForHelp.setEnabled(false);

        fragmentMapBinding.requestServiceLayout.btnSearchService.setEnabled(false);


    }

    public void  enableButton(){
        fragmentMapBinding.serviceOrHelpLayout.btnRequestService.setEnabled(true);
        fragmentMapBinding.serviceOrHelpLayout.btnRequestForHelp.setEnabled(true);

        fragmentMapBinding.requestServiceLayout.btnSearchService.setEnabled(true);


    }

    private  void changeSnackBarToRight(String message){
        Snackbar mSnackBar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        View view = mSnackBar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        view.setLayoutParams(params);
        TextView tv =  (view).findViewById(android.support.design.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        else
            tv.setGravity(Gravity.END);

        mSnackBar.show();
    }

    private void searchForServiceOrHelp(){
        if(TAG_SERVICE_OR_HELP==1){
            fragmentMapBinding.serviceOrHelpLayout.serviceOrHelp.setVisibility(View.GONE);
            fragmentMapBinding.requestServiceLayout.requestService.setVisibility(View.VISIBLE);
        }else if(TAG_SERVICE_OR_HELP==2){
            fragmentMapBinding.serviceOrHelpLayout.serviceOrHelp.setVisibility(View.GONE);
            fragmentMapBinding.requestHelpLayout.requestHelp.setVisibility(View.VISIBLE);
        }
    }

}
