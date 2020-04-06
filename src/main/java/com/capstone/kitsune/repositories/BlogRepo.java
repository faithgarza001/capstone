package com.capstone.kitsune.repositories;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepo extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long user_id);
    List<Blog> findByHandle(String handle);
    Blog findByid(Long id);
}
