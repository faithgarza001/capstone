package com.capstone.kitsune.repositories;

import com.capstone.kitsune.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
}
