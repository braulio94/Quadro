package com.braulio.cassule.designfocus;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{
    static final String TAG = "EditProfileActivity";

    DatabaseReference mDatabase;
    LinearLayout mLinearAddMore;
    Button mButtonAddMore;
    Spinner spinnerProvince;
    Spinner spinnerMunicipality;
    Spinner spinnerJobMonth;
    EditText editTextFullName;
    EditText editTextBirthDay;
    EditText editTextBirthMonth;
    EditText editTextBirthYear;
    EditText editTextHeight;
    EditText editTextAboutMe;
    EditText editTextJobPosition;
    EditText editTextCompanyName;
    EditText editTextCompanyLocation;
    EditText editTextJobYear;
    EditText editTextHomeEmail;
    EditText editTextHomePhone;
    EditText editTextWorkEmail;
    EditText editTextWorkPhone;
    EditText editTextFacebookName;
    EditText editTextFacebookLink;
    EditText editTextInstaName;
    EditText editTextInstaLink;
    Button saveDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        spinnerProvince = (Spinner) findViewById(R.id.province_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(EditProfileActivity.this,
                R.array.province, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setOnItemSelectedListener(EditProfileActivity.this);
        spinnerProvince.setAdapter(spinnerAdapter);
        spinnerJobMonth = (Spinner) findViewById(R.id.spinner_job_month);
        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(EditProfileActivity.this,
                R.array.month, android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJobMonth.setAdapter(Adapter);
         editTextFullName = (EditText)findViewById(R.id.full_name_edit_text);
         editTextBirthDay= (EditText)findViewById(R.id.birth_day_edit_text);
         editTextBirthMonth= (EditText)findViewById(R.id.birth_month_edit_text);
         editTextBirthYear= (EditText)findViewById(R.id.birth_year_edit_text);
         editTextHeight = (EditText)findViewById(R.id.height_edit_text);
         editTextAboutMe = (EditText)findViewById(R.id.about_me_edit_text);
         editTextJobPosition = (EditText)findViewById(R.id.job_position_edit_text);
         editTextCompanyName = (EditText)findViewById(R.id.company_name_edit_text);
         editTextCompanyLocation = (EditText)findViewById(R.id.company_location_edit_text);
         editTextJobYear = (EditText)findViewById(R.id.job_year_edit_text);
         editTextHomeEmail = (EditText)findViewById(R.id.home_email_edit_text);
         editTextHomePhone = (EditText)findViewById(R.id.home_phone_edit_text);
         editTextWorkEmail = (EditText)findViewById(R.id.work_email_edit_text);
         editTextWorkPhone = (EditText)findViewById(R.id.work_phone_edit_text);
         editTextFacebookName = (EditText)findViewById(R.id.facebook_name_edit_text);
         editTextFacebookLink = (EditText)findViewById(R.id.facebook_link_edit_text);
         editTextInstaName = (EditText)findViewById(R.id.instagram_name_edit_text);
         editTextInstaLink = (EditText)findViewById(R.id.instagram_link_edit_text);
        saveDetailsButton = (Button)findViewById(R.id.save_user_details);
        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserDetails();
            }
        });
        mLinearAddMore = (LinearLayout)findViewById(R.id.more_contact_layout);
        mButtonAddMore = (Button)findViewById(R.id.add_more_contact);
    }

    private void getUserDetails(){
        final String id = user.getUid();
        final String fullName = editTextFullName.getText().toString();
        final String birthYear = editTextBirthYear.getText().toString();
        final String birthDate = editTextBirthDay.getText().toString()+ "-" + editTextBirthMonth.getText().toString()+ "-" + editTextBirthYear.getText().toString();
        final String height = editTextHeight.getText().toString();
        final String aboutMe = editTextAboutMe.getText().toString();
        final String jobPosition = editTextJobPosition.getText().toString();
        final String companyName = editTextCompanyName.getText().toString();
        final String companyLocation = editTextCompanyLocation.getText().toString();
        final String jobStartDate = spinnerJobMonth.getSelectedItem().toString()+ ", "+ editTextJobYear.getText().toString();
        final String homeEmail = editTextHomeEmail.getText().toString();
        final String homePhone = editTextHomePhone.getText().toString();
        final String workEmail = editTextWorkEmail.getText().toString();
        final String workPhone = editTextWorkPhone.getText().toString();
        final String facebookName = editTextFacebookName.getText().toString();
        final String facebookLink = editTextFacebookLink.getText().toString();
        final String instaName = editTextInstaName.getText().toString();
        final String instaLink = editTextInstaLink.getText().toString();
        final String userId = user.getUid();
        if (spinnerProvince.getSelectedItem().toString() == null || spinnerProvince.getSelectedItem().toString().equals("Selecionar Provincia")) {
            Toast.makeText(EditProfileActivity.this, R.string.select_province, Toast.LENGTH_SHORT).show();
        }else if(spinnerMunicipality.getSelectedItem().toString() == null || spinnerMunicipality.getSelectedItem().toString().equals("Selecionar Municipio")){
            Toast.makeText(EditProfileActivity.this, R.string.select_municipality, Toast.LENGTH_SHORT).show();
        }
        else{
            final String userLocation = spinnerMunicipality.getSelectedItem().toString()+", "+ spinnerProvince.getSelectedItem().toString();
            mDatabase.child("Details").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (user == null) {
                        // User is null, error out
                        Log.e(TAG, "User " + userId + " is unexpectedly null");
                        Toast.makeText(EditProfileActivity.this,
                                "Error: could not fetch user.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        saveUserDetails(id, fullName, birthDate, birthYear, height, aboutMe, userLocation, jobPosition, companyName, companyLocation, jobStartDate, homeEmail, homePhone, workEmail, workPhone, facebookName, facebookLink, instaName, instaLink);
                        Handler myHandler = new Handler();
                        myHandler.postDelayed(mMyRunnable, 1000);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(EditProfileActivity.this, "Failed to load post.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void saveUserDetails(String id, String fullName, String birthDate, String birthYear, String height, String aboutMe, String userLocation, String jobPosition, String companyName, String companyLocation, String jobStartDate, String homeEmail, String homePhone, String workEmail, String workPhone, String facebookName, String facebookLink, String instaName, String instaLink){
        //String key = mDatabase.child("Details").push().getKey();
        Details detailsModel = new Details( id, fullName, birthDate, birthYear, height, aboutMe, userLocation, jobPosition, companyName, companyLocation, jobStartDate, homeEmail, homePhone, workEmail, workPhone, facebookName, facebookLink, instaName, instaLink);
        Map<String, Object> detailValues = detailsModel.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/Details/"+key, detailValues);
        childUpdates.put("/User-Details/"+ user.getUid(),detailValues);
        mDatabase.updateChildren(childUpdates);
    }

    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent(EditProfileActivity.this, UserActivity.class);
            startActivity(intent);

        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //int spinner = (int) parent.getItemAtPosition(position);
        switch (position) {
            case 0:
                selectProvince(R.array.select_municipality);
                break;
            case 1:
                int municipality1 = R.array.municipalities;
                selectProvince(municipality1);
                break;
            case 2:
                int municipality2 = R.array.municipalities2;
                selectProvince(municipality2);
                break;
            case 3:
                int municipality3 = R.array.municipalities3;
                selectProvince(municipality3);
                break;
            case 4:
                int municipality4 = R.array.municipalities4;
                selectProvince(municipality4);
                break;
            case 5:
                int municipality5 = R.array.municipalities5;
                selectProvince(municipality5);
                break;
            case 6:
                int municipality6 = R.array.municipalities6;
                selectProvince(municipality6);
                break;
            case 7:
                selectProvince(R.array.municipalities7);
                break;
            case 8:
                selectProvince(R.array.municipalities8);
                break;
            case 9:
                selectProvince(R.array.municipalities9);
                break;
            case 10:
                selectProvince(R.array.municipalities10);
                break;
            case 11:
                selectProvince(R.array.municipalities11);
                break;
            case 12:
                selectProvince(R.array.municipalities12);
                break;
            case 13:
                selectProvince(R.array.municipalities13);
                break;
            case 14:
                selectProvince(R.array.municipalities14);
                break;
            case 15:
                selectProvince(R.array.municipalities15);
                break;
            case 16:
                selectProvince(R.array.municipalities16);
                break;
            case 17:
                selectProvince(R.array.municipalities17);
                break;
            case 18:
                selectProvince(R.array.municipalities18);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addMoreContact(View view){

        mLinearAddMore.setVisibility(View.VISIBLE);
        mButtonAddMore.setVisibility(View.GONE);
    }

    public void selectProvince(int municipality){

        spinnerMunicipality = (Spinner) findViewById(R.id.district_spinner);
        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(EditProfileActivity.this,
                municipality , android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setOnItemSelectedListener(EditReservationActivity.this);
        spinnerMunicipality.setAdapter(Adapter);
    }
    public void close(View v){
        finish();
    }
}
