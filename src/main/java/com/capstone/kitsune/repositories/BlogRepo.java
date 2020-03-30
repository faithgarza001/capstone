package com.capstone.kitsune.repositories;

import com.capstone.kitsune.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<Blog, Long> {
}
