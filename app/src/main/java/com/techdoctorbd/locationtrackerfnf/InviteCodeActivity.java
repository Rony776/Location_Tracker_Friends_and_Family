package com.techdoctorbd.locationtrackerfnf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class InviteCodeActivity extends AppCompatActivity {

    String name,email,password,isSharing,date,code;
    Uri imageUri;
    ProgressDialog progressDialog;

    TextView t1;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference referenc;
    StorageReference storageReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1 = (TextView)findViewById(R.id.textView);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Intent myIntent = getIntent();

        referenc = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_Images");

        if (myIntent != null)
        {
            email=myIntent.getStringExtra("email");
            password=myIntent.getStringExtra("password");
            name=myIntent.getStringExtra("name");
            code = myIntent.getStringExtra("code");
            date = myIntent.getStringExtra("date");
            isSharing = myIntent.getStringExtra("isSharing");
            imageUri = myIntent.getParcelableExtra("imageUri");

        }

        t1.setText(code);
    }


    public void registerUser(View v)
    {
        progressDialog.setMessage("Creating Your Account");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            //Insert Value in Real Time Database


                            user = auth.getCurrentUser();


                            CreateUser createUser = new CreateUser(name,email,password,code,"false","na","na","na",user.getUid());
                            user = auth.getCurrentUser();
                            userId = user.getUid();


                            referenc.child(userId).setValue(createUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                StorageReference sr = storageReference.child(user.getUid() + ".jpg");
                                                sr.putFile(imageUri)
                                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                                        if (task.isSuccessful())
                                                        {
                                                            String download_image_path = task.getResult().getUploadSessionUri().toString();
                                                            referenc.child(user.getUid()).child("imageUrl").setValue(download_image_path)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful())
                                                                            {
                                                                                progressDialog.dismiss();
                                                                                //Toast.makeText(getApplicationContext(),"Email Sent for verification , Please Check Email",Toast.LENGTH_LONG).show();
                                                                                sendVerificationEmail();
                                                                                Intent myIntent  = new Intent(InviteCodeActivity.this,LoginActivity.class);
                                                                                startActivity(myIntent);
                                                                            }
                                                                            else
                                                                            {
                                                                                progressDialog.dismiss();
                                                                                Toast.makeText(getApplicationContext(),"Am error Occured",Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                                
                                            }
                                            else
                                            {
                                                progressDialog.dismiss();

                                                Toast.makeText(getApplicationContext(),"Could not Rergister user",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }



    public void sendVerificationEmail()
    {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Email Sent for verification",Toast.LENGTH_SHORT).show();
                            finish();
                            auth.signOut();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Could not send Email",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
