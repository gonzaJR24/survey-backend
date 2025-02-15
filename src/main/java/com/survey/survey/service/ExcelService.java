package com.survey.survey.service;

import com.survey.survey.model.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private DataService dataService;

    public ByteArrayInputStream exportAllDataToExcel() {
        List<Data> dataList = dataService.getAllData();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Entrada", "HCU", "Número", "Medio", "Municipio", "Sector"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            int rowIndex = 1;
            for (Data data : dataList) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(data.getId());
                row.createCell(1).setCellValue(data.getEntrada());
                row.createCell(2).setCellValue(data.getHcu());
                row.createCell(3).setCellValue(data.getNumero());
                row.createCell(4).setCellValue(data.getMedio());
                row.createCell(5).setCellValue(data.getMunicipio());
                row.createCell(6).setCellValue(data.getSector());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while exporting data to Excel", e);
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }
}
