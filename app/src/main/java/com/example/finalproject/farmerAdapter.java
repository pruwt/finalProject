package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class farmerAdapter extends ArrayAdapter<Farmer> {

    public farmerAdapter(@NonNull Context context, @NonNull List<Farmer> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_farmer, parent, false);
        }

        Farmer farmer = getItem(position);

        TextView farmerNameTextView = convertView.findViewById(R.id.farmerNameTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
        TextView locationTextView = convertView.findViewById(R.id.locationTextView);

        farmerNameTextView.setText(farmer.getName());
        emailTextView.setText(farmer.getEmail());
        locationTextView.setText(farmer.getLocation());

        return convertView;
    }
}
