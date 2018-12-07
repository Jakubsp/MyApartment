package com.example.jakub.myapartment;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    private EditText edtAddress;
    private EditText edtDatabase;
    private EditText edtUser;
    private EditText edtPassword;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getApplicationContext().getSharedPreferences("DBinitials", MODE_PRIVATE);

        edtAddress = findViewById(R.id.edtHostdb);
        edtDatabase = findViewById(R.id.edtNamedb);
        edtUser = findViewById(R.id.edtUserdb);
        edtPassword = findViewById(R.id.edtPassworddb);

        loadValues();
    }

    private void loadValues() {
        edtAddress.setText(sharedPreferences.getString("address", "127.0.0.1"));
        edtDatabase.setText(sharedPreferences.getString("database", "apartments"));
        edtUser.setText(sharedPreferences.getString("user", "admin"));
        edtPassword.setText(sharedPreferences.getString("password", "password"));
    }

    public void btnSaveOnClick(View v) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("address", edtAddress.getText().toString());
        editor.putString("database", edtDatabase.getText().toString());
        editor.putString("user", edtUser.getText().toString());
        editor.putString("password", edtPassword.getText().toString());

        editor.commit();
        onBackPressed();
    }

    public void btnTestConnOnClick(View v) {

    }
}
