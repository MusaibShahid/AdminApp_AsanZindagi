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
import com.duakhan.ASANzindagi.databinding.ActivityDoctorHomeBinding;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.duakhan.ASANzindagi.models.doctor_model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
public class doctor_adapter extends FirebaseRecyclerAdapter<doctor_model,doctor_adapter.myviewholder> {
    public doctor_adapter(@NonNull FirebaseRecyclerOptions<doctor_model> options) {
        super(options);
    }
    String Title="";
    String Massage=" ";
    String Usertoken="";
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull  doctor_model doctor_model) {
        holder.Username.setText(doctor_model.getUsername());
        holder.Time.setText(doctor_model.getTime());
        holder.status.setText(doctor_model.getStatus());
        Usertoken=doctor_model.getToken();
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.delt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(holder.card3.getContext())
                        .setTitle("Delete user")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(R.drawable.delet)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Doctor_Appointment").child(uid).child(getRef(position).getKey()).removeValue();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                            }
                        });
                builder.show();
            }});
//dlt
        holder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object>map=new HashMap<>();
                map.put("status","Approve");
                Title="Approved";
                Massage="Your appointment is Approved";
              //  Toast.makeText(v.getContext(), Usertoken +" Token "+ Title +" Title "+ Massage +"Massage ", Toast.LENGTH_SHORT).show();
                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(Usertoken,
                        Title,
                        Massage,
                        v.getContext().getApplicationContext(),
                        v.getContext());
                notificationsSender.SendNotifications();
                FirebaseDatabase.getInstance().getReference().child("Doctor_Appointment").child(uid).child(getRef(position).getKey()).updateChildren(map)
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
                FirebaseDatabase.getInstance().getReference().child("Doctor_Appointment").child(uid).child(getRef(position).getKey()).updateChildren(map)
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
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rows,parent,false);
return new myviewholder(view);
    }
    public class myviewholder extends RecyclerView.ViewHolder{
    TextView Username,Time,status;
    Button approved;
    Button canceled;
ImageView delt3;
CardView card3;
    public myviewholder(@NonNull View itemView) {
        super(itemView);
        Username =itemView.findViewById(R.id.docnam);
        Time =itemView.findViewById(R.id.timdoc);
        status=itemView.findViewById(R.id.status);
        approved=itemView.findViewById(R.id.btnaprov);
        canceled=itemView.findViewById(R.id.btncancl);
        delt3=itemView.findViewById(R.id.btn_dlt3);
        card3=itemView.findViewById(R.id.crd3);
    }}}
