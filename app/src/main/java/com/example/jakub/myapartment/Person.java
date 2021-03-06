package com.example.jakub.myapartment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import Database.DBConnect;
import Database.proxy.PersonTableProxy;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Person.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Person#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Person extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mCallback;
    private Context context;
    FloatingActionButton floatingActionButton;

    ListView lvPerson;
    SwipeRefreshLayout swipeRefreshLayout;

    PersonAdapter personAdapter;

    public Person() {
        // Required empty public constructor
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
        personAdapter = new PersonAdapter(getContext(), PersonTableProxy.SelectAllUsers());
        RefreshList();

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Database.Person person = (Database.Person)personAdapter.getItem(position);

                Intent intent = new Intent(getContext(), PersonEdit.class);
                intent.putExtra("id", person.getId());
                intent.putExtra("name", person.getName());
                intent.putExtra("companyName", person.getCompanyName());
                intent.putExtra("email", person.getEmail());
                intent.putExtra("nfcUid", person.getNfcUid());
                intent.putExtra("rights", person.getRights());
                intent.putExtra("dateOfBirth", person.getDateOfBirth());
                intent.putExtra("superiorID", person.getSuperiorId());
                intent.putExtra("task", person.getTask());
                startActivityForResult(intent, 1);
            }
        });

        swipeRefreshLayout = getView().findViewById(R.id.srlPerson);
        swipeRefreshLayout.setOnRefreshListener(this);

        floatingActionButton = getView().findViewById(R.id.btnAddPerson);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PersonEdit.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void RefreshList() {
        if (lvPerson.getAdapter() == null)
            lvPerson.setAdapter(personAdapter);
        else {
            personAdapter = new PersonAdapter(getContext(), PersonTableProxy.SelectAllUsers());
            lvPerson.setAdapter(personAdapter);
            personAdapter.notifyDataSetChanged();
            lvPerson.invalidate();
            lvPerson.invalidateViews();
            lvPerson.refreshDrawableState();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            RefreshList();
        }
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
        RefreshList();
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
        Context getContext();
    }
}
