package com.capstone.kitsune.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "blog_title", nullable = false, length = 100)
    private String blogTitle;

    @Column(nullable = false, length = 100)
    private String handle;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    // Please review: Original SQL file did not have a list of posts in a blog. Remove if necessary
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "blogs_categories",
            joinColumns = {@JoinColumn(name = "blog_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories;

    public Blog() {
    }

    public Blog(long id, String blogTitle, String handle, Date dateCreated, List<Post> posts, User user, List<Category> categories) {
        this.id = id;
        this.blogTitle = blogTitle;
        this.handle = handle;
        this.dateCreated = dateCreated;
        this.posts = posts;
        this.user = user;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
