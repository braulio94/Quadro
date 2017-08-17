package com.braulio.cassule.designfocus;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;
    public String image;
    public String authorImage;
    public String authorName;
    public String authorJobTitle;
    public String aboutTheAuthor;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();
    public Long timeStamp;


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String image, String authorImage, String authorName, String authorJobTitle, String aboutTheAuthor) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.image = image;
        this.authorImage = authorImage;
        this.authorName = authorName;
        this.authorJobTitle = authorJobTitle;
        this.aboutTheAuthor = aboutTheAuthor;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("image", image);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("authorImage", authorImage);
        result.put("authorName", authorName);
        result.put("authorJobTitle", authorJobTitle);
        result.put("aboutTheAuthor", aboutTheAuthor);
        result.put("timeStamp", ServerValue.TIMESTAMP);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
