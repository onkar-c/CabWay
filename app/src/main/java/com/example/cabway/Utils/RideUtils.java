package com.example.cabway.Utils;

import com.example.core.responseModel.RideResponseModel;

import java.util.Collections;
import java.util.List;

public class RideUtils {

    public static List<RideResponseModel> sortRidesDescending(List<RideResponseModel> rideList){
        if(rideList != null)
            Collections.sort(rideList,
                    (o1, o2) -> o2.getRideId().compareTo(o1.getRideId()));
        return rideList;
    }

    public static List<RideResponseModel> sortRidesOnTime(List<RideResponseModel> rideList){
        if(rideList != null)
            Collections.sort(rideList,
                    (o1, o2) -> o1.getConvertedPickUpDate().compareTo(o2.getConvertedPickUpDate()));
        return rideList;
    }
}
