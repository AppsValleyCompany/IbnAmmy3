package com.av.ibnammy.homePage.map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.av.ibnammy.R;

import java.util.ArrayList;

/**
 * Created by Maiada on 6/11/2018.
 */

public class ServiceTypeListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    private ArrayList<ServiceCategory> categoryArrayList;
    private Context context;

    private  MyViewHolder holder = null;

    public ServiceTypeListAdapter( Context context ,ArrayList<ServiceCategory> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;

        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_request_type, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
            Log.d("row", "Creating row");
        } else {
            holder = (MyViewHolder) convertView.getTag();
            Log.d("row", "Recycling use");
        }

        ServiceCategory serviceCategory =  categoryArrayList.get(position);

        holder.tvServiceTypeName.setText(serviceCategory.getName());

       if(position==1)
            holder.lineSeparator.setVisibility(View.INVISIBLE);
        else
            holder.lineSeparator.setVisibility(View.INVISIBLE);

        return convertView;
    }


    public class MyViewHolder {
        TextView tvServiceTypeName;
        View lineSeparator;
        public  MyViewHolder(View v){
            tvServiceTypeName = v.findViewById(R.id.tv_service_type);
            lineSeparator     = v.findViewById(R.id.line_separator);
            v.setTag(this);
        }
    }
}
