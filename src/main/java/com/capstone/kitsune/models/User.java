package com.capstone.kitsune.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 15)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "blogs_followed")
    private int blogsFollowed;

    @Column(name = "profile_picture")
    private String profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Blog> blogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_blogs",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "blog_id")}
    )
    private List<Blog> following;

    // Copy constructor for authentication
    public User(User copy) {
        id = copy.id;
        email = copy.email;
        username = copy.username;
        password = copy.password;
    }

    public User() {
    }

    // Full Constructor
    public User(String username, String firstname, String lastname, String email, String password, List<Blog> following, List<Blog> blogs, List<Post> posts, int blogsFollowed, String profilePicture){
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
        this.following = following;
        this.blogs = blogs;
        this.posts = posts;
        this.blogsFollowed = blogsFollowed;
        this.profilePicture = profilePicture;
    }

    // Profile constructor
    public User(String username, String firstname, String lastname, String email, String password, List<Blog> following, List<Blog> blogs, List<Post> posts){
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
        this.following = following;
        this.blogs = blogs;
        this.posts = posts;
    }


    //Following Constructor
    public User(String username, String firstname, String lastname, String email, String password, List<Blog> following){
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
        this.following = following;
    }

    //Registration Constructor
    public User(String username, String firstname, String lastname, String email, String password){
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
    }

    public User(String firstname, String lastname, String email, String password){
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
    }

    public List<Blog> getFollowing(){
        return this.following;
    }

    public void setFollowing(List<Blog> following){
        this.following = following;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBlogsFollowed() {
        return blogsFollowed;
    }

    public void setBlogsFollowed(int blogsFollowed) {
        this.blogsFollowed = blogsFollowed;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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