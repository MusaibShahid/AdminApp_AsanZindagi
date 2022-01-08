package com.duakhan.ASANzindagi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Animation topAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        //animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        imageView =findViewById(R.id.logo);
        imageView.setAnimation(topAnim);

//animation


        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();


                } finally {
                    Intent intent = new Intent(MainActivity.this,login_service
                            .class);
                    startActivity(intent);
                }
            }
        };thread.start();








    }

    String Title="";
    String Massage=" ";





    }
