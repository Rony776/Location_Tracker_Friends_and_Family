package com.techdoctorbd.locationtrackerfnf;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);

        auth=FirebaseAuth.getInstance();
    }
    public void login(View v)
    {
        auth.signInWithEmailAndPassword(e1.getText().toString(),e2.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            //Toast.makeText(getApplicationContext(), "User Logged In Successfully", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = auth.getCurrentUser();
                            if (user.isEmailVerified())
                            {
                                Intent myIntent = new Intent(LoginActivity.this,MyNavigationActivity.class);
                                startActivity(myIntent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Email not verified",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Wrong Email Or Password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
