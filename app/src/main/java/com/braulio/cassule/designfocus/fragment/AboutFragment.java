package com.braulio.cassule.designfocus.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.braulio.cassule.designfocus.model.Details;
import com.braulio.cassule.designfocus.R;
import com.braulio.cassule.designfocus.activity.EditProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView aboutMeTextView;
    TextView birthDateTextView;
    TextView heightTextView;

    public AboutFragment() {
        // Required empty public constructor
        }

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_about, container, false);
        aboutMeTextView = (TextView)view.findViewById(R.id.user_about_me_text_view);
        birthDateTextView = (TextView)view.findViewById(R.id.user_birth_date_text_view);
        heightTextView = (TextView)view.findViewById(R.id.user_height_text_view);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab10);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User-Details").child(user.getUid());

    }

    @Override
    public void onStart() {
        super.onStart();
        final ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Details detailsModel = dataSnapshot.getValue(Details.class);

                if (dataSnapshot.exists()) {
                    aboutMeTextView.setText(detailsModel.aboutMe);
                    birthDateTextView.setText(detailsModel.birthDate);
                    heightTextView.setText(detailsModel.height);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);
    }

}
