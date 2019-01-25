package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name = "insurance")
public class Insurance {


    @Column(nullable = false)
    private String imgURL;

    @Id
    @GeneratedValue
    private long id;


    public Insurance(){}

    public Insurance(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
