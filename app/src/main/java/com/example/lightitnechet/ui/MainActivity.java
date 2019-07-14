package com.example.lightitnechet.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightitnechet.Constants;
import com.example.lightitnechet.R;
import com.example.lightitnechet.adapter.RecyclerViewAdapter;
import com.example.lightitnechet.model.Product;
import com.example.lightitnechet.retrofit.ApiService;
import com.example.lightitnechet.retrofit.RetroClient;
import com.example.lightitnechet.singleton.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Product> posts;
    public static final int KEY_REG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        posts = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, posts);
        recyclerView.setAdapter(adapter);

        listProduct();
    }

    private void listProduct() {

        ApiService api = RetroClient.getApiService();
        Call<ArrayList<Product>> call = api.getMyJSON();

        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()) {
                    posts.addAll(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Неудалось загрузить список товаров", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (User.getInstance().getToken() != null) {
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            startActivityForResult(intent, Constants.KEY_REG);
        } else {
            Intent intent = new Intent(MainActivity.this, AutorizationActivity.class);
            startActivityForResult(intent, Constants.KEY_PROFILE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

