package com.av.ibnammy.homePage.favourite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.MenuListAdapter;

/**
 * Created by Maiada on 1/2/2018.
 */

public class FavouriteFragment extends Fragment {

    RecyclerView recyclerViewFavourite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerViewFavourite = rooView.findViewById(R.id.listFavourite);
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFavourite.setHasFixedSize(true);
        recyclerViewFavourite.setAdapter(new RecyclerView.Adapter() {
            @Override
            public MenuListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_favourite,parent,false);
                return new MenuListAdapter.MyViewHolder(view);

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });






        return rooView;
    }
}
