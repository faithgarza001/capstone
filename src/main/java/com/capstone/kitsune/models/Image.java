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

    @Column
    private String small_size;

    @Column
    private String medium_size;

    @Column
    private String caption;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "images")
    private List<Post> posts;

}
