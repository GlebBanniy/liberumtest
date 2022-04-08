package com.example.liberumtest.repo;

import com.example.liberumtest.models.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {
    Content findContentByContentName(String name);
}
