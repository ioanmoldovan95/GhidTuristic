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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by John on 3/14/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.map_fragment_layout, container,false);
        SupportMapFragment mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

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
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionCheck= ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(requestCode==Constants.PermissionContacts.ACCESS_COARSE_LOCATION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                LatLng clujPos=new LatLng(46.7715879, 23.5936863);
                map.addMarker(new MarkerOptions().position(clujPos).title("Cluj-Napoca"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(clujPos,15));
                map.setMyLocationEnabled(true);
            }
        }
    }
}
