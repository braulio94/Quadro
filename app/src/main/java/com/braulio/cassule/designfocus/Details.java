package com.braulio.cassule.designfocus;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Braulio on 12/7/2016.
 **/
@IgnoreExtraProperties
public class Details {

    public String id;
    private String fullName;
    String birthDate;
    String birthYear;
    String height;
    String aboutMe;
    String userLocation;
    String jobPosition;
    private String companyName;
    private String companyLocation;
    private String jobStartDate;
    String homeEmail;
    String homePhone;
    String workEmail;
    String workPhone;
    String facebookName;
    private String facebookLink;
    String instaName;
    private String instaLink;

    String getJobPosition() {
        return jobPosition;
    }

    String getAboutMe() {
        return aboutMe;
    }

    Details(){}

    Details(String id, String fullName, String birthDate, String birthYear, String height, String aboutMe, String userLocation, String jobPosition, String companyName, String companyLocation, String jobStartDate, String homeEmail, String homePhone, String workEmail, String workPhone, String facebookName, String facebookLink, String instaName, String instaLink){
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.birthYear = birthYear;
        this.height = height;
        this.aboutMe = aboutMe;
        this.userLocation = userLocation;
        this.jobPosition = jobPosition;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.jobStartDate = jobStartDate;
        this.homeEmail = homeEmail;
        this.homePhone = homePhone;
        this.workEmail = workEmail;
        this.workPhone = workPhone;
        this.facebookName = facebookName;
        this.facebookLink = facebookLink;
        this.instaName = instaName;
        this.instaLink = instaLink;
    }

    @Exclude
    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fullName",fullName);
        result.put("birthDate",birthDate);
        result.put("birthYear", birthYear);
        result.put("height",height);
        result.put("aboutMe",aboutMe);
        result.put("userLocation",userLocation);
        result.put("jobPosition",jobPosition);
        result.put("companyName",companyName);
        result.put("companyLocation",companyLocation);
        result.put("jobStartDate",jobStartDate);
        result.put("homeEmail",homeEmail);
        result.put("homePhone",homePhone);
        result.put("workEmail",workEmail);
        result.put("workPhone",workPhone);
        result.put("facebookName",facebookName);
        result.put("facebookLink",facebookLink);
        result.put("instaName",instaName);
        result.put("instaLink",instaLink);
        return result;
    }
}
