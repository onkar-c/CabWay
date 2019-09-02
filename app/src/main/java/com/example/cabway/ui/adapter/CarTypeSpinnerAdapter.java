package com.example.cabway.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import com.example.cabway.R;
import com.example.core.CommonModels.VehicleTypeModel;

import java.util.ArrayList;
import java.util.List;

public class CarTypeSpinnerAdapter extends ArrayAdapter implements AdapterView.OnItemSelectedListener {

    private Context mContext;
    private List<VehicleTypeModel> dataList;
    private TypeSelectedCallback itemSelectedCallback;

    public CarTypeSpinnerAdapter(Activity context, AppCompatSpinner spinner, List<VehicleTypeModel> dataList) {
        super(context, R.layout.city_item);
        this.mContext = context;
        this.dataList = new ArrayList<>();
        this.dataList = dataList;
        //TODO:Remove
        dataList.addAll(getVehicleTypeList());
        this.itemSelectedCallback = (TypeSelectedCallback) context;
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public VehicleTypeModel getItem(int position) {
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
                view = inflater.inflate(R.layout.car_type_item, parent, false);
            }
            TextView name = view.findViewById(R.id.tv_type);
            ImageView image = view.findViewById(R.id.iv_car_type);

            Object obj = getItem(position);
            if(obj!=null) {
                VehicleTypeModel vehicleTypeModel = (VehicleTypeModel) obj;
                name.setText(vehicleTypeModel.getType());
                //ImageUtils.setImageFromUrl(mContext,vehicleTypeModel.getCarImageUrl(),image);
                image.setImageDrawable(mContext.getDrawable(R.drawable.ic_add_image));
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
            view = View.inflate(mContext, R.layout.car_type_item, null);
        }
        TextView name = view.findViewById(R.id.tv_type);
        ImageView image = view.findViewById(R.id.iv_car_type);

        Object obj = getItem(position);
        if(obj!=null) {
            VehicleTypeModel vehicleTypeModel = (VehicleTypeModel) obj;
            name.setText(vehicleTypeModel.getType());
            //ImageUtils.setImageFromUrl(mContext,vehicleTypeModel.getCarImageUrl(),image);
            image.setImageDrawable(mContext.getDrawable(R.drawable.ic_add_image));
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
        itemSelectedCallback.sendTypeOnSelection(dataList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public interface TypeSelectedCallback {
        void sendTypeOnSelection(VehicleTypeModel data);
    }

    private static List<VehicleTypeModel> getVehicleTypeList(){
        List<VehicleTypeModel> vehicleTypeModels =new ArrayList<>();
        for(int i=0;i<4;i++){
            VehicleTypeModel vehicleTypeModel = new VehicleTypeModel();
            vehicleTypeModel.setType("Sedan");
            vehicleTypeModel.setCarImageUrl("R.drawable.ic_add_image");
            vehicleTypeModels.add(vehicleTypeModel);
        }

        return vehicleTypeModels;
    }

}

