package com.example.cabway.ui.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.cabway.R;
import com.example.core.CommonModels.CityModel;

import java.util.ArrayList;
import java.util.List;

public class CitySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context mContext;
    private List<CityModel> cityList;


    public CitySpinnerAdapter(Context context, List<CityModel> cities) {
        this.mContext=context;
        this.cityList=new ArrayList<>();
        this.cityList=cities;
    }


    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                view = inflater.inflate(R.layout.city_item, parent, false);
            }

            CityModel city = (CityModel) getItem(position);
            TextView name = view.findViewById(R.id.tv_city_name);
            name.setText(city.getName());
            if(position==0)
                name.setTextColor(ContextCompat.getColor(mContext,R.color.hint));
            else
                name.setTextColor(ContextCompat.getColor(mContext,android.R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.city_item, null);
        }
        CityModel city = (CityModel) getItem(position);
        final TextView name =view.findViewById(R.id.tv_city_name);
        name.setText(city.getName());
        name.setPadding(20,20,0,0);
        if(position==0)
            name.setTextColor(ContextCompat.getColor(mContext,R.color.hint));
        else
            name.setTextColor(ContextCompat.getColor(mContext,android.R.color.black));
        return view;
    }
}
