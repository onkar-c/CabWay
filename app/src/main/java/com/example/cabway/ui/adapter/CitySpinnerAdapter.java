package com.example.cabway.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import com.example.cabway.R;
import com.example.cabway.ui.activities.ProfileActivity;
import com.example.cabway.ui.activities.RegistrationActivity;
import com.example.cabway.ui.activities.UploadDocumentActivity;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;

import java.util.ArrayList;
import java.util.List;

public class CitySpinnerAdapter<T> extends ArrayAdapter<T> implements AdapterView.OnItemSelectedListener {

    private Context mContext;
    private List<T> dataList;
    private ItemSelectedCallback itemSelectedCallback;

    public CitySpinnerAdapter(Context context, AppCompatSpinner spinner, List<T> dataList) {
        super(context, R.layout.city_item);
        this.mContext = context;
        this.dataList = new ArrayList<>();
        this.dataList = dataList;
        if(context instanceof RegistrationActivity)
            this.itemSelectedCallback = (RegistrationActivity) context;
        if(context instanceof ProfileActivity)
            this.itemSelectedCallback = (ProfileActivity) context;
        if(context instanceof UploadDocumentActivity)
            this.itemSelectedCallback = (UploadDocumentActivity) context;
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                view = inflater.inflate(R.layout.city_item, parent, false);
            }
            TextView name = view.findViewById(R.id.tv_city_name);
            StateModel stateModel;
            CityModel cityModel;
            Object obj = getItem(position);
            if (obj instanceof StateModel) {
                stateModel = (StateModel) obj;
                name.setText(stateModel.getName());
            } else if (obj instanceof CityModel) {
                cityModel = (CityModel) obj;
                name.setText(cityModel.getName());
            }
            if (position == 0)
                name.setTextColor(ContextCompat.getColor(mContext, R.color.hint));
            else
                name.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.city_item, null);
        }
        final TextView name = view.findViewById(R.id.tv_city_name);
        StateModel stateModel;
        CityModel cityModel;
        Object obj = getItem(position);
        if (obj instanceof StateModel) {
            stateModel = (StateModel) obj;
            name.setText(stateModel.getName());
        } else if (obj instanceof CityModel) {
            cityModel = (CityModel) obj;
            name.setText(cityModel.getName());
        }
        name.setPadding(20, 20, 0, 20);
        if (position == 0)
            name.setTextColor(ContextCompat.getColor(mContext, R.color.hint));
        else
            name.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        itemSelectedCallback.sendDataOnSelection(dataList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public interface ItemSelectedCallback {
        <T> void sendDataOnSelection(T data);
    }

}
