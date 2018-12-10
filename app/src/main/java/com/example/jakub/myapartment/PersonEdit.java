package com.example.jakub.myapartment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Database.Person;
import Database.proxy.PersonTableProxy;

public class PersonEdit extends AppCompatActivity {

    private EditText etName;
    private EditText etCompanyName;
    private EditText etBirth;
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
        etBirth = findViewById(R.id.etPersonEditBirth);
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

        Person person = new Person();
        person.setName(name);
        person.setCompanyName(companyName);
        person.setRights(rights);
        person.setNfcUid(nfcUid);
        person.setTask(task);

        if (newPerson) {
            PersonTableProxy.Insert(person);
            onBackPressed();
        }
        else {

        }
    }

    public void onClickDelete(View v) {
        PersonTableProxy.Delete(id);
        onBackPressed();
    }
}
