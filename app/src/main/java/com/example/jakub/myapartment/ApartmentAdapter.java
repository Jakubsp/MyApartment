package com.example.jakub.myapartment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import Database.Apartment;
import Database.proxy.PersonTableProxy;

public class ApartmentAdapter extends BaseAdapter {

    Context context;
    Collection<Apartment> apartmentCollection;
    LayoutInflater layoutInflater;

    public ApartmentAdapter(Context context, Collection<Apartment> apartmentCollection) {
        this.context = context;
        this.apartmentCollection = apartmentCollection;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return apartmentCollection.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listview_entry_apartment, null);
        TextView apartmentFloor = convertView.findViewById(R.id.txvApartmentFloor);
        TextView apartmentRooms = convertView.findViewById(R.id.txvApartmentRooms);
        TextView apartmentRenter = convertView.findViewById(R.id.txvApartmentRenter);
        TextView apartmentUsableArea = convertView.findViewById(R.id.txvApartmentUsableArea);

        int idRenter = ((List<Apartment>)apartmentCollection).get(position).getTenantId();
        apartmentRenter.setText(PersonTableProxy.SelectById(idRenter).getName());
        apartmentFloor.setText(String.valueOf(((List<Apartment>)apartmentCollection).get(position).getFloor()));
        apartmentRooms.setText(String.valueOf(((List<Apartment>)apartmentCollection).get(position).getRooms()));
        apartmentUsableArea.setText(String.valueOf(((List<Apartment>)apartmentCollection).get(position).getUsableArea()));

        return convertView;
    }
}
