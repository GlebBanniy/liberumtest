package com.example.liberumtest.service;

import com.example.liberumtest.dto.DataDto;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ParserServiceTest {

    @Mock
    private DataService dataService;
    @Mock
    private EstimateService estimateService;
    @Mock
    private ContentService contentService;
    private List<String> data;
    private ParserService parserService;
    private AutoCloseable autoCloseable;
    private DataDto dataDto;

    @Captor
    private ArgumentCaptor<Long> argCaptorL;

    @Captor
    private ArgumentCaptor<String> argCaptorS;

    @Captor
    private ArgumentCaptor<DataDto> argCaptorDto;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        parserService = new ParserService(dataService, estimateService, contentService);
        data = new ArrayList<>(2);
        data.add("data1");
        data.add("data2");
        dataDto = new DataDto(
                1L,
                "company1",
                1L,
                1L,
                "data1",
                10
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void startParsing() {
        parserService.startParsing("testFile.xlsx");
        verify(estimateService, times(2)).addEstimate(argCaptorS.capture());
        verify(contentService, times(4)).addContent(argCaptorS.capture());
        verify(dataService, times(1)).addData(argCaptorDto.capture());
        List<String> list = new ArrayList<>(2);
        list.add("fact");
        list.add("forecast");
        list.add("Qliq");
        list.add("Qoil");
        list.add("Qliq");
        list.add("Qoil");
        assertThat(argCaptorS.getAllValues()).isEqualTo(list);
        assertThat(argCaptorDto.getValue()).isEqualTo(dataDto);
        assertThat(parserService.getData()).isEqualTo(data);
    }

    @Test
    void getTotal() {
        parserService.getTotal(1L, 1L, "name");
        verify(dataService).getTotal(argCaptorL.capture(), argCaptorL.capture(), argCaptorS.capture());
        List<Long> list = new ArrayList<>(2);
        list.add(1L);
        list.add(1L);
        assertThat(argCaptorL.getAllValues()).isEqualTo(list);
        assertThat(argCaptorS.getValue()).isEqualTo("name");
    }

    @Test
    void willThrowWhenFileReadError(){
        assertThatThrownBy(() ->parserService.startParsing("breakFile.xlsx"))
                .isInstanceOf(NotOfficeXmlFileException.class)
                .hasMessageContaining("No valid entries or contents found, this is not a valid OOXML (Office Open XML) file");
    }
}
