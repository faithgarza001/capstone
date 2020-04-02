package com.capstone.kitsune.repositories;

import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByidIn(long[] ids);

}