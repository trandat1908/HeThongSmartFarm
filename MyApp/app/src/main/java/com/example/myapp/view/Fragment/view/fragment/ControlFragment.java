package com.example.myapp.view.Fragment.view.fragment;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapp.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ControlFragment extends Fragment {
    View view;


    private TextView nn1,dd1,dd2;
    private Switch simpleSwitch2,simpleSwitch3,simpleSwitch4,simpleSwitch5;
   //  private ImageView imageMis,imagePump,imageFan,imageLight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.control,container,false);

        nn1  = view.findViewById(R.id.den1);
        dd1 = view.findViewById(R.id.phunsuong1);
        dd2 = view.findViewById(R.id.bomnuoc1);

        simpleSwitch2 = view.findViewById(R.id.switch2);
        simpleSwitch3 = view.findViewById(R.id.switch3);
        simpleSwitch4 = view.findViewById(R.id.switch4);
        simpleSwitch5 = view.findViewById(R.id.switch5);


//        imageLight = view.findViewById(R.id.imageView6);
//        imageFan = view.findViewById(R.id.fan1);
//        imagePump = view.findViewById(R.id.pump1);
//        imageMis = view.findViewById(R.id.mist);


        display();
        simpleSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //TransitionDrawable drawable = (TransitionDrawable) imageLight.getDrawable();
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/fan");
                    reference1.setValue("1");
                  //            drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/fan");
                    reference1.setValue("0");
                   //          drawable.reverseTransition(200);
                }
            }
        });
        simpleSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                  //   TransitionDrawable drawable1 = (TransitionDrawable) imageFan.getDrawable();
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/lamp");
                    reference1.setValue("1");
                 //           drawable1.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/lamp");
                    reference1.setValue("0");
                    //        drawable1.reverseTransition(200);
                }
            }
        });
        simpleSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                 //   TransitionDrawable drawable = (TransitionDrawable) imagePump.getDrawable();
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/pump");

                    reference1.setValue("1");
                 //            drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/pump");
                    reference1.setValue("0");
                  //         drawable.reverseTransition(200);
                }
            }
        });
        simpleSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               //        TransitionDrawable drawable = (TransitionDrawable) imageMis.getDrawable();
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/mis");
                    reference1.setValue("1");
                //                drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = database.getReference("Control/mis");
                    reference1.setValue("0");
                 //              drawable.reverseTransition(200);
                }
            }
        });


        return view;
    }

    public void display(){
       DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Sensor");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                String nhietDo = dataSnapshot.child("temperature").getValue().toString();
                String doAmDat = dataSnapshot.child("soilmoisture").getValue().toString();
                String doAm = dataSnapshot.child("humidity").getValue().toString();

                nn1.setText("Temp:" + nhietDo + "°C");
                dd1.setText("Humi:" + doAm + "%");
                dd2.setText("Soil:" + doAmDat + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
