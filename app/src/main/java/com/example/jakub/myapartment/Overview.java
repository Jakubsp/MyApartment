package com.example.jakub.myapartment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import Database.proxy.OverviewTableProxy;
import Database.proxy.PersonTableProxy;

public class Overview extends AppCompatActivity {

    private TextView txvApartmentId;
    private TextView txvTenantName;
    private TextView txvDate;
    private TextView txvGas;
    private TextView txvElectricity;
    private TextView txvAvgTemp;
    private TextView txvWater;

    private int apartmentId = -1;
    private Database.Person tenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            apartmentId = extras.getInt("apartment_id");
            tenant = PersonTableProxy.SelectById(extras.getInt("person_id"));
        }

        txvApartmentId = findViewById(R.id.txvOverviewApartmentName);
        txvTenantName = findViewById(R.id.txvOverviewTenant);
        txvDate = findViewById(R.id.txvOverviewDate);
        txvGas = findViewById(R.id.txvOverviewGas);
        txvElectricity = findViewById(R.id.txvOverviewElectricity);
        txvAvgTemp = findViewById(R.id.txvOverviewAvgTemp);
        txvWater = findViewById(R.id.txvOverviewWater);

        txvApartmentId.setText("Byt č." + apartmentId);
        txvTenantName.setText(tenant.getName());
        loadOverview(OverviewTableProxy.SelectNewestByApartmentId(apartmentId));
    }

    private void loadOverview(Database.Overview overview) {
        if (overview == null)
            return;

        txvDate.setText(overview.getDate().toString());
        txvGas.setText(String.valueOf(overview.getGas()));
        txvElectricity.setText(String.valueOf(overview.getElectricity()));
        txvAvgTemp.setText(String.valueOf(overview.getAvgTemp()));
        txvWater.setText(String.valueOf(overview.getWater()));
    }
}
