package com.av.ibnammy.updateUserData.familyData;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentFamilyDataBinding;
import com.av.ibnammy.updateUserData.UpdateDataActivity;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple {@link Fragment} subclass.
 */


public class FamilyDataFragment extends Fragment implements FamilyCallBack.AddFamily,FamilyCallBack.GetFamily {

    FragmentFamilyDataBinding binding;
    ArrayList<Follower> familyList;
    FamilyListAdapter adapter;
    String phone;
    String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateDataActivity activity=new UpdateDataActivity();
        activity.addStep(2,getContext());
        familyList=new ArrayList<>();
        Bundle bundle= CommonUtils.loadCredentials(getContext());
        phone=bundle.getString(Constants.PHONE_KEY);
        password=bundle.getString(Constants.PASSWORD_KEY);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_family_data,container,false);
        View v=binding.getRoot(); //inflater.inflate(R.layout.fragment_family_data, container, false);
        getFamily();
        setEntries(binding.relationSpinner, Constants.relation_map().keySet());
        binding.addFollowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String desKey= (String) binding.relationSpinner.getSelectedItem(); // get description value for selected key.
               // String des= Constants.relation_map().get(desKey);
                String name=binding.followerNameEt.getText().toString();
                if(name.equals("")){
                    binding.followerNameEt.setError( "برجاء ادخال اسم التابع اولا.");
                }
                else if(name.startsWith(" ") || CommonUtils.stringContainsNumber(name)){
                    binding.followerNameEt.setError("خطأ فى الادخال.");
                }
                else {
                    name=name.replaceAll("\n","");
                    Follower follower = new Follower(name, desKey);
                    if (desKey.equals("زوجة") || desKey.equals("ام") || desKey.equals("ابنة") || desKey.equals("اخت")) {
                        follower.setPhoto(R.mipmap.female);
                    } else
                        follower.setPhoto(R.mipmap.male);

                    adapter.add(follower);
                    binding.followerNameEt.setText("");
                    hideKeyboard();
                }
            }
        });

       // adapter=new FamilyListAdapter(familyList,getContext());
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFamily(adapter.getNewFamilyList());
            }
        });
        return v;
    }
    //private

    public void setup_recycler(ArrayList<Follower> list){
        if(getActivity()==null) return;
        adapter=new FamilyListAdapter(list,getActivity());
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycleView.setAdapter(adapter);

    }

    void setEntries(Spinner s, Collection entries)
    {
        if(getContext()!=null){
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, Constants.relation_map().keySet().toArray());
            s.setAdapter(spinnerArrayAdapter);
        }
    }



    public void getFamily(){
        showProgress();
        FamilyModel.getFamily(phone,password,this);
    }

    @Override
    public void onGetFamilySuccess(ArrayList<Follower> followers) {
        for(Follower fol:followers){
             String desKey=fol.getDescription();
            if(desKey.equals("زوجة")||desKey.equals("ام")||desKey.equals("ابنة")||desKey.equals("اخت")){
                fol.setPhoto(R.mipmap.female);
                continue;
            }
                fol.setPhoto(R.mipmap.male);
        }
        setup_recycler(followers);
        hideProgress();
    }

    @Override
    public void onGetFamilyFailure(String status) {
        hideProgress();
        Toast.makeText(getContext(), status,Toast.LENGTH_SHORT).show();
    }

    public void addFamily(ArrayList<Follower>list){
        showProgress();
        FamilyModel.addFamily(phone,password,list,this);
    }

    @Override
    public void onAddFamilySuccess(String status) {
        hideProgress();
        Toast.makeText(getContext(), status,Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onAddFamilyFailure(String status) {
        hideProgress();
        Toast.makeText(getContext(), status,Toast.LENGTH_SHORT).show();
    }

    void showProgress(){
        binding.progressBar.setVisibility(View.VISIBLE);
    }
    void hideProgress(){
        binding.progressBar.setVisibility(View.GONE);
    }

    void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
  //  public void
}
