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

    @Column(nullable = false, length = 200)
    private String textTitle;

    @Column(nullable = false)
    private String textBody;

    @Column
    private String linkUrl;

    @Column
    private String linkTitle;

    @Column
    private String linkCaption;

    @Column
    private String videoEmbedCode;

    @Column
    private String postStatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
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
    private List<Category> categories;

    public Post() {}



}

