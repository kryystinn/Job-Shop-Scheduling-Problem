package logic.output.impl;

import logic.output.Writer;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

public class ExcelWriterImpl implements Writer {

    String fileName;


    @Override
    public void write(String path, String name, String sheetName, List<String[]> data) {

        File file = null;
        OutputStream fos = null;
        XSSFWorkbook workbook = null;
        File out = new File(String.valueOf(Paths.get(path + "\\out")));

        try {

            if (!out.exists()){
                out.mkdir();
            }

            fileName = out + "\\" + name + ".xlsx";

            file = new File(fileName);

            if (file.exists()) {
                workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.createSheet(sheetName);

            int rowNum = 0;
            for(String[] job: data) {
                Row row = sheet.createRow(rowNum++);
                int i = 0;
                for(String str: job){
                    row.createCell(i).setCellValue(str);
                    i++;
                }
            }

            fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeAllSameSheet(String path, String name, String instName, int rowNum, int result, int rule,
                                  boolean extended) {

        File file = null;
        OutputStream fos = null;
        XSSFWorkbook workbook = null;
        Sheet sheet = null;

        File out = new File(String.valueOf(Paths.get(path + "\\out")));

        try {

            if (!out.exists()){
                out.mkdir();
            }

            fileName = out + "\\" + name + ".xlsx";

            file = new File(fileName);

            if (file.exists()) {
                workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));

            } else {
                workbook = new XSSFWorkbook();

                sheet = workbook.createSheet("makespan");

                Row headers = sheet.createRow(0);

                CellStyle cStyle = workbook.createCellStyle();
                cStyle.setWrapText(true);
                cStyle.setAlignment(HorizontalAlignment.CENTER);
                cStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                headers.createCell(1).setCellValue("SPT");
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
                headers.createCell(2).setCellValue("LPT");
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
                headers.createCell(3).setCellValue("MCM");

                if (extended) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
                    headers.createCell(4).setCellValue("EDD");
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 9));
                    Row atcValues = sheet.createRow(1);
                    headers.createCell(5).setCellValue("ATC");
                    atcValues.createCell(5).setCellValue(0.25);
                    atcValues.createCell(6).setCellValue(0.5);
                    atcValues.createCell(7).setCellValue(0.75);
                    atcValues.createCell(8).setCellValue(1);
                }

                for (Cell c: headers) {
                    c.setCellStyle(cStyle);
                }

            }

            Row inst = sheet.createRow(rowNum);
            inst.createCell(0).setCellValue(instName);

            int colNum;
            switch (rule) {
                case 1:
                    colNum = 1;
                    break;

                case 2:
                    colNum = 2;
                    break;

                case 3:
                    colNum = 3;
                    break;

                case 4:
                    colNum = 4;
                    break;

                case 5:
                    colNum = 5;
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + rule);
            }
            inst.createCell(colNum).setCellValue(result);

            fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
            }








}
