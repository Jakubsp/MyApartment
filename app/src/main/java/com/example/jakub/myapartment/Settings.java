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

import Database.ConfigurationManager;
import Database.DBConnect;
import Database.DatabaseType;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edtAddress;
    private EditText edtDatabase;
    private EditText edtUser;
    private EditText edtPassword;
    private Spinner spinner;
    private Button btnTestConn;

    SharedPreferences DBinitials;

    private String address;
    private String database;
    private String user;
    private String password;
    private DatabaseType databaseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        DBinitials = getApplicationContext().getSharedPreferences("DBinitials", MODE_PRIVATE);

        edtAddress = findViewById(R.id.edtHostdb);
        edtDatabase = findViewById(R.id.edtNamedb);
        edtUser = findViewById(R.id.edtUserdb);
        edtPassword = findViewById(R.id.edtPassworddb);
        btnTestConn = findViewById(R.id.btnTestConn);

        loadValues();
        showValues();

        spinner = findViewById(R.id.spinnerSettings);
        spinner.setOnItemSelectedListener(this);

        // Spinner drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("MySQL");
        categories.add("JSON");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attach data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getPosition(databaseType.toString()));
    }

    private void loadValues() {
        address = DBinitials.getString("address", "127.0.0.1");
        database = DBinitials.getString("database", "apartments");
        user = DBinitials.getString("user", "admin");
        password = DBinitials.getString("password", "password");
        databaseType = DatabaseType.valueOf(DBinitials.getString("databasetype", "MySQL"));
        ConfigurationManager.getInstance().updateParameters(address, database, user, password);
        ConfigurationManager.getInstance().updateDatabaseType(databaseType);
    }

    private void getValues() {
        address = edtAddress.getText().toString();
        database = edtDatabase.getText().toString();
        user = edtUser.getText().toString();
        password = edtPassword.getText().toString();
        ConfigurationManager.getInstance().updateParameters(address, database, user, password);
        ConfigurationManager.getInstance().updateDatabaseType(databaseType);
    }

    private void showValues() {
        edtAddress.setText(address);
        edtDatabase.setText(database);
        edtUser.setText(user);
        edtPassword.setText(password);
    }

    public void btnSaveOnClick(View v) {
        SharedPreferences.Editor editor = DBinitials.edit();

        getValues();

        editor.putString("address", address);
        editor.putString("database", database);
        editor.putString("user", user);
        editor.putString("password", password);
        editor.putString("databasetype", databaseType.toString());

        ConfigurationManager.getInstance().updateParameters(address, database, user, password);
        ConfigurationManager.getInstance().updateDatabaseType(databaseType);

        if (databaseType == DatabaseType.MySQL)
            DBConnect.getInstance().newConnection();

        editor.commit();
        onBackPressed();
    }

    public void btnTestConnOnClick(View v) {
        getValues();

        DBConnect.getInstance().newConnection();

        boolean connected = false;
        if (DBConnect.getInstance().getConnection() != null)
            connected = true;

        Toast.makeText(getApplicationContext(), connected?"Spojení navázáno":"Nebylo možné se připojit", Toast.LENGTH_SHORT).show();

        loadValues();
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
