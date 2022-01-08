package com.duakhan.ASANzindagi.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duakhan.ASANzindagi.FcmNotificationsSender;
import com.duakhan.ASANzindagi.R;
import com.duakhan.ASANzindagi.models.tutor_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class tutor_adapter extends FirebaseRecyclerAdapter<tutor_model,tutor_adapter.myviewholder> {

    public tutor_adapter(@NonNull FirebaseRecyclerOptions<tutor_model> options) {
        super(options);
    }

    String Title="";
    String Massage=" ";
    String Usertoken="";
    @Override
    protected void onBindViewHolder(@NonNull tutor_adapter.myviewholder holder, int position, @NonNull  tutor_model tutor_model) {
        holder.Username.setText(tutor_model.getUsername());
        holder.Time.setText(tutor_model.getTime());
        holder.tutorstatus.setText(tutor_model.getStatus());
        Usertoken=tutor_model.getToken();
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

 //dlt
holder.delt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder =new AlertDialog.Builder(holder.crd1.getContext())
        .setTitle("Delete user")
        .setMessage("Are you sure you want to delete?")
                .setIcon(R.drawable.delet)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                FirebaseDatabase.getInstance().getReference().child("Tutor_Appointment").child(uid).child(getRef(position).getKey()).removeValue();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
builder.show();
    }
});
//dlt*/
        holder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map=new HashMap<>();
                map.put("status","Approve");

                Title="Approved";
                Massage="Your appointment is Approved";
               // Toast.makeText(v.getContext(), Usertoken +" Token "+ Title +" Title "+ Massage +"Massage ", Toast.LENGTH_SHORT).show();

                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(Usertoken,
                        Title,
                        Massage,
                        v.getContext().getApplicationContext(),
                        v.getContext());
                notificationsSender.SendNotifications();
                FirebaseDatabase.getInstance().getReference().child("Tutor_Appointment").child(uid).child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(v.getContext(), "Your Appointment is approverd", Toast.LENGTH_SHORT).show();

                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


            }

        });
        holder.canceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object>map=new HashMap<>();
                map.put("status","Cancel");
                Title="Denied";
                Massage="Your appointment is Cancel";
               // Toast.makeText(v.getContext(), Usertoken +" Token "+ Title +" Title "+ Massage +"Massage ", Toast.LENGTH_SHORT).show();

                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(Usertoken,
                        Title,
                        Massage,
                        v.getContext().getApplicationContext(),
                        v.getContext());
                notificationsSender.SendNotifications();



                FirebaseDatabase.getInstance().getReference().child("Tutor_Appointment").child(uid).child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(v.getContext(), "Your Appointment is Canceled", Toast.LENGTH_SHORT).show();



                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


            }




        });

    }

    @NonNull
    @Override
    public tutor_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_rows,parent,false);
        return new tutor_adapter.myviewholder(view);

    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView Username,Time,tutorstatus;
        Button approved;
        Button canceled;
        ImageView delt;
        CardView crd1;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Username =itemView.findViewById(R.id.tutornam);
            Time =itemView.findViewById(R.id.tutortimdoc);
            tutorstatus=itemView.findViewById(R.id.tutorstatus);
            approved=itemView.findViewById(R.id.tutorbtnaprov);
            canceled=itemView.findViewById(R.id.tutorbtncancl);
            delt=itemView.findViewById(R.id.btn_dlt);
            crd1=itemView.findViewById(R.id.crd1);
        }
    }








}
