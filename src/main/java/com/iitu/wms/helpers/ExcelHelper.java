package com.iitu.wms.helpers;

import com.iitu.wms.entities.ProductEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelHelper {

    public static List<ProductEntity> readFromExcelFile(MultipartFile multipartFile) throws IOException {

        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();

        Workbook workbook = null;

        String fileExtensionName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream);
        } else if (fileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(fileInputStream);

        }

        List<ProductEntity> list = new ArrayList<>();

        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                ProductEntity productsEntity = new ProductEntity();

                productsEntity.setProduct_name(row.getCell(0).toString());
                productsEntity.setCount_expected((int) row.getCell(1).getNumericCellValue());
                productsEntity.setDate(row.getCell(2).getDateCellValue());
                productsEntity.setPrice(row.getCell(3).getNumericCellValue());
                list.add(productsEntity);
            }
        }

        return list;
    }


    public static File createExelFile(Map<Integer, Object[]> data) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Batch");


        // Iterate over data and write to sheet
        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key : keyset) {
            // this creates a new row in the sheet
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                // this line creates a cell in the next column of that row
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }

        File file = null;

        try {
            // this Writes the workbook gfgcontribute
            file = new File("Report(" + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ").xlsx");
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return file;
    }

}
