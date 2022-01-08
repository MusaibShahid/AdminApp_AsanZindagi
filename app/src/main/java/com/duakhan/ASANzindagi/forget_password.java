package com.duakhan.ASANzindagi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {
    private EditText passwordEmail;
    private Button RESET_PASSWORD;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        passwordEmail = (EditText)findViewById(R.id.email);
        RESET_PASSWORD =(Button)findViewById(R.id.btn_password);
        firebaseAuth = FirebaseAuth.getInstance();


        RESET_PASSWORD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = passwordEmail.getText().toString().trim();
                if(useremail.equals("")){
                    Toast.makeText(forget_password.this, "please enter your registered email ID", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(forget_password.this, "password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forget_password.this,login_service.class));
                            }else{
                                Toast.makeText(forget_password.this, "Error is sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });



    }

}