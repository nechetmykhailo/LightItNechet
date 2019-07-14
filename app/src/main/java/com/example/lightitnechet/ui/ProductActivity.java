package com.example.lightitnechet.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightitnechet.Constants;
import com.example.lightitnechet.R;
import com.example.lightitnechet.SQLiteConnector;
import com.example.lightitnechet.SpacecItemDecoration;
import com.example.lightitnechet.adapter.RecyclerAdapterFinalProduct;
import com.example.lightitnechet.model.FinalProduct;
import com.example.lightitnechet.model.ReviewRequest;
import com.example.lightitnechet.model.ReviewResponse;
import com.example.lightitnechet.retrofit.ApiService;
import com.example.lightitnechet.retrofit.RetroClient;
import com.example.lightitnechet.singleton.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private TextView tvProductName;
    private TextView tvProductTitle;
    private ImageView imageProduct;
    private RatingBar ratingBar;
    private EditText etOtziv;
    private Button btnPost;
    private RecyclerView recyclerViewProduct;
    private List<FinalProduct> posts;
    private RecyclerAdapterFinalProduct adapter;

    private int raitingBarInt; // рейтинг отзыва
    private Integer intID; // id продукта
    private String review; // id продукта

    private SQLiteConnector connector;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tvProductName = findViewById(R.id.tvProductName);
        tvProductTitle = findViewById(R.id.tvProductTitle);
        imageProduct = findViewById(R.id.imageProduct);
        ratingBar = findViewById(R.id.ratingBar);
        etOtziv = findViewById(R.id.etOtziv);
        btnPost = findViewById(R.id.btnPost);
        recyclerViewProduct = findViewById(R.id.recyclerViewProduct);

        posts = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        final String title = extras.getString("tvName");
        final String text = extras.getString("tvTitle");
        final String image = extras.getString("image");
        intID = extras.getInt("tvID");

        tvProductName.setText(text);
        tvProductTitle.setText(title);

        Picasso.get()
                .load(image)
                .into(imageProduct);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
                raitingBarInt = (int) v;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProduct.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterFinalProduct(this, posts);
        recyclerViewProduct.addItemDecoration(new SpacecItemDecoration(getApplicationContext()));
        recyclerViewProduct.setAdapter(adapter);

        productAdapter(); // отображение списка отзывов
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retroReview();
                recyclerViewProduct.getAdapter().notifyDataSetChanged();
                productAdapter();
            }
        });
    }

    private void retroReview() {
        if (User.getInstance().getToken() != null) {
            ReviewRequest reviewRequest = new ReviewRequest();
            review = etOtziv.getText().toString();
            reviewRequest.setRate(raitingBarInt);
            reviewRequest.setText(review);

            ApiService api = RetroClient.getApiService();

            Call<ReviewResponse> reviewCall = api.reviewRequest(reviewRequest, intID, "Token "+User.getInstance().getToken());
            reviewCall.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    if (review.equals("")) {
                        Toast.makeText(getApplicationContext(), "Поле должно быть заполненно", Toast.LENGTH_SHORT).show();
                    } else if (raitingBarInt == 0) {
                        Toast.makeText(getApplicationContext(), "Поставте рейтинг", Toast.LENGTH_SHORT).show();
                    }else {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Коментарий успешно добавлен", Toast.LENGTH_SHORT).show();
                            etOtziv.setText("");
                            ratingBar.setRating(0);
                            recyclerViewProduct.getAdapter().notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplication(), "Ошибка с добавлением комента", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                }
            });
        } else {
            Toast.makeText(getApplication(), "Что бы оставить отзыв, Вы должны авторизироваться", Toast.LENGTH_SHORT).show();
        }
    }

    private void productAdapter() {
        ApiService api = RetroClient.getApiService();
        Call<ArrayList<FinalProduct>> call = api.getMyProduct(intID);
        call.enqueue(new Callback<ArrayList<FinalProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<FinalProduct>> call, Response<ArrayList<FinalProduct>> response) {
                posts.addAll(response.body());
                recyclerViewProduct.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<FinalProduct>> call, Throwable t) {
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
            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), AutorizationActivity.class);
            startActivityForResult(intent, Constants.KEY_PROFILE);
        }
        return true;
    }
}
