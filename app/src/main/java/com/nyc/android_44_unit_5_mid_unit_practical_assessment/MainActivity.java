package com.nyc.android_44_unit_5_mid_unit_practical_assessment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nyc.android_44_unit_5_mid_unit_practical_assessment.conroller.UserAdapter;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.model.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserNetwork service = retrofit.create(UserNetwork.class);
        Call<Users> getUsers = service.getUserList();
        getUsers.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                users = response.body();
                adapter = new UserAdapter(users.getResults());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }
}
