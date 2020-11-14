package com.example.myapp.view.Fragment.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Objects;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class DisplayFragment extends Fragment {
    View view;
    private ImageView imageView;
    private ImageView btnfan;
    private ImageView btnpump;
    private ImageView btnlamp;
    private ImageView btnmis;


    private ImageView btnfan11;
    private ImageView btnpump11;
    private ImageView btnmist1;
    private ImageView btnlamp11;


    private TextView light;
    private PieView pieView_hum;
    private PieView pieView_soil;
    private CustomGauge customGauge;
    private TextView textView;
    public DisplayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_display, container, false);
        customGauge = view.findViewById(R.id.gauge2);
        textView = view.findViewById(R.id.text1);
        pieView_hum = view.findViewById(R.id.pieView_hum);
        pieView_soil = view.findViewById(R.id.pieView_soil);
        imageView = view.findViewById(R.id.image);
        light = view.findViewById(R.id.anhsang);
        btnfan = view.findViewById(R.id.fan_status);
        btnlamp = view.findViewById(R.id.lamp_status);
        btnpump = view.findViewById(R.id.pump_status);
        btnmis = view.findViewById(R.id.mis_status);

        btnfan11 = view.findViewById(R.id.btnfan11);
        btnpump11 = view.findViewById(R.id.btnpump1);
        btnmist1 = view.findViewById(R.id.btnmist1);
        btnlamp11 = view.findViewById(R.id.btnlamp1);


        pieView_hum.setPercentageBackgroundColor(getResources().getColor(R.color.bluehigh));
        pieView_soil.setPercentageBackgroundColor(getResources().getColor(R.color.browhigh));


        Firebase.setAndroidContext(Objects.requireNonNull(getContext()));

        final Firebase mRef1 = new Firebase("https://projecttkhn.firebaseio.com/Sensor/temperature");
        Firebase mRef2 = new Firebase("https://projecttkhn.firebaseio.com/Sensor/light");
        Firebase mRef3 = new Firebase("https://projecttkhn.firebaseio.com/Sensor/soilmoisture");
        Firebase mRef4 = new Firebase("https://projecttkhn.firebaseio.com/Sensor/humidity");


        Firebase btnfan1 = new Firebase("https://projecttkhn.firebaseio.com/Control/fan");
        Firebase btnlamp1 = new Firebase("https://projecttkhn.firebaseio.com/Control/lamp");
        Firebase btnpump1 = new Firebase("https://projecttkhn.firebaseio.com/Control/pump");
        Firebase btnmis1 = new Firebase("https://projecttkhn.firebaseio.com/Control/mis");


        pieView_hum.setPercentageBackgroundColor(getResources().getColor(R.color.bluehigh));
//        pieView_hum.setMainBackgroundColor(getResources().getColor(R.color.white));


        pieView_soil.setPercentageBackgroundColor(getResources().getColor(R.color.browhigh));
//        pieView_soil.setMainBackgroundColor(getResources().getColor(R.color.white));




        btnfan1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnfan11.setImageResource(R.drawable.fan);
                    btnfan.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnfan.setImageResource(R.drawable.btntat);
                    btnfan11.setImageResource(R.drawable.fanoff);

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        btnlamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnlamp11.setImageResource(R.drawable.lighon2);
                    btnlamp.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnlamp.setImageResource(R.drawable.btntat);
                    btnlamp11.setImageResource(R.drawable.light_off1);

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        btnpump1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnpump11.setImageResource(R.drawable.pump2);
                    btnpump.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnpump.setImageResource(R.drawable.btntat);
                    btnpump11.setImageResource(R.drawable.pumpoff);

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        btnmis1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnmist1.setImageResource(R.drawable.miston);
                    btnmis.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnmis.setImageResource(R.drawable.btntat);
                    btnmist1.setImageResource(R.drawable.mistoff);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                float i = Float.parseFloat(value);
                pieView_hum.setPercentage(i);
                pieView_hum.setPieInnerPadding(30);
                pieView_hum.setPercentageTextSize(20);
                PieAngleAnimation animation = new PieAngleAnimation(pieView_hum);
                animation.setDuration(2000); //This is the duration of the animation in millis
                pieView_hum.startAnimation(animation);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                light.setText(value);
                if (light.getText().toString().equals("Trời sáng")) {
                    imageView.setImageResource(R.drawable.sun);
                } else if (light.getText().toString().equals("Trời tối")) {
                    imageView.setImageResource(R.drawable.moon);
                }


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView.setText(value+"°C");
                int i=Integer.parseInt(value.replaceAll("[\\D]", ""));
                customGauge.setValue(i*10);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                float i = Float.parseFloat(value);
                pieView_soil.setPieInnerPadding(30);
                pieView_soil.setPercentageTextSize(20);
                pieView_soil.setPercentage(i);
                PieAngleAnimation animation = new PieAngleAnimation(pieView_soil);
                animation.setDuration(2000); //This is the duration of the animation in millis
                pieView_soil.startAnimation(animation);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return view;
    }
}
