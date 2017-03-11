package Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.carparkappv1.MyCustomMap;
import com.example.android.carparkappv1.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import Controllers.MapController;

public class MapFragmentHolder extends Fragment {

    private final String TAG = "MapFragmentHolderClass";

    TextView test;
    MapFragment mapFragment;
    MyCustomMap myMap;
    public static MapController mapController;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.map_fragment, viewGroup, false);
        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        myMap = new MyCustomMap(getActivity(), mapFragment);
        mapController = new MapController(myMap);
        test = (TextView) view.findViewById(R.id.testing);



        return view;
    }


    //Carry out all functions of the map!
    public void start(){
        if(mapController.initMap()){
            LatLng ll = mapController.searchLocation(location);
        }

    }


}
