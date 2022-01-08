package com.duakhan.ASANzindagi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duakhan.ASANzindagi.adapters.lawyer_adapter;
import com.duakhan.ASANzindagi.models.lawyer_model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class lawyer_appointment extends Fragment {

    RecyclerView recview;
    lawyer_adapter adapter;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public lawyer_appointment() {
        // Required empty public constructor
    }


    public static lawyer_appointment newInstance(String param1, String param2) {
        lawyer_appointment fragment = new lawyer_appointment();
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
        View view=   inflater.inflate(R.layout.fragment_lawyer_appointment, container, false);

        recview=(RecyclerView)view.findViewById(R.id.rec_view);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseRecyclerOptions<lawyer_model> option=
                new FirebaseRecyclerOptions.Builder<lawyer_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lawyer_Appointment").child(uid),lawyer_model.class)
                        .build();
        adapter=new lawyer_adapter(option);
        recview.setAdapter(adapter);



        return  view;

    }
    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}