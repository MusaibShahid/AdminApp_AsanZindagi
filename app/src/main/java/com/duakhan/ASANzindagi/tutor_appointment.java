package com.duakhan.ASANzindagi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.duakhan.ASANzindagi.adapters.tutor_adapter;
import com.duakhan.ASANzindagi.models.tutor_model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class tutor_appointment extends Fragment {


    RecyclerView recview;
    tutor_adapter adapter;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tutor_appointment() {
        // Required empty public constructor
    }

    public static tutor_appointment newInstance(String param1, String param2) {
        tutor_appointment fragment = new tutor_appointment();
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
        View view=   inflater.inflate(R.layout.fragment_tutor, container, false);

        recview=(RecyclerView)view.findViewById(R.id.tutorrec_view);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseRecyclerOptions<tutor_model> option=
                new FirebaseRecyclerOptions.Builder<tutor_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tutor_Appointment").child(uid),tutor_model.class)
                        .build();
        adapter=new tutor_adapter(option);
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