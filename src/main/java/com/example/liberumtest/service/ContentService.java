package com.example.liberumtest.service;

import com.example.liberumtest.models.Content;
import com.example.liberumtest.repo.ContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {
    private final ContentRepo contentRepo;

    @Autowired
    public ContentService(ContentRepo contentRepo) {
        this.contentRepo = contentRepo;
    }

    public void addContent(String name){
        if (contentRepo.findContentByContentName(name) == null)
            contentRepo.save(
                    Content.builder()
                            .contentName(name)
                            .build()
            );
    }
}
