package com.capstone.kitsune.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Blog> blogs;

    @ManyToMany(mappedBy = "categories")
    private List<Post> posts;

    public Category() {
    }

    public Category(long id, String name){
        this.id = id;
        this.name = name;
    }

    public Category(long id, String name, List<Blog> blogs, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.blogs = blogs;
        this.posts = posts;
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

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
