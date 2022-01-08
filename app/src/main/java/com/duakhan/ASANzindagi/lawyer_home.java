package com.duakhan.ASANzindagi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class lawyer_home extends AppCompatActivity {
    Button btnapp2;
    ImageView logout;
    SharedPreferences sh;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_home);

        //change by fragment
        btnapp2=findViewById(R.id.btn_app2);
        logout=findViewById(R.id.logout);
        btnapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnapp2.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.lawyer_container,new lawyer_appointment()).commit();

            }
        });//chnge by fragment
//logout lawyer
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout.setVisibility(View.GONE);
                sh=getSharedPreferences("data",MODE_PRIVATE);
                editor=sh.edit();
                editor.clear();
                editor.apply();
                Intent intent=new Intent(lawyer_home.this,login_service.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        //logout lawyer
    }


    //exit app on back press

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setIcon(R.drawable.exit);
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }

    //exit app on back press
}