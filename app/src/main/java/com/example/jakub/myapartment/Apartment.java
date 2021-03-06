package com.example.jakub.myapartment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import Database.DBConnect;
import Database.proxy.ApartmentTableProxy;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Apartment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Apartment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Apartment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mCallback;
    private Context context;

    ListView lvApartment;
    SwipeRefreshLayout swipeRefreshLayout;

    ApartmentAdapter apartmentAdapter = null;

    public Apartment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Database.Apartment.
     */
    // TODO: Rename and change types and number of parameters
    public static Apartment newInstance(String param1, String param2) {
        Apartment fragment = new Apartment();
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
        return inflater.inflate(R.layout.fragment_apartment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvApartment = getView().findViewById(R.id.lvApartment);
        apartmentAdapter = new ApartmentAdapter(getContext(), ApartmentTableProxy.SelectAll());
        RefreshList();

        lvApartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Database.Apartment apartment = (Database.Apartment)apartmentAdapter.getItem(position);

                Intent intent = new Intent(getContext(), Overview.class);
                intent.putExtra("apartment_id", apartment.getId());
                intent.putExtra("person_id", apartment.getTenantId());
                startActivity(intent);
            }
        });

        swipeRefreshLayout = getView().findViewById(R.id.srlApartment);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void RefreshList() {
        if (lvApartment.getAdapter() == null)
            lvApartment.setAdapter(apartmentAdapter);
        else {
            apartmentAdapter = new ApartmentAdapter(getContext(), ApartmentTableProxy.SelectAll());
            lvApartment.setAdapter(apartmentAdapter);
            apartmentAdapter.notifyDataSetChanged();
            lvApartment.invalidate();
            lvApartment.invalidateViews();
            lvApartment.refreshDrawableState();
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
