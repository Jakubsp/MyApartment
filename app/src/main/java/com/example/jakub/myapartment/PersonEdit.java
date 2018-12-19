package com.example.jakub.myapartment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Database.Person;
import Database.proxy.PersonTableProxy;

public class PersonEdit extends AppCompatActivity {

    private EditText etName;
    private EditText etCompanyName;
    private EditText etEmail;
    private EditText etRights;
    private EditText etNfcUid;
    private EditText etTask;

    private int id;

    private Button btnDelete;

    private boolean newPerson = true;
    private int personId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit);

        etName = findViewById(R.id.etPersonEditName);
        etCompanyName = findViewById(R.id.etPersonEditCompanyName);
        etEmail = findViewById(R.id.etPersonEmail);
        etRights = findViewById(R.id.etPersonEditRights);
        etNfcUid = findViewById(R.id.etPersonEditNfcUid);
        etTask = findViewById(R.id.etPersonEditTask);

        btnDelete = findViewById(R.id.btnPersonDelete);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newPerson = false;
            id = extras.getInt("id");
            etName.setText(extras.getString("name"));
            etCompanyName.setText(extras.getString("companyName"));
            etEmail.setText(extras.getString("email"));
            etRights.setText(extras.getString("rights"));
            etNfcUid.setText(extras.getString("nfcUid"));
            etTask.setText(extras.getString("task"));
        }

        if (newPerson) btnDelete.setVisibility(View.GONE);
    }


    public void onClickSave(View v) {
        String name = etName.getText().toString();
        String companyName = etCompanyName.getText().toString();

        String rights = etRights.getText().toString();
        String nfcUid = etNfcUid.getText().toString();
        String task = etTask.getText().toString();
        String email = etEmail.getText().toString();

        Person person = new Person();
        person.setName(name);
        person.setCompanyName(companyName);
        person.setRights(rights);
        person.setNfcUid(nfcUid);
        person.setTask(task);
        person.setEmail(email);

        if (newPerson) {
            PersonTableProxy.Insert(person);
            onBackPressed();
        }
        else {
            person.setId(id);
            PersonTableProxy.Update(person);
            onBackPressed();
        }
    }

    public void onClickDelete(View v) {
        PersonTableProxy.Delete(id);
        onBackPressed();
    }
}
