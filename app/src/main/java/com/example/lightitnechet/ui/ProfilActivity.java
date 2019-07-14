package com.example.lightitnechet.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lightitnechet.R;
import com.example.lightitnechet.dialog.EditProfileDialog;
import com.example.lightitnechet.singleton.User;
import com.squareup.picasso.Picasso;

public class ProfilActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 1;
    private Button btnAddPhoto;
    private Button btnEditor;
    private Button btnExit;
    private ImageView ivPhoto;
    private TextView tvCounter;
    private TextView tvName;
    private TextView tvLastName;
    private Uri selectedImage;

    private EditProfileDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnEditor = findViewById(R.id.btnEditor);
        ivPhoto = findViewById(R.id.ivPhoto);
        tvCounter = findViewById(R.id.tvCounter);
        tvLastName = findViewById(R.id.tvLastName);
        tvName = findViewById(R.id.tvName);
        btnExit = findViewById(R.id.btnExit);

        tvName.setText(User.getInstance().getName());
        tvLastName.setText(User.getInstance().getName());

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
        btnEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.getInstance().setToken(null);
                User.getInstance().setImagePath(null);
                User.getInstance().setLastName(null);
                User.getInstance().setName(null);
                finish();
            }
        });
    }

    private void myDialog() {
        dialog = new EditProfileDialog(); // диалоговое окно поверх основного layout
        dialog.setDismissListener(new EditProfileDialog.DismissListener() {
            @Override
            public void onDismiss() {

                tvName.setText(User.getInstance().getName());
                tvLastName.setText(User.getInstance().getLastName());

                Toast.makeText(getApplicationContext(), "Данные успешно изменены!", Toast.LENGTH_SHORT);
            }
        });
        dialog.setCancelable(true);
        dialog.show(getSupportFragmentManager(), "fragment");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            selectedImage = data.getData();

            SharedPreferences activityPreferences = getPreferences(Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = activityPreferences.edit();
            editor.putString("ImagePath", selectedImage.toString());
            editor.apply();

            User.getInstance().setImagePath(activityPreferences.getString("ImagePath", ""));

            Picasso.get()
                    .load(User.getInstance().getImagePath())
                    .placeholder(R.drawable.progress)
                    .into(ivPhoto);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (User.getInstance().getImagePath() != null) {
            Picasso.get()
                    .load(User.getInstance().getImagePath())
                    .placeholder(R.drawable.progress)
                    .into(ivPhoto);
        }
    }
}
