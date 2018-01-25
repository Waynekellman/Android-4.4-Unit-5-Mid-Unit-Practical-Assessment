package com.nyc.android_44_unit_5_mid_unit_practical_assessment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.model.Results;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.model.Users;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private Results result;
    private static final String INSTANCE_STATE_KEY = "result";
    private TextView bold_name,email,cell_number,address,date_of_birth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imageView = findViewById(R.id.user_image_detail);
        bold_name = findViewById(R.id.bold_name);
        email = findViewById(R.id.email);
        cell_number = findViewById(R.id.cell_number);
        address = findViewById(R.id.address);
        date_of_birth = findViewById(R.id.date_of_birth);
        if (savedInstanceState != null){
            String userInfo = savedInstanceState.getString(INSTANCE_STATE_KEY);
            result = new Gson().fromJson(userInfo,Results.class);
        } else {
            Intent intent = getIntent();
            String userInfo = intent.getStringExtra("userAsJson");
            result = new Gson().fromJson(userInfo, Results.class);
        }
        Picasso.with(this).load(result.getPicture().getLarge()).into(imageView);
        String name = result.getName().getTitle() + " " + result.getName().getFirst() + " " + result.getName().getLast();
        bold_name.setText(name);
        email.setText(result.getEmail());
        cell_number.setText(result.getCell());
        String location = result.getLocation().getStreet() + " " + result.getLocation().getCity() + " " + result.getLocation().getState();
        address.setText(location);
        date_of_birth.setText(result.getDob());

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String userString = new Gson().toJson(result);
        outState.putString(INSTANCE_STATE_KEY,userString);
    }
}
