package com.braulio.cassule.designfocus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.intrusoft.library.FunkyHeader;
import com.squareup.picasso.Picasso;
import java.util.Calendar;

public class UserActivity extends BaseActivity {

    ImageView backHome;
    ImageView overFlow;
    private DatabaseReference mDatabase;
    private ViewPager mViewPager;
    private NavigationTabStrip mCenterNavigationTabStrip;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    TextView userNameTextView;
    TextView userAgeTextView;
    TextView userJobPositionTextView;
    TextView userLocationTextView;
    ImageView profileImageView;
    FunkyHeader funkyHeader;
    TextView commaText;
    private static final int GALLERY_INTENT = 2;
    Uri imageUrl;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User-Details").child(user.getUid());
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    startActivity(new Intent(UserActivity.this, AuthActivity.class));
                    finish();
                }
            }
        };
        profileImageView = (ImageView)findViewById(R.id.user_profile_image_view);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        Picasso.with(UserActivity.this).load(user.getPhotoUrl()).fit().centerCrop().into(profileImageView);

        userNameTextView = (TextView)findViewById(R.id.user_name_text_view);
        userNameTextView.setText(user.getDisplayName());
        userAgeTextView = (TextView)findViewById(R.id.user_age_text_view);
        userJobPositionTextView = (TextView)findViewById(R.id.user_job_position);
        userLocationTextView = (TextView)findViewById(R.id.user_location_text_view);
        commaText = (TextView)findViewById(R.id.simple_comma_text);
        backHome = (ImageView)findViewById(R.id.up_as_home_button);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        overFlow = (ImageView)findViewById(R.id.overflow_menu);
        funkyHeader = (FunkyHeader) findViewById(R.id.wave_head);
        funkyHeader.setImageSource(profileImageView.getImageAlpha());
        initUI();
        setUI();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            showProgressDialog();
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("user-photos").child(user.getUid()).child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    hideProgressDialog();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    imageUrl = downloadUri;
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(downloadUri)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UserActivity.this, R.string.upload__success, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    hideProgressDialog();
                    Toast.makeText(UserActivity.this, R.string.upload_failure, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();


        final ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Details detailsModel = dataSnapshot.getValue(Details.class);

                if (dataSnapshot.exists()) {

                    int birthYear = Integer.parseInt(detailsModel.birthYear);
                    int realYear = year - birthYear;
                    userAgeTextView.setText(String.valueOf(realYear));
                    if (userAgeTextView.getText() != null){
                        commaText.setVisibility(View.VISIBLE);
                    }
                    userJobPositionTextView.setText(detailsModel.jobPosition);
                    userLocationTextView.setText(detailsModel.userLocation);
                    hideProgressDialog();
                } else {
                    hideProgressDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // Toast.makeText(UserActivity.this, "Failed to load post.",
                 //       Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addValueEventListener(userListener);
    }

    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mCenterNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts_center);
    }

    private void setUI() {
        mViewPager.setAdapter(new UserActivity.CustomAdapter(getSupportFragmentManager()));
        mCenterNavigationTabStrip.setViewPager(mViewPager);
    }

    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments[] = {"About","Portfolio","Contact"};
        private CustomAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new AboutFragment();
                case 1:
                    return new PortfolioFragment();
                case 2:
                    return new ContactFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
        public CharSequence getPageTitle(int position){
            return fragments[position];
        }
    }

}
