package com.techdoctorbd.locationtrackerfnf;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinCircleActivity extends AppCompatActivity {

    Pinview pinview;
    DatabaseReference reference,currentreference,cirlereference;
    FirebaseUser user;
    FirebaseAuth auth;
    String current_user_id,join_user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_circle);

        pinview = (Pinview)findViewById(R.id.pinview);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        currentreference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        current_user_id = user.getUid();
    }

    public void JoinBuuttonClick(View v)
    {
        Query query = reference.orderByChild("code").equalTo(pinview.getValue());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    CreateUser createUser = null;
                    for (DataSnapshot childDss : dataSnapshot.getChildren())
                    {
                        createUser = childDss.getValue(CreateUser.class);
                        join_user_id = createUser.userid;

                        cirlereference = FirebaseDatabase.getInstance().getReference().child("Users").child(join_user_id).child("CircleMembers");

                        Circlejoin circlejoin = new Circlejoin(current_user_id);
                        Circlejoin circlejoin1 = new Circlejoin(join_user_id);


                        cirlereference.child(user.getUid()).setValue(circlejoin)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                      if (task.isSuccessful())
                                      {
                                          Toast.makeText(getApplicationContext(),"User joined Circle Successfully",Toast.LENGTH_SHORT).show();
                                      }
                                      else
                                      {
                                          Toast.makeText(getApplicationContext(),"Somenthing Went wrong",Toast.LENGTH_SHORT).show();
                                      }
                                    }
                                });
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Circle Code",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
