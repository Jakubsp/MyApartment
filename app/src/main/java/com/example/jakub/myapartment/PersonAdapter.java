package com.example.jakub.myapartment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jakub.myapartment.Person;
import com.example.jakub.myapartment.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PersonAdapter extends BaseAdapter {

    Context context;
    Collection<Database.Person> personCollection;
    LayoutInflater layoutInflater;

    public PersonAdapter(Context context, Collection<Database.Person> personCollection) {
        this.context = context;
        this.personCollection = personCollection;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return personCollection.size();
    }

    @Override
    public Object getItem(int position) {
        return ((List<Database.Person>)personCollection).get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listview_entry_person, null);
        TextView personName = convertView.findViewById(R.id.txvPersonName);
        personName.setText(((List<Database.Person>)personCollection).get(position).getName());
        TextView personEmail = convertView.findViewById(R.id.txvPersonEmail);
        personEmail.setText(((List<Database.Person>)personCollection).get(position).getEmail());
        return convertView;
    }
}
