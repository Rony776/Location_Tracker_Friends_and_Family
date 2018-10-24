package com.techdoctorbd.locationtrackerfnf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    String email;
    EditText e5_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        e5_password = (EditText)findViewById(R.id.editText5);
        Intent myIntent = getIntent();
        if (myIntent != null)
        {
            email = myIntent.getStringExtra("email");
        }
    }


    public void goToNamePickActivity(View v)
    {
        if (e5_password.getText().toString().length() > 5)
        {
            Intent myIntent = new Intent(PasswordActivity.this,NameActivity.class);
            myIntent.putExtra("email",email);
            myIntent.putExtra("password",e5_password.getText().toString());
            startActivity(myIntent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Password Legnth Should be More Then 6 Characters",Toast.LENGTH_SHORT).show();
        }
    }
}
