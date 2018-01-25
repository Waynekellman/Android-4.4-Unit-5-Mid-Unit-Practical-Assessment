package com.nyc.android_44_unit_5_mid_unit_practical_assessment;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.conroller.UserAdapter;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.model.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private Users users;
    private SharedPreferences sharedPreferences;
    private boolean hitRefresh;
    private static final String INSTANCE_STATE_KEY = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        if (savedInstanceState != null){
            String userString = savedInstanceState.getString(INSTANCE_STATE_KEY);
            users = new Gson().fromJson(userString, Users.class);
            Log.d(TAG, "onCreate: " + users.getResults()[0].getName().getFirst());
            adapter = new UserAdapter(users.getResults());
            recyclerView.setAdapter(adapter);
        } else {
            retrofitCall();
        }
    }

    private void retrofitCall() {
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String userString = new Gson().toJson(users);
        outState.putString(INSTANCE_STATE_KEY,userString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
