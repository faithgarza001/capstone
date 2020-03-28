package com.capstone.kitsune.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String url;

    @Column(name = "small_size")
    private String smallSize;

    @Column(name = "medium_size")
    private String mediumSize;

    @Column(length = 500)
    private String caption;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "images")
    private List<Post> posts;

    public Image() {
    }

    public Image(long id, String url, String smallSize, String mediumSize, String caption, User user, List<Post> posts) {
        this.id = id;
        this.url = url;
        this.smallSize = smallSize;
        this.mediumSize = mediumSize;
        this.caption = caption;
        this.user = user;
        this.posts = posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmallSize() {
        return smallSize;
    }

    public void setSmallSize(String smallSize) {
        this.smallSize = smallSize;
    }

    public String getMediumSize() {
        return mediumSize;
    }

    public void setMediumSize(String mediumSize) {
        this.mediumSize = mediumSize;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
