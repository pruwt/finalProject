package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SensorViewAdmin extends AppCompatActivity {

    private static final String TAG = "SensorViewAdmin";
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private ListView sensorsListViewAdmin;
    private SensorsAdapter adapter;
    private List<Sensors> sensorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_view_admin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Realtime Database
        db = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI elements
        sensorsListViewAdmin = findViewById(R.id.sensorsListViewAdmin);
        sensorsList = new ArrayList<>();
        adapter = new SensorsAdapter(this, sensorsList);
        sensorsListViewAdmin.setAdapter(adapter);

        // Fetch and display data
        fetchSensorsDataDB();

        // Set item click listener to pass the ID back
        sensorsListViewAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sensors selectedSensor = adapter.getItem(position);
                Intent intent = new Intent(SensorViewAdmin.this, AdminHome.class);
                intent.putExtra("selectedSensorId", selectedSensor.getId());
                startActivity(intent);
            }
        });
    }

    private void fetchSensorsDataDB() {
        DatabaseReference sensorsRef = db.child("users");

        sensorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sensorsList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot sensorsSnapshot = userSnapshot.child("sensors");
                    for (DataSnapshot snapshot : sensorsSnapshot.getChildren()) {
                        Log.d(TAG, "Sensor Data Snapshot: " + snapshot.toString());

                        String id = snapshot.getKey();
                        String type = snapshot.child("sensorType").getValue(String.class);
                        String numOfSensors = snapshot.child("numOfSensors").getValue(String.class);
                        String condition = snapshot.child("sensorCondition").getValue(String.class);
                        Sensors sensor = new Sensors(type, numOfSensors, condition, id);
                        sensorsList.add(sensor);
                    }
                }
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read data", databaseError.toException());
            }
        });
    }

    public class SensorsAdapter extends ArrayAdapter<Sensors> {

        private Context context;
        private List<Sensors> sensorsList;

        public SensorsAdapter(@NonNull Context context, @NonNull List<Sensors> objects) {
            super(context, 0, objects);
            this.context = context;
            this.sensorsList = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if (listItem == null)
                listItem = LayoutInflater.from(context).inflate(R.layout.sensor_list_item_admin, parent, false);

            Sensors currentSensor = sensorsList.get(position);

            TextView sensorType = listItem.findViewById(R.id.sensorType);
            sensorType.setText(currentSensor.getSensorType());

            TextView numOfSensors = listItem.findViewById(R.id.numOfSensors);
            numOfSensors.setText(currentSensor.getNumOfSensors());

            TextView sensorCondition = listItem.findViewById(R.id.sensorCondition);
            sensorCondition.setText(currentSensor.getSensorCondition());

            return listItem;
        }
    }

    public static class Sensors {
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
