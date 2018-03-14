package com.av.ibnammy.updateUserData;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.av.ibnammy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkDataFragment_New extends Fragment {

    String[] business_cats={"اصحاب الحرف","مؤسسات طبية","مكاتب محاسبة"}
    ,unEmployee_cats={"طالب","باحث عن وظيفة","معاش","ربة منزل"}
    ,employee_cats={"المالية","السياحة و الاثار"}

    ,skills_serviceType={"نجار","حداد","سروجي","سباك"}
    ,medical_subCats={"مستشفيات","عيادات","مراكز اشعة","معامل تحاليل"}
    ,acc_office_subcCats={"تكاليف","محاسبة قانونية","ميزانيات"}
    ,eductational_levels_subCasts={"ثانوية عامة","ثانوية فنية","معهد","جامعة","اعداية","ابتدئية"}
    ,certificates_subCats={"بكالوريوس","دبلوم","ماجيستير","دكتوراه","ثانوية","اعدادية"}
    ,finance_subCats={"بنوك خاصة","بنك مركزي","ضرائب","تامينات","وزارة"}
    ,student_spec_serviceType={"هندسة","طب اسنان","طب بيطري","علوم الحاسب"}
    ,financial_emps_serviceType={"محاسب اول","مدير مالي","مراقب مالي","صراف","اخرى"}
    ,medical_bus_serviceType={"جراحة عامة","نسا و توليد","اطفال"} ;

//***********


    Spinner catTypeSpinner,spinnerServiceCategories,spinnerServiceSubCategories,spinnerServiceType;
    TextView subCatLabel,serviceTypeLabel,addressLabel;
    EditText orgName;
    Button upload_media_page_btn;



    public WorkDataFragment_New() {
        // Required empty public constructor
    }
   // FragmentWorkDataBinding binding;
   // FragmentWorkDataBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.new_fragment_work_data,container,false);
       //View v=binding.getRoot();

        subCatLabel=v.findViewById(R.id.service_sub_cat_label_tv);
        serviceTypeLabel=v.findViewById(R.id.service_type_label_tv);
        orgName=v.findViewById(R.id.organization_name_et);
        addressLabel=v.findViewById(R.id.address_label_tv);
        catTypeSpinner=v.findViewById(R.id.cat_type_spinner);
        spinnerServiceCategories=v.findViewById(R.id.spinner_service_categories);
        spinnerServiceSubCategories=v.findViewById(R.id.spinner_service_subcategories);
        spinnerServiceType=v.findViewById(R.id.spinner_service_type);
        catTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               clearSpinners();
               if(i==0){
                   setEntries(spinnerServiceCategories,business_cats);
                   upload_media_page_btn.setVisibility(View.VISIBLE);

                   // addressLabel.setText("مقر العمل");
               }
               else if(i==1){

                   setEntries(spinnerServiceCategories,employee_cats);
                   upload_media_page_btn.setVisibility(View.INVISIBLE);
               }
               else   {
                   setEntries(spinnerServiceCategories,unEmployee_cats);
                   upload_media_page_btn.setVisibility(View.INVISIBLE);

               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        spinnerServiceCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getAdapter().getItem(i);
                if (s.matches("طالب"))

                {
                    setEntries(spinnerServiceSubCategories,eductational_levels_subCasts);
                    setEntries(spinnerServiceType, student_spec_serviceType);
                    subCatLabel.setText("المرحلة التعليمية:");
                    serviceTypeLabel.setText("التخصص الدراسي:");
                    orgName.setHint("اسم المؤسسة التعليمية");
                    orgName.setEnabled(true);
                }

                else if (s.matches("باحث عن وظيفة") || s.matches( "ربة منزل") || s.matches( "معاش"))

                {
                    setEntries(spinnerServiceSubCategories,certificates_subCats);
                    setEntries(spinnerServiceType, student_spec_serviceType);
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
                     setEntries(spinnerServiceSubCategories, new String [0]);
                        setEntries(spinnerServiceType, skills_serviceType);
                    }
                    else if (s.matches("مؤسسات طبية"))
                    {
                    setEntries(spinnerServiceSubCategories, medical_subCats);
                    setEntries(spinnerServiceType, medical_bus_serviceType);
                }

                    else if(s.matches("مكاتب محاسبة")){
                    setEntries(spinnerServiceSubCategories,acc_office_subcCats);
                    setEntries(spinnerServiceType, financial_emps_serviceType);
                    }
                    else if(s.matches("المالية")){
                    setEntries(spinnerServiceSubCategories,finance_subCats);
                    setEntries(spinnerServiceType, financial_emps_serviceType);
                }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerServiceSubCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getAdapter().getItem(i);
                if( spinnerServiceCategories.getSelectedItem().toString().matches("طالب") && !(s.matches("جامعة") ))
                {
                   spinnerServiceType.setAdapter(null);
                }
                else if(s.matches("جامعة")|| s.matches("معهد"))
                {
                    setEntries(spinnerServiceType,student_spec_serviceType);
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

        return v;//inflater.inflate(R.layout.fragment_work_data, container, false);
    }

    void setEntries(Spinner s, String[] entries)
    {
        ArrayAdapter spinnerArrayAdapter =
                new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                entries);
        s.setAdapter(spinnerArrayAdapter);
    }

    void clearSpinners(){
        spinnerServiceSubCategories.setAdapter(null);
        spinnerServiceCategories.setAdapter(null);
        spinnerServiceType.setAdapter(null);
    }

}
