package com.av.ibnammy.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mina on 1/3/2018.
 */

public class DashBoardFragment extends Fragment implements GetCallback.onGetStatistic  {

    private ProgressWheel pwtwo,pwthree,pwfour;

    private TextView total_num_of_cousins,num_of_subAccounts_tv,num_of_accounts_tv,accounts_for_country_tv,followers_for_country_tv;
    private Spinner countries;
    private ProgressBar progressBar;
  //  List<String> spinner_data=new ArrayList<>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
                View v=inflater.inflate(R.layout.dash_board_activity_layout, container, false);
                progressBar=v.findViewById(R.id.progress_bar);
                total_num_of_cousins=v.findViewById(R.id.total_num_of_cousins);
                num_of_subAccounts_tv=v.findViewById(R.id.num_of_subAccounts_tv);
                num_of_accounts_tv=v.findViewById(R.id.num_of_accounts_tv);
                countries=v.findViewById(R.id.spinner_countries_dashboard);
                accounts_for_country_tv=v.findViewById(R.id.accounts_for_country_tv);
                followers_for_country_tv=v.findViewById(R.id.followers_for_country_tv);
                pwtwo = (ProgressWheel)v. findViewById(R.id.progressBarTwo);

           countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   CountryListItem item= (CountryListItem) adapterView.getSelectedItem();
                   accounts_for_country_tv.setText(item.getNumberAccounts());
                   followers_for_country_tv.setText(item.getNumberFollowers());
               }

               @Override
               public void onNothingSelected(AdapterView<?> adapterView) {

               }
           });

            getStatistics(this);
        return v;
    }

    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
    public void getStatistics(final GetCallback.onGetStatistic listener){
        showProgressBar();
        Call<String> call=apiInterface.getStatisticsApi();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null) {
                    Gson gson = new Gson();
                    DashboardData data = gson.fromJson(response.body(), DashboardData.class);
                    listener.onGetStatisticSuccess(data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    if(t.getMessage()!=null)
                    listener.onGetStatisticFailure(t.getMessage());
            }
        });

    }

    public void setEntries(Spinner s, ArrayList entries)
    {
        if(getContext()!=null) {
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
             R.layout.spinner_item, entries);
            s.setAdapter(spinnerArrayAdapter);
        }
    }

    @Override
    public void onGetStatisticSuccess(DashboardData data) {
        setEntries(countries,data.getCountriesData());
        pwtwo.incrementProgress(70);
        total_num_of_cousins.setText(data.getTotalAccountAndFollowers());
        num_of_accounts_tv.setText(data.getAccountsSignUp());
        num_of_subAccounts_tv.setText(data.getFollowers());

        hideProgressBar();
    }

    @Override
    public void onGetStatisticFailure(String status) {
        if(getContext()==null) return;
        Toast.makeText(getContext(),status,Toast.LENGTH_SHORT).show();

        hideProgressBar();
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}
