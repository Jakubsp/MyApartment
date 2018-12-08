package com.example.jakub.myapartment;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBConnect;

public class Settings extends AppCompatActivity {

    private EditText edtAddress;
    private EditText edtDatabase;
    private EditText edtUser;
    private EditText edtPassword;
    SharedPreferences sharedPreferences;

    private String address;
    private String database;
    private String user;
    private String password;

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
        showValues();
    }

    private void loadValues() {
        address = sharedPreferences.getString("address", "127.0.0.1");
        database = sharedPreferences.getString("database", "apartments");
        user = sharedPreferences.getString("user", "admin");
        password = sharedPreferences.getString("password", "password");
    }

    private void getValues() {
        address = edtAddress.getText().toString();
        database = edtDatabase.getText().toString();
        user = edtUser.getText().toString();
        password = edtPassword.getText().toString();
    }

    private void showValues() {
        edtAddress.setText(address);
        edtDatabase.setText(database);
        edtUser.setText(user);
        edtPassword.setText(password);
    }

    public void btnSaveOnClick(View v) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        getValues();

        editor.putString("address", address);
        editor.putString("database", database);
        editor.putString("user", user);
        editor.putString("password", password);

        DBConnect.getInstance().newConnection(address, database, user, password);

        editor.commit();
        onBackPressed();
    }

    public void btnTestConnOnClick(View v) {
        getValues();

        DBConnect.getInstance().newConnection(address, database, user, password);

        boolean connected = false;
        if (DBConnect.getInstance().getConnection() != null)
            connected = true;

        Toast.makeText(getApplicationContext(), connected?"Spojení navázáno":"Nebylo možné se připojit", Toast.LENGTH_SHORT).show();
    }
}
