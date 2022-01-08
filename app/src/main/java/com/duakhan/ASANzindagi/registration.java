package com.duakhan.ASANzindagi;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.duakhan.ASANzindagi.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.duakhan.ASANzindagi.models.registration_model;
public class registration extends AppCompatActivity {
    ActivityRegistrationBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    Spinner spinner;
    //img
    private CircleImageView profile_img;
    //private Button uploadbtn;
    private StorageReference reference= FirebaseStorage.getInstance().getReference();
    private Uri imageUri;
//img
//Animation
    Animation leftAnim;
    ConstraintLayout anim_layout1;
    //Animation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//animation
        leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_animation);
        anim_layout1 =findViewById(R.id.reg);
        anim_layout1.setAnimation(leftAnim);
        //animation
        //spinner
        spinner = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("select services");
        list.add("Doctor");
        list.add("Lawyer");
        list.add("Tutor");
        int a;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //spinner
        String profile = null;
        progressDialog = new ProgressDialog(registration.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("we are creating your account");
        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.signupEmail.getText().toString(), binding.signupPass.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(registration.this, login_service.class);
                                    startActivity(intent);
                                    if (imageUri != null) {
                                        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                                        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String profileurl = uri.toString();
                                                        String id = task.getResult().getUser().getUid();
                                                        registration_model provider = new registration_model(
                                                                profileurl,
                                                                binding.username.getText().toString().trim(),
                                                                binding.signupEmail.getText().toString(),
                                                                binding.signupPass.getText().toString(),
                                                                binding.prof.getText().toString(),
                                                                binding.special.getText().toString(),
                                                                binding.day.getText().toString(),
                                                                binding.time.getText().toString(),
                                                                binding.address.getText().toString(),
                                                                binding.fee.getText().toString(),
                                                                binding.contact.getText().toString(),
                                                                id.toString());
                                                        ;
                                                        String services = spinner.getSelectedItem().toString();
                                                        database.getReference().child(services).child(id).setValue(provider);
                                                        Toast.makeText(registration.this, "Upload succesfully", Toast.LENGTH_SHORT).show();
                                                    }});
                                            }
                                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(registration.this, "Uploading Fields!!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(registration.this, "please select image", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(registration.this, "user created successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } }
                        }); }
        });
        profile_img = findViewById(R.id.profile_img);
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, 2);
            }});
       }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) ;
        imageUri = data.getData();
        profile_img.setImageURI(imageUri);
    }
    private String getFileExtension(Uri muri){
        ContentResolver cr= getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }}
