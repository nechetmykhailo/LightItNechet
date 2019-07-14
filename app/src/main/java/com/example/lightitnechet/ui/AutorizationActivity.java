package com.example.lightitnechet.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lightitnechet.Constants;
import com.example.lightitnechet.R;
import com.example.lightitnechet.model.RegistrationRequest;
import com.example.lightitnechet.model.RegistrationResponse;
import com.example.lightitnechet.retrofit.ApiService;
import com.example.lightitnechet.retrofit.RetroClient;
import com.example.lightitnechet.singleton.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorizationActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etPassword;
    private Button btnAuth;
    private Button btnReg;

    private String login;
    private String password;
    private String token;

    private final ApiService api = RetroClient.getApiService();
    private final RegistrationRequest request = new RegistrationRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPass);
        btnAuth = findViewById(R.id.btnAuth);
        btnReg = findViewById(R.id.btnReg);

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutorizationActivity.this, RegistrationActivity.class);
                startActivityForResult(intent, Constants.KEY_REG);
            }
        });
    }

    private void auth() {
        login = etLogin.getText().toString();
        password = etPassword.getText().toString();

        request.setUsername(login);
        request.setPassword(password);

        Call<RegistrationResponse> callAuth = api.autorizationUser(request);
        callAuth.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if(response.isSuccessful()) {
                    token = response.body().getToken();
                    if(token != null){
                        User.getInstance().setToken(token);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Пользователь с таким логином или паролем не найден", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "NotGood Registration ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {

            }
        });
    }
}

