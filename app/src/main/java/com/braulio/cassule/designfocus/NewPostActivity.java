package com.braulio.cassule.designfocus;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;


public class NewPostActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    private DatabaseReference mDatabase;
    private EditText mBodyField;
    private EditText mCriminalNameField;
    private FloatingActionButton mSubmitButton;
    private Spinner mSpinnerType;
    private ImageView mCriminalPicture;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    Uri imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        mCriminalPicture = (ImageView)findViewById(R.id.picture_of_criminal);
        mCriminalPicture.setOnClickListener(this);
        mCriminalNameField = (EditText)findViewById(R.id.field_criminal_name);
        mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);
        mSpinnerType = (Spinner)findViewById(R.id.crime_type_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(NewPostActivity.this,
                R.array.crimeType, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerType.setOnItemSelectedListener(NewPostActivity.this);
        mSpinnerType.setAdapter(spinnerAdapter);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            showProgressDialog();
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    hideProgressDialog();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    imageUrl = downloadUri;
                    Picasso.with(NewPostActivity.this).load(downloadUri).fit().centerCrop().into(mCriminalPicture);
                    Toast.makeText(NewPostActivity.this, R.string.upload__success, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    hideProgressDialog();
                    Toast.makeText(NewPostActivity.this, R.string.upload_failure, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void submitPost() {
        Details detailsModel = new Details();

        final String title = mSpinnerType.getSelectedItem().toString();
        final String body = mBodyField.getText().toString();
        final String criminal = mCriminalNameField.getText().toString();
        final String auPhoto = String.valueOf(user.getPhotoUrl());
        final String auName = user.getDisplayName();
        final String auJobTitle = detailsModel.getJobPosition();
        final String aboutAu = detailsModel.getAboutMe();
        final String criminalImage = imageUrl.toString();

        if (TextUtils.isEmpty(criminal)) {
            mCriminalNameField.setError(REQUIRED);
            return;
        }

        if (criminalImage == null) {
            return;
        }
        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }
        if (mSpinnerType.getSelectedItem().toString() == null || mSpinnerType.getSelectedItem().toString().equals("Seleciona Categoria")) {
            Toast.makeText(NewPostActivity.this, R.string.select_crime, Toast.LENGTH_SHORT).show();
        }
        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        /*User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            */
                            writeNewPost(userId, criminal, title, body, criminalImage, auPhoto, auName, auJobTitle, aboutAu);

                        setEditingEnabled(true);
                        startActivity(new Intent(NewPostActivity.this, HomeActivity.class));
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        Toast.makeText(NewPostActivity.this,
                                "Error: could not connect to database.",
                                Toast.LENGTH_SHORT).show();
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void setEditingEnabled(boolean enabled) {
        mCriminalNameField.setEnabled(enabled);
        mSpinnerType.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String criminalName, String title, String body, String imageString, String authorImage, String authorName, String authorJob, String authorDiscription) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, criminalName, title, body, imageString, authorImage, authorName, authorJob, authorDiscription);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
        childUpdates.put("/category/" + title +"/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.picture_of_criminal){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        }
    }
    public void close(View v){
        finish();
    }
}