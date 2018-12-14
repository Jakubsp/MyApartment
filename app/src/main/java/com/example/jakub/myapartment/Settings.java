package com.example.jakub.myapartment;

import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.DBConnect;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    enum DatabaseType {
        MySQL, XML
    }

    private EditText edtAddress;
    private EditText edtDatabase;
    private EditText edtUser;
    private EditText edtPassword;
    private Spinner spinner;
    private Button btnTestConn;

    SharedPreferences sharedPreferences;

    private String address;
    private String database;
    private String user;
    private String password;
    private DatabaseType databaseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getApplicationContext().getSharedPreferences("DBinitials", MODE_PRIVATE);
        loadValues();

        edtAddress = findViewById(R.id.edtHostdb);
        edtDatabase = findViewById(R.id.edtNamedb);
        edtUser = findViewById(R.id.edtUserdb);
        edtPassword = findViewById(R.id.edtPassworddb);
        btnTestConn = findViewById(R.id.btnTestConn);

        spinner = findViewById(R.id.spinnerSettings);
        spinner.setOnItemSelectedListener(this);

        // Spinner drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("MySQL");
        categories.add("XML");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attach data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getPosition(databaseType.toString()));
    }

    private void loadValues() {
        address = sharedPreferences.getString("address", "127.0.0.1");
        database = sharedPreferences.getString("database", "apartments");
        user = sharedPreferences.getString("user", "admin");
        password = sharedPreferences.getString("password", "password");
        databaseType = DatabaseType.valueOf(sharedPreferences.getString("databasetype", "MySQL"));
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
        editor.putString("databasetype", databaseType.toString());

        if (databaseType == DatabaseType.MySQL)
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        databaseType = DatabaseType.valueOf(spinner.getItemAtPosition(position).toString());
        switch(databaseType) {
            case MySQL:
            {
                edtAddress.setEnabled(true);
                edtDatabase.setEnabled(true);
                edtUser.setEnabled(true);
                edtPassword.setEnabled(true);
                btnTestConn.setEnabled(true);
            }
            break;

            case XML:
            {
                edtAddress.setEnabled(false);
                edtDatabase.setEnabled(false);
                edtUser.setEnabled(false);
                edtPassword.setEnabled(false);
                btnTestConn.setEnabled(false);
            }
            break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
