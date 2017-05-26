package com.example.john.ghidturistic.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Helpers.Constants;
import com.example.john.ghidturistic.Models.Objective;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by John on 3/14/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    ArrayList<Objective> objectives;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.map_fragment_layout, container,false);
        SupportMapFragment mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        Bundle args=getArguments();
        objectives=(ArrayList<Objective>)args.getSerializable(Constants.Keys.OBJECTIVES_KEY);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

        int permissionCheck= ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            if(this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){

            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PermissionContacts.ACCESS_COARSE_LOCATION);
            }
        }else{
           updateUI();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionCheck= ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(requestCode==Constants.PermissionContacts.ACCESS_COARSE_LOCATION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                LatLng clujPos=new LatLng(46.7715879, 23.5936863);
                map.addMarker(new MarkerOptions().position(clujPos).title("Cluj-Napoca"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(clujPos,Constants.Zoom.MAX_ZOOM));
                map.setMyLocationEnabled(true);
                if (objectives != null) {
                    for (Objective objective:objectives) {
                        if(objective.getPosition()!=null){
                            LatLng pos=new LatLng(objective.getPosition().getLat(),objective.getPosition().getLng());
                            map.addMarker(new MarkerOptions().position(pos).title(objective.getName()));
                        }
                    }
                }
            }
        }
    }

    public void updateUI(){
        map.clear();
        if (objectives != null) {
            for (Objective objective:objectives) {
                if(objective.getPosition()!=null){
                    LatLng pos=new LatLng(objective.getPosition().getLat(),objective.getPosition().getLng());
                    map.addMarker(new MarkerOptions().position(pos).title(objective.getName()));
                }
            }
        }
        LatLng clujPos=new LatLng(46.7715879, 23.5936863);
        map.addMarker(new MarkerOptions().position(clujPos).title("Cluj-Napoca"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(clujPos,Constants.Zoom.MAX_ZOOM));
    }

    public void setObjectives(ArrayList<Objective> objectives){
        this.objectives=objectives;
        if(map!=null) {
            updateUI();
        }
    }


}
