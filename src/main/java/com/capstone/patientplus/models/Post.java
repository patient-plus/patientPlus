package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;

    @Id @GeneratedValue
    private long id;

    public Post(){}

    public Post(String title, String body, long id){
        this.title = title;
        this.body = body;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
