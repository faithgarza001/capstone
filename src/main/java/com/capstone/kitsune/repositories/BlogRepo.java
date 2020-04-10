package com.capstone.kitsune.repositories;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepo extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long user_id);
    Blog findByhandle(String handle);
    List<Blog> findByhandleContains(String handle);
    Blog findByid(Long id);
    @Query("from Blog b join b.categories bc where b.handle like concat('%',:search,'%') or bc.name like concat('%',:search,'%')")
    List<Blog> findAllByhandleOrcategories(@Param("search") String search);
}
