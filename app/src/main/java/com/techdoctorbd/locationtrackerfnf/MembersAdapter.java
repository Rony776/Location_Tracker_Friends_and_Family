package com.techdoctorbd.locationtrackerfnf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder> {

    ArrayList<CreateUser> namelist;
    Context c;

    MembersAdapter(ArrayList<CreateUser> namelist,Context c)
    {
        this.namelist = namelist;
        this.c = c;
    }


    @Override
    public int getItemCount() {
        return namelist.size();
    }

    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        MembersViewHolder membersViewHolder = new MembersViewHolder(v,c,namelist);

        return membersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder membersViewHolder, int i) {

        CreateUser createUserObj = namelist.get(i);
        membersViewHolder.text_name.setText(createUserObj.name);
        Picasso.get().load(createUserObj.imageUrl).placeholder(R.drawable.profile).into(membersViewHolder.image_profile);





    }

    public static class MembersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView text_name;
        CircleImageView image_profile;
        Context c;
        ArrayList<CreateUser> nameArrayList;
        FirebaseAuth auth;
        FirebaseUser user;

        public MembersViewHolder(@NonNull View itemView, TextView text_name, Context c, ArrayList<CreateUser> nameArrayList) {
            super(itemView);
            this.text_name = text_name;
            this.c = c;
            this.nameArrayList = nameArrayList;


            itemView.setOnClickListener(this);
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();


            text_name = itemView.findViewById(R.id.item_title);
            image_profile = itemView.findViewById(R.id.circleImageView1);
        }


        @Override
        public void onClick(View view) {
            Toast.makeText(c,"You clicked this user",Toast.LENGTH_LONG).show();
        }
    }
}
