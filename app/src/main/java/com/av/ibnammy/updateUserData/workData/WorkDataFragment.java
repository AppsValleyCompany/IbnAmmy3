package com.av.ibnammy.updateUserData.workData;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.NewFragmentWorkDataBinding;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.updateUserData.UpdateDataActivity;
import com.av.ibnammy.updateUserData.UpdateDataModel;
import com.av.ibnammy.updateUserData.UpdateDataView;
import com.av.ibnammy.updateUserData.User;
import com.av.ibnammy.updateUserData.familyData.FamilyDataFragment;
import com.av.ibnammy.updateUserData.uploadMedia.UploadMediaActivity;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkDataFragment extends Fragment implements GetCallback.onDDListsFetched ,GetCallback.onServiceTypesFetched,
GetCallback.onUpdateFinish,UpdateDataView ,GetCallback.onUserDataFetched//, LocationListener

{
    NewFragmentWorkDataBinding binding;
    String longitude,latitude;
    LocationManager  locationManager;
    Bundle bundle;
    String phone ,password;
    String catTypeId,catId,subCatId,serviceTypeId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateDataActivity activity=new UpdateDataActivity();
        activity.addStep(1,getContext());
        bundle=CommonUtils.loadCredentials(getContext());
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext()
                , android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                       new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                               android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        phone=bundle.getString(Constants.PHONE_KEY);
        password=bundle.getString(Constants.PASSWORD_KEY);
        final String data="{'Mobile':"+phone+",'Password':'"+password+"'}";

        UpdateDataModel.get_user_data(data,this);
        binding= DataBindingUtil.inflate(inflater,R.layout.new_fragment_work_data, container, false);
        View v=binding.getRoot();

     //     if(listsDD ==null || serviceTypes.isEmpty()) {
            loadDDlists();
        //  }else{
        //   listsAlreadyFetched();
        //    }

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                onSaveData();
            }
        });

        binding.catTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               CategoryType type= (CategoryType) adapterView.getItemAtPosition(i);
                if(catId!=null) {
                   for (ServiceCategory sc : type.getCategories()) {
                     if (sc.getService_CategoryID().equals(catId)) {
                       String pos = String.valueOf(type.getCategories().indexOf(sc));
                       setEntries(binding.categorySpinner, type.getCategories(), pos);
                       catId=null;
                       break;
                       }
                    }
               }else
                    setEntries(binding.categorySpinner, type.getCategories(), null);

                if(type.getCategory_TypeID().equals("3")){
                   binding.uploadMediaPageBtn.setVisibility(View.VISIBLE);
                   binding.addressLabelTv.setText("مقر العمل");
               }
               else {
                    binding.addressLabelTv.setText("مقر السكن");
                    binding.uploadMediaPageBtn.setVisibility(View.GONE);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ServiceCategory category= (ServiceCategory) adapterView.getAdapter().getItem(i);
                if(subCatId!=null) {
                    for (ServiceSubcategory ss : category.getSubcategories()) {
                        if (ss.getService_SubCategoryID().equals(subCatId)) {
                            String pos = String.valueOf(category.getSubcategories().indexOf(ss));
                            setEntries(binding.spinnerServiceSubcategories, category.getSubcategories(), pos);
                            subCatId=null;
                            break;
                        }
                    }
                }
                else {
                    setEntries(binding.spinnerServiceSubcategories, category.getSubcategories(), null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.getAddressDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissions()){
                    getLocation();
                }
            }
        });

        binding.uploadMediaPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UploadMediaActivity.class));
            }
        });
              return v;//inflater.inflate(R.layout.fragment_work_data, container, false);
    }

  public void setEntries(Spinner s, ArrayList entries,String selection)
      {
        if(getContext()!=null){
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item, entries);
        s.setAdapter(spinnerArrayAdapter);

        if(selection!=null){
            if(s==binding.catTypeSpinner && s.getAdapter()!=null)
                binding.catTypeSpinner.setSelection(Integer.parseInt(selection) - 1);
            else if (s==binding.categorySpinner)
                binding.categorySpinner.setSelection(Integer.parseInt(selection));
            else if(s==binding.spinnerServiceSubcategories)
                binding.spinnerServiceSubcategories.setSelection(Integer.parseInt(selection));
            else
                binding.spinnerServiceType.setSelection(Integer.parseInt(selection));
           }
        }
    }

    DDListResponse listsDD;
    ArrayList<ServiceType> serviceTypes;

    @Override
    public void onDDListsFetchSuccess(DDListResponse response) {
        this.listsDD =response;
        setEntries(binding.catTypeSpinner,response.getTypes(),catTypeId);
        binding.progressBar.setVisibility(View.GONE);
        binding.saveBtn.setVisibility(View.VISIBLE);
      //  UpdateDataModel.get_user_datasave(data,this);
    }

    void listsAlreadyFetched(){
        setEntries(binding.catTypeSpinner, listsDD.getTypes(),catId);
        setEntries(binding.spinnerServiceType,serviceTypes,subCatId);
    }

    void loadDDlists(){
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.getDDLists(this);
        Model.getServiceTypes(this);
    }
    @Override
    public void onDDListsFetchFailure(String s) {
        if(getActivity()==null) return;
        Toast.makeText(getContext(),"خطأ اثناء تحميل البيانات!" + s,Toast.LENGTH_LONG).show();
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTypesFetchSuccess(ArrayList<ServiceType> response) {
        this.serviceTypes = response;
        if(serviceTypeId!=null) {
            for (ServiceType st : serviceTypes) {
               if (st.getService_TypeID().equals(serviceTypeId)) {
                   String pos = String.valueOf(serviceTypes.indexOf(st));
                   setEntries(binding.spinnerServiceType, serviceTypes, pos);
                   serviceTypeId=null;
                   break;
                  }
              }
        }
        else setEntries(binding.spinnerServiceType, serviceTypes, null);
    }

    @Override
    public void onTypesFetchFailure(String s) {

    }

    @Override
    public void onUpdateSuccess(String status) {
        saveLngLatToPreference();
        Toast.makeText(getContext(),status,Toast.LENGTH_SHORT).show();
        binding.progressBar.setVisibility(View.GONE);
        CommonUtils.transitFrag_BackStack(getContext(),new FamilyDataFragment());

    }

    @Override
    public void onUpdateFailure(String s) {
        Toast.makeText(getContext(),"خطأ اثناء حفظ البيانات! " + s,Toast.LENGTH_LONG).show();
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSaveData() throws NullPointerException{
        binding.progressBar.setVisibility(View.VISIBLE);
        if(validateForms()) {
            CategoryType cat_type = (CategoryType) binding.catTypeSpinner.getSelectedItem();

            String cat_type_id = cat_type.getCategory_TypeID();
            ServiceCategory serviceCategory = (ServiceCategory) binding.categorySpinner.getSelectedItem();
            String cat_id = serviceCategory.getService_CategoryID();
            ServiceSubcategory subcat = (ServiceSubcategory) binding.spinnerServiceSubcategories.getSelectedItem();
            String subcat_id = subcat.getService_SubCategoryID();
            ServiceType serviceType = (ServiceType) binding.spinnerServiceType.getSelectedItem();
            String service_type_id = serviceType.getService_TypeID();
            String service_name = binding.organizationNameEt.getText().toString();

            String country=binding.countryEt.getText().toString();
            String district=binding.districtEt.getText().toString();
            String city=binding.cityEt.getText().toString();
            String region=binding.regionEt.getText().toString();
            String area=binding.areaEt.getText().toString();
            String street =binding.streetEt.getText().toString();

            //Bundle bundle=CommonUtils.loadCredentials(getContext());


            String phone=bundle.getString(Constants.PHONE_KEY);
            String password=bundle.getString(Constants.PASSWORD_KEY);



            User user = new User(phone, password, cat_type_id, cat_id, subcat_id, service_type_id,
                    service_name,longitude, latitude,country,district,city,region,area,street);

            UpdateDataModel.update_user_data(user, this);
        }
        else{
            Toast.makeText(getContext(),"رجاء اكمال البيانات اولا.",Toast.LENGTH_SHORT).show();
            binding.saveBtn.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean validateForms(){
        String service_name=binding.organizationNameEt.getText().toString();
            if(service_name.startsWith(" ")){
                binding.organizationNameEt.setError("خطأ فى الادخال");
                return false;
            }
            else{
                return true;
            }

/*            else if(longitude==null||latitude==null){
            Toast.makeText(getContext(),"الرجاء الضغط على تحديد موقعك لاكمال التسجيل..",Toast.LENGTH_LONG).show();
            return false;
        }*/

    }

    @Override
    public void fillForms(User user) {
        String orgName=user.getService_Name();
        String country=user.getHome_Country();
        String district=user.getHome_District();
        String region=user.getHome_Region();
        String city=user.getHome_City();
        String area=user.getHome_Area();
        String street=user.getHome_Street();

        binding.organizationNameEt.setText(orgName);
        binding.countryEt.setText(country);
        binding.districtEt.setText(district);
        binding.cityEt.setText(city);
        binding.regionEt.setText(region);
        binding.areaEt.setText(area);
        binding.streetEt.setText(street);

        longitude=user.getHome_Longitude();
        latitude=user.getHome_Latitude();
    }



    public void getLocation() {

        binding.progressBar.setVisibility(View.VISIBLE);
        LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = String.valueOf(location.getLongitude());
                latitude = String.valueOf(location.getLatitude());

                try {
                    Geocoder geocoder = new Geocoder(getContext(), new Locale("ar"));
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    //      String []address= addresses.get(0).getAddressLine(0).split("،");

                    binding.countryEt.setText(addresses.get(0).getCountryName());
                    binding.districtEt.setText(addresses.get(0).getAdminArea());
                    binding.cityEt.setText(addresses.get(0).getSubAdminArea());
                    binding.regionEt.setText(addresses.get(0).getLocality());
                    binding.areaEt.setText(addresses.get(0).getFeatureName());
                    binding.streetEt.setText("");
                    binding.progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"تم الحصول على بيانات موقعك بنجاح.",Toast.LENGTH_LONG).show();

                }catch(Exception e){
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(getActivity(), "الرجاء تفعيل خاصية ال GPS اولا.", Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
            }
        };

        try {
                locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,locationListener,null);//.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
        }

        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x1;

    private boolean checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(),listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager=null;
    }

    @Override
    public void onGetDataSuccess(User user) {
        catTypeId=user.getCategory_TypeID();
        catId=user.getService_CategoryID();
        subCatId=user.getService_SubcategoryID();
        serviceTypeId=user.getService_TypeID();
        fillForms(user);
    }

    @Override
    public void onGetDataFailure(String status) {
        Toast.makeText(getContext(),status,Toast.LENGTH_SHORT).show();
    }

    public void saveLngLatToPreference(){
        if(getActivity()!=null) {
            SharedPreferences pref = getActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(Constants.USER_LAT, latitude);
                editor.putString(Constants.USER_LNG, longitude);
                editor.apply();
        }
    }
}






/* catTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               clearSpinners();
               if(i==0){
                   setEntries(categorySpinner,business_cats);
                   upload_media_page_btn.setVisibility(View.VISIBLE);

                   // addressLabel.setText("مقر العمل");
               }
               else if(i==1){

                   setEntries(categorySpinner,employee_cats);
                   upload_media_page_btn.setVisibility(View.INVISIBLE);
               }
               else   {
                   setEntries(categorySpinner,unEmployee_cats);
                   upload_media_page_btn.setVisibility(View.INVISIBLE);

               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getAdapter().getItem(i);
                if (s.matches("طالب"))

                {
                    setEntries(subcatSpinner,eductational_levels_subCasts);
                    setEntries(serviceTpeSpinner, student_spec_serviceType);
                    subCatLabel.setText("المرحلة التعليمية:");
                    serviceTypeLabel.setText("التخصص الدراسي:");
                    orgName.setHint("اسم المؤسسة التعليمية");
                    orgName.setEnabled(true);
                }

                else if (s.matches("باحث عن وظيفة") || s.matches( "ربة منزل") || s.matches( "معاش"))

                {
                    setEntries(subcatSpinner,certificates_subCats);
                    setEntries(serviceTpeSpinner, student_spec_serviceType);
                    subCatLabel.setText("المؤهل:");
                    serviceTypeLabel.setText("التخصص الدراسي:");
                    orgName.setHint("اسم المؤسسة");
                    orgName.setHintTextColor(getResources().getColor(R.color.gray));
                    orgName.setEnabled(false);
                }



           else {

                    subCatLabel.setText("نوع المؤسسة:");
                    serviceTypeLabel.setText("التخصص الوظيفي:");
                    orgName.setHint("اسم المؤسسة");
                    orgName.setEnabled(true);

                    if (s.matches("اصحاب الحرف")) {
                     setEntries(subcatSpinner, new String [0]);
                        setEntries(serviceTpeSpinner, skills_serviceType);
                    }
                    else if (s.matches("مؤسسات طبية"))
                    {
                    setEntries(subcatSpinner, medical_subCats);
                    setEntries(serviceTpeSpinner, medical_bus_serviceType);
                }

                    else if(s.matches("مكاتب محاسبة")){
                    setEntries(subcatSpinner,acc_office_subcCats);
                    setEntries(serviceTpeSpinner, financial_emps_serviceType);
                    }
                    else if(s.matches("المالية")){
                    setEntries(subcatSpinner,finance_subCats);
                    setEntries(serviceTpeSpinner, financial_emps_serviceType);
                }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        subcatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getAdapter().getItem(i);
                if( categorySpinner.getSelectedItem().toString().matches("طالب") && !(s.matches("جامعة") ))
                {
                   serviceTpeSpinner.setAdapter(null);
                }
                else if(s.matches("جامعة")|| s.matches("معهد"))
                {
                    setEntries(serviceTpeSpinner,student_spec_serviceType);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        upload_media_page_btn=v.findViewById(R.id.upload_media_page_btn);
        upload_media_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UploadMediaActivity.class));
            }
        });

        Button next_btn=v.findViewById(R.id.save_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.transitFrag_BackStack(getContext(),new FamilyDataFragment());
            }
        });*/