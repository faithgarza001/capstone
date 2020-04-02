package com.capstone.kitsune.repositories;

import com.capstone.kitsune.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByBlogId(long id);
}
