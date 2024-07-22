package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewSensorsUsers extends AppCompatActivity {
    private static final String TAG = "ViewSensorsUsers";
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private ListView sensorsListView;
    private SensorAdapter adapter;
//    private ArrayAdapter<String> adapter;
    private List<Sensors> sensorList;
    private List<String> sensorIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sensors_users);

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference("users");

        sensorsListView = findViewById(R.id.sensorsListView);
        sensorList = new ArrayList<>();
        sensorIds = new ArrayList<>();
        adapter = new SensorAdapter();
        sensorsListView.setAdapter(adapter);

        fetchSensorsData(); //fetch and display the data

        sensorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = sensorIds.get(position);
                Intent intent = new Intent(ViewSensorsUsers.this, AddSensors.class);
                intent.putExtra("selectedSensorId",selectedId);
                startActivity(intent);
            }
        });

    }

    private void fetchSensorsData(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String uid = currentUser.getUid();
            DatabaseReference sensorRef = db.child(uid).child("sensors");

            sensorRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    sensorList.clear();
                    sensorIds.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String sensorType = snapshot.child("sensorType").getValue(String.class);
                        String numOfSensors = snapshot.child("numOfSensors").getValue(String.class);
                        String sensorCondition = snapshot.child("sensorCondition").getValue(String.class);
                        String id = snapshot.getKey();
                        sensorList.add(new Sensors(sensorType, numOfSensors, sensorCondition, id));
                        sensorIds.add(id);
                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read data", databaseError.toException());
                }
            });

        } else{
            Log.w(TAG, "User not authenticated");
        }
    }

    private class SensorAdapter extends ArrayAdapter<Sensors> {

        public SensorAdapter() {
            super(ViewSensorsUsers.this, R.layout.list_item_sensors, sensorList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_sensors, parent, false);
            }

            Sensors sensor = getItem(position);

            TextView sensorTypeTextView = convertView.findViewById(R.id.sensorTypeTextView);
            TextView numOfSensorsTextView = convertView.findViewById(R.id.numOfSensorsTextView);
            TextView sensorConditionTextView = convertView.findViewById(R.id.sensorConditionTextView);

            if (sensor != null) {
                sensorTypeTextView.setText(sensor.getSensorType());
                numOfSensorsTextView.setText(sensor.getNumOfSensors());
                sensorConditionTextView.setText(sensor.getSensorCondition());
            }

            return convertView;
        }
    }

    public class Sensors {
        private String sensorType;
        private String numOfSensors;
        private String sensorCondition;
        private String id;

        public Sensors(String sensorType, String numOfSensors, String sensorCondition, String id) {
            this.sensorType = sensorType;
            this.numOfSensors = numOfSensors;
            this.sensorCondition = sensorCondition;
            this.id = id;
        }

        public String getSensorType() {
            return sensorType;
        }

        public String getNumOfSensors() {
            return numOfSensors;
        }

        public String getSensorCondition() {
            return sensorCondition;
        }

        public String getId() {
            return id;
        }
    }
}
