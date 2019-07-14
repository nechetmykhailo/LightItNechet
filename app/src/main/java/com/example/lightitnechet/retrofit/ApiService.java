package com.example.lightitnechet.retrofit;

import com.example.lightitnechet.model.FinalProduct;
import com.example.lightitnechet.model.Product;
import com.example.lightitnechet.model.RegistrationRequest;
import com.example.lightitnechet.model.RegistrationResponse;
import com.example.lightitnechet.model.ReviewRequest;
import com.example.lightitnechet.model.ReviewResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/products")
    Call<ArrayList<Product>> getMyJSON(); // список товаров

    @POST("/api/register/")
    Call<RegistrationResponse> registerUser(@Body RegistrationRequest registrationBody); // регистрация

    @POST("/api/login/")
    Call<RegistrationResponse> autorizationUser(@Body RegistrationRequest registrationBody); // авторизация

    @GET("api/reviews/{id}")
    Call<ArrayList<FinalProduct>> getMyProduct(@Path("id") int id); // возвращает список с коментами продукт по id

    @POST("/api/reviews/{product_id}")
    Call<ReviewResponse> reviewRequest(@Body ReviewRequest review, @Path("product_id") int product_id, @Header("Authorization") String token); // отзыв о продукте
}
