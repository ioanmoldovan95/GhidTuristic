package com.example.john.ghidturistic.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Adapters.ObjectiveListAdapter;
import com.example.john.ghidturistic.Helpers.Constants;
import com.example.john.ghidturistic.Models.Objective;

import java.util.ArrayList;

/**
 * Created by John on 3/15/2017.
 */

public class ListFragment extends Fragment {

    ArrayList<Objective> objectives;
    ListView listView;
    ObjectiveListAdapter objectiveListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        Bundle args = getArguments();
        objectives = (ArrayList<Objective>) args.getSerializable(Constants.Keys.OBJECTIVES_KEY);
        listView = (ListView) view.findViewById(R.id.list_view);
        if (objectives != null) {
            objectiveListAdapter = new ObjectiveListAdapter(this.getContext(), objectives);

            listView.setAdapter(objectiveListAdapter);
        }


        return view;
    }

    public void setObjectives(ArrayList<Objective> objectives) {
        this.objectives = objectives;
        objectiveListAdapter.setObjectives(objectives);
        objectiveListAdapter.notifyDataSetChanged();

    }
}
