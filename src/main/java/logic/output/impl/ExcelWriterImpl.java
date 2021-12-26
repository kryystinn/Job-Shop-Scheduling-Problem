package logic.output.impl;

import logic.instances.Instance;
import logic.output.Writer;
import logic.schedule.rules.Rule;
import logic.schedule.rules.impl.SPTRule;
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
    public void writeMatrix(String path, String name, String sheetName, List<String[]> data) {

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
    public void writeAllSameSheet(String path, String name, String instName, int rowNum, int colNum, Instance instance,
                                  long result, boolean extended, String objFunc) {


        File file = null;
        OutputStream fos = null;
        XSSFWorkbook workbook = null;
        Sheet sheet = null;

        File out = new File(String.valueOf(Paths.get(path + "\\out")));

        try {

            if (!out.exists()){
                out.mkdir();
            }

            fileName = out + "\\" + name + " " + objFunc + ".xlsx";

            file = new File(fileName);

            String sheetName = "";
            if (objFunc.equals("m")) {
                sheetName = "makespan";
            } else if (objFunc.equals("t")){
                sheetName = "tardiness";
            }

            if (file.exists()) {
                workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));

            } else {
                workbook = new XSSFWorkbook();

                sheet = workbook.createSheet(sheetName);

                Row headers = sheet.createRow(0);

                CellStyle cStyle = workbook.createCellStyle();
                cStyle.setWrapText(true);
                cStyle.setAlignment(HorizontalAlignment.CENTER);
                cStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                headers.createCell(1).setCellValue("n x m");
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
                headers.createCell(2).setCellValue("SPT");
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
                headers.createCell(3).setCellValue("LPT");
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
                headers.createCell(4).setCellValue("MCM");

                if (objFunc.equals("t")) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
                    headers.createCell(5).setCellValue("EDD");
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 9));
                    Row atcValues = sheet.createRow(1);
                    headers.createCell(6).setCellValue("ATC");
                    atcValues.createCell(6).setCellValue(0.25);
                    atcValues.createCell(7).setCellValue(0.5);
                    atcValues.createCell(8).setCellValue(0.75);
                    atcValues.createCell(9).setCellValue(1);
                }

                if (instance.getLowerBound() > 0 && objFunc.equals("m")) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
                    headers.createCell(5).setCellValue("Lower Bound");
                }

                for (Cell c: headers) {
                    c.setCellStyle(cStyle);
                }

            }

            sheet = workbook.getSheet(sheetName);

            Row inst = sheet.getRow(rowNum);
            if (inst == null) {
                inst = sheet.createRow(rowNum);
                inst.createCell(0).setCellValue(instName);
                inst.createCell(1).setCellValue(instance.getnJobs() + "x" + instance.getnMachines());
            }

            inst.createCell(colNum).setCellValue(result);

            if (instance.getLowerBound() > 0 && objFunc.equals("m")) {
                inst.createCell(5).setCellValue(instance.getLowerBound());
            }

            sheet.autoSizeColumn(0);

            fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
            }

}
