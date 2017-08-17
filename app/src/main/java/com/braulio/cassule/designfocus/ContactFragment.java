package com.braulio.cassule.designfocus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class ContactFragment extends Fragment {
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;
    TextView homeEmailTextView;
    TextView workEmailTextView;
    TextView homePhoneTextView;
    TextView workPhoneTextView;
    TextView facebookNameTextView;
    TextView instagramTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blank, container, false);
        homeEmailTextView = (TextView)view.findViewById(R.id.user_home_email_text_view);
        workEmailTextView = (TextView)view.findViewById(R.id.user_work_email_text_view);
        homePhoneTextView = (TextView)view.findViewById(R.id.user_home_phone_text_view);
        workPhoneTextView = (TextView)view.findViewById(R.id.user_work_phone_text_view);
        facebookNameTextView = (TextView)view.findViewById(R.id.user_facebook_name_text_view);
        instagramTextView = (TextView)view.findViewById(R.id.user_instagram_text_view);
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
                    homeEmailTextView.setText(detailsModel.homeEmail);
                    workEmailTextView.setText(detailsModel.workEmail);
                    homePhoneTextView.setText(detailsModel.homePhone);
                    workPhoneTextView.setText(detailsModel.workPhone);
                    facebookNameTextView.setText(detailsModel.facebookName);
                    instagramTextView.setText(detailsModel.instaName);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);
    }

    public ContactFragment() {
        // Required empty public constructor
    }

}
