package com.duakhan.ASANzindagi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duakhan.ASANzindagi.databinding.ActivityLoginServiceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class login_service extends AppCompatActivity {
    TextView Create_an_Account;
    ActivityLoginServiceBinding binding;
    ProgressDialog progressDialog;
    Spinner spinner;
    private TextView forget_password;
    FirebaseAuth auth;
    String email="";
    String password="";
    String field="";
    SharedPreferences sh;
    SharedPreferences.Editor editor;


    //animtion
Animation leftAnim;
RelativeLayout anim_layout;
//animation



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_service);

        sh=getSharedPreferences("data",MODE_PRIVATE);
        editor=sh.edit();
        email=sh.getString("E","");
        password=sh.getString("P","");
        field=sh.getString("Se","");
        if (!email.isEmpty() && !password.isEmpty() && !field.isEmpty() ) {

            Toast.makeText(this, field, Toast.LENGTH_SHORT).show();
            if(field.contains("Doctor")) {

                Intent intent = new Intent(login_service.this, doctor_home.class);
                startActivity(intent);
            }
            else if(field.contains("Lawyer")) {
                Intent intent = new Intent(login_service.this, lawyer_home.class);
                startActivity(intent);
            }
            else if(field.contains("Tutor")) {
                Intent intent = new Intent(login_service.this, tutor_home.class);
                startActivity(intent);
            }


        }
        binding = ActivityLoginServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth= FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(login_service.this);
        progressDialog.setTitle("creating account");
        progressDialog.setMessage("we are creating your account");
//frorget pass
        forget_password = (TextView)findViewById(R.id.forgot);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_service.this,forget_password.class));
            }
        });
        //forget pass

        //anim


        //spinner

        spinner = (Spinner)findViewById(R.id.spinner1);
        List<String> list=new ArrayList<String>();
        list.add("select services");
        list.add("Doctor");
        list.add("Lawyer");
        list.add("Tutor");
        int a;

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        //spinner

//animation on whole activity
        leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_animation);
        anim_layout =findViewById(R.id.loginanim);
        anim_layout.setAnimation(leftAnim);
        //animation on whole activity

        //go for create an account
        Create_an_Account  = (TextView) findViewById(R.id.account);
        Create_an_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Create_an_Account=new Intent(login_service.this, registration.class);
                startActivity(Create_an_Account);
            }
        });//go for create account

//user loging in their own home page
        binding.btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coding of codintion to check user is registered or not?
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.logemail.getText().toString(),binding.logpass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    String services = spinner.getSelectedItem().toString();
                                    //coding of spinner selection
                                    editor.putString("E",binding.logemail.getText().toString());
                                    editor.putString("P",binding.logpass.getText().toString());
                                    editor.putString("Se",services);
                                    editor.apply();

                                    if (services.contains("Doctor")) {
                                       //code
                                        Intent intent = new Intent(login_service.this, doctor_home.class);
                                        startActivity(intent);
                                    } else if (services.contains("Lawyer")) {
                                        //Toast.makeText(login_service.this, "Lawyer", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login_service.this, lawyer_home.class);
                                        startActivity(intent);
                                    } else if (services.contains("Tutor")) {

                                        Intent intent = new Intent(login_service.this, tutor_home.class);
                                        startActivity(intent);
                                      //  Toast.makeText(login_service.this, "Tutor", Toast.LENGTH_SHORT).show();

                                    }

                                } else{
                                    Toast.makeText(login_service.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }



                            }
                        });
            }
        });


    }






}
