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

public class RegistrationActivity extends AppCompatActivity {

    private EditText etLoginReg;
    private EditText etPassReg;
    private Button btnOk;

    private String login;
    private String password;
    private String token;

    private final ApiService api = RetroClient.getApiService();
    private final RegistrationRequest request = new RegistrationRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        etLoginReg = findViewById(R.id.etLoginReg);
        etPassReg = findViewById(R.id.etPassReg);
        btnOk = findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg();
            }
        });
    }

    private void reg() {

        login = etLoginReg.getText().toString();
        password = etPassReg.getText().toString();

        request.setUsername(login);
        request.setPassword(password);

        Call<RegistrationResponse> call = api.registerUser(request);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    token = response.body().getToken();
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        setResult(Constants.KEY_PROFILE, intent);
                        User.getInstance().setToken(token);
                        finish();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
            }
        });
    }
}

