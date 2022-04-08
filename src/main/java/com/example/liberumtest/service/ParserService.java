package com.example.liberumtest.service;

import com.example.liberumtest.dto.DataDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ParserService {

    private final DataService dataService;
    private final EstimateService estimateService;
    private final ContentService contentService;
    private final List<String> data;

    @Autowired
    public ParserService(DataService dataService, EstimateService estimateService, ContentService contentService) {
        this.dataService = dataService;
        this.estimateService = estimateService;
        this.contentService = contentService;
        this.data = new ArrayList<>();
    }

    public List<String> getData() {
        return data;
    }

    public void startParsing(String fileName) {
        try (FileInputStream file = new FileInputStream(fileName)) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            getEstimates(rowIterator);
            getContent(rowIterator);
            getData(rowIterator);
            while (rowIterator.hasNext()) {
                getValues(rowIterator);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getTotal(Long contentId, Long estimateId, String companyName) {
        return dataService.getTotal(contentId, estimateId, companyName);
    }

    private void getEstimates(Iterator<Row> rowIterator) {
        Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (!cell.getStringCellValue().equals("") && !cell.getStringCellValue().equals("id") && !cell.getStringCellValue().equals("company")) {
                estimateService.addEstimate(cell.getStringCellValue());
            }
        }
    }

    private void getContent(Iterator<Row> rowIterator) {
        Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (!cell.getStringCellValue().equals("")) {
                contentService.addContent(cell.getStringCellValue());
            }
        }
    }

    private void getData(Iterator<Row> rowIterator) {
        Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (!cell.getStringCellValue().equals("") && !data.contains(cell.getStringCellValue())) {
                data.add(cell.getStringCellValue());
            }
        }
    }

    private void getValues(Iterator<Row> rowIterator) {
        DataDto dataDto = new DataDto();
        Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
        for (int i = 0; cellIterator.hasNext(); i++) {
            Cell cell = cellIterator.next();
            switch (cell.getCellType()) {
                case NUMERIC:
                    if (i == 0)
                        dataDto.setDataId((long) cell.getNumericCellValue());
                    else {
                        dataDto.setValue((int) cell.getNumericCellValue());
                        if (i < 6)
                            dataDto.setEstimateId(1L);
                        else
                            dataDto.setEstimateId(2L);
                        if (i == 2 || i == 3 || i == 6 || i == 7)
                            dataDto.setContentId(1L);
                        else
                            dataDto.setContentId(2L);
                        dataDto.setDateName(data.get(i % 2));
                        dataService.addData(dataDto);
                    }
                    break;
                case STRING:
                    dataDto.setCompanyName(cell.getStringCellValue());
                    break;
            }
        }
    }
}
