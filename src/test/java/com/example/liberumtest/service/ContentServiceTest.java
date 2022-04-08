package com.example.liberumtest.service;

import com.example.liberumtest.models.Content;
import com.example.liberumtest.repo.ContentRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentRepo contentRepo;
    private ContentService contentService;
    private AutoCloseable autoCloseable;
    private Content content;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        contentService = new ContentService(contentRepo);
        content = Content.builder()
                .contentName("testContent")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    private Content captureFinalVariable() {
        var contentArgumentCaptor = ArgumentCaptor.forClass(Content.class);
        verify(contentRepo).save(contentArgumentCaptor.capture());
        return contentArgumentCaptor.getValue();
    }

    @Test
    void addContent() {
        contentService.addContent("testContent");
        var value = captureFinalVariable();
        assertThat(value).isEqualTo(content);
    }
}
