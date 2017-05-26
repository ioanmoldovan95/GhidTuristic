package com.example.john.ghidturistic.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Fragments.PositionActivity;
import com.example.john.ghidturistic.Helpers.Constants;
import com.example.john.ghidturistic.Helpers.FirebaseService;
import com.example.john.ghidturistic.Models.Objective;
import com.example.john.ghidturistic.Models.Position;

public class AddObjectiveActivity extends AppCompatActivity {

    EditText nameEditText, descriptionEditText;
    Button positionButton, addButton;
    ImageButton backButton;
    Position position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_objective);
        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddObjectiveActivity.this.finish();
            }
        });
        positionButton = (Button) findViewById(R.id.input_position_button);
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPositionFromDialog();
            }
        });

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);

        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, description;
                name = nameEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                Objective objective = new Objective(name, description, position);
                if (MainActivity.getObjectives().contains(objective)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.objective_exists), Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseService.getInstance().addNewObjective(objective);
                    Intent intent = new Intent(AddObjectiveActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });
    }

    private void getPositionFromMap() {
        Intent intent = new Intent(AddObjectiveActivity.this, PositionActivity.class);
        startActivityForResult(intent, Constants.RequestCodes.POSITION_CODE_MAP);
    }

    private void getPositionFromDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.input_position_dialog_layout);
        dialog.setTitle("Input position");
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final EditText latEditText, lngEditText;
        latEditText = (EditText) dialog.findViewById(R.id.lat_edit_text);
        lngEditText = (EditText) dialog.findViewById(R.id.long_edit_text);
        Button okButton, cancelButton;
        okButton = (Button) dialog.findViewById(R.id.ok_button);
        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double lat = Double.parseDouble(latEditText.getText().toString());
                    double lng = Double.parseDouble(lngEditText.getText().toString());
                    position = new Position(lat, lng);
                    dialog.dismiss();
                } catch (NumberFormatException ex) {
                    Toast.makeText(AddObjectiveActivity.this, "Data is not valid", Toast.LENGTH_SHORT).show();
                    position = null;
                }
            }
        });

        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RequestCodes.POSITION_CODE_MAP) {
                double lat, lng;
                lat = data.getDoubleExtra(Constants.Keys.LAT, 0.0f);
                lng = data.getDoubleExtra(Constants.Keys.LONG, 0.0f);
                position = new Position(lat, lng);
            }
        }
    }
}

