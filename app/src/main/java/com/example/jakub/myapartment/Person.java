package com.example.jakub.myapartment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;

import Database.DBConnect;
import Database.oracle.PersonTable;
import Database.proxy.PersonTableProxy;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Person.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Person#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Person extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mCallback;
    private Context context;

    ListView lvPerson;

    public Person() {

    }

    private void connectToDb() {
        SharedPreferences shaPref = getContext().getSharedPreferences("DBinitials", Context.MODE_PRIVATE);
        DBConnect.getInstance().newConnection(shaPref.getString("address", "127.0.0.1"),
                shaPref.getString("database", "apartments"),
                shaPref.getString("user", "admin"),
                shaPref.getString("password", "password"));
        boolean connected = false;
        if (DBConnect.getInstance().getConnection() != null)
            connected = true;

        Toast.makeText(getContext().getApplicationContext(), connected?"Spojení navázáno":"Nebylo možné se připojit", Toast.LENGTH_SHORT).show();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Person.
     */
    // TODO: Rename and change types and number of parameters
    public static Person newInstance(String param1, String param2) {
        Person fragment = new Person();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvPerson = getView().findViewById(R.id.lvPerson);
        PersonAdapter personAdapter = new PersonAdapter(getContext(), PersonTableProxy.Select());
        lvPerson.setAdapter(personAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mCallback = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        context = mCallback.getContext();
        if (DBConnect.getInstance().getConnection() == null) {
            connectToDb();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onPersonFragmentInteraction(Uri uri);

        Context getContext();
    }
}
