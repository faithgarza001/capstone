package com.capstone.kitsune.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text_title", nullable = false, length = 50)
    private String textTitle;

    @Column(name = "text_body", nullable = false, length = 500)
    private String textBody;

    @Column(name = "link_url", length = 500)
    private String linkUrl;

    @Column(name = "link_title", length = 50)
    private String linkTitle;

    @Column(name = "link_caption", length = 50)
    private String linkCaption;

    @Column(name = "video_embed_code", length = 500)
    private String videoEmbedCode;

    @Column(name = "post_status", length = 50)
    private String postStatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created",nullable = false)
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="posts_categories",
            joinColumns={@JoinColumn(name="post_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")}
    )
    private List<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="posts_images",
            joinColumns={@JoinColumn(name="post_id")},
            inverseJoinColumns={@JoinColumn(name="image_id")}
    )
    private List<Image> images;

    public Post() {
    }

    public Post(long id, String textTitle, String textBody, String linkUrl, String linkTitle, String linkCaption, String videoEmbedCode, String postStatus, Date dateCreated, Blog blog, List<Category> categories, List<Image> images) {
        this.id = id;
        this.textTitle = textTitle;
        this.textBody = textBody;
        this.linkUrl = linkUrl;
        this.linkTitle = linkTitle;
        this.linkCaption = linkCaption;
        this.videoEmbedCode = videoEmbedCode;
        this.postStatus = postStatus;
        this.dateCreated = dateCreated;
        this.blog = blog;
        this.categories = categories;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkCaption() {
        return linkCaption;
    }

    public void setLinkCaption(String linkCaption) {
        this.linkCaption = linkCaption;
    }

    public String getVideoEmbedCode() {
        return videoEmbedCode;
    }

    public void setVideoEmbedCode(String videoEmbedCode) {
        this.videoEmbedCode = videoEmbedCode;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}

