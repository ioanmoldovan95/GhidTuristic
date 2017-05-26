package com.example.john.ghidturistic.Adapters;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Helpers.TrackGPS;
import com.example.john.ghidturistic.Models.Objective;
import com.example.john.ghidturistic.Models.Position;

import java.util.ArrayList;


public class ObjectiveListAdapter extends ArrayAdapter<Objective> {

    private Context context;
    private ArrayList<Objective> objectives;

    public ObjectiveListAdapter(Context context, ArrayList<Objective> objectives){
        super(context,-1,objectives);
        this.context=context;
        this.objectives=objectives;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.objective_row_layout,parent,false);
        TextView nameTextView=(TextView)rowView.findViewById(R.id.name_text_view);
        TextView distanceTextView=(TextView)rowView.findViewById(R.id.distance_text_view);
        nameTextView.setText(objectives.get(position).getName());
        float distance=getDistance(objectives.get(position).getPosition());
        distanceTextView.setText(distance+"");

        return rowView;
    }

    private float getDistance(Position position){
        float results[]={0};
        double currLat,currLng;
        TrackGPS gps=new TrackGPS(context);
        if(gps.canGetLocation()){
            currLat=gps.getLatitude();
            currLng=gps.getLongitude();
            Location.distanceBetween(currLat, currLng, position.getLat(), position.getLng(),results );
        }else{
            gps.showSettingsAlert();
        }




        return results[0];
    }

    public void setObjectives(ArrayList<Objective> objectives){
        this.objectives=objectives;
    }
}
