package com.nihatalim.draglist.model;

import java.util.Date;

/**
 * Created by thecower on 1/8/18.
 */

public class User {
    private long id;
    private String name;
    private Date dateCreated;

    public User(){
        this.dateCreated = new Date();
    }

    public User(long id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
