package com.braulio.cassule.designfocus.model;

import java.io.Serializable;

/**
 * Created by Braulio on 11/18/2016.
 **/

public class TimeLineModel implements Serializable{

    private String title;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
