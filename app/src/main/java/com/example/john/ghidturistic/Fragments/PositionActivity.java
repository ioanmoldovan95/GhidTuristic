package com.example.john.ghidturistic.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Helpers.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class PositionActivity extends AppCompatActivity implements OnMapReadyCallback {

    LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        Button getPositionButton = (Button) findViewById(R.id.get_position_button);
        getPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != null) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.Keys.LAT, position.latitude);
                    intent.putExtra(Constants.Keys.LONG, position.longitude);
                    PositionActivity.this.setResult(RESULT_OK, intent);
                    PositionActivity.this.finish();
                } else {
                    Toast.makeText(PositionActivity.this, "Please select position on map!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                position = latLng;
            }
        });
    }


}
