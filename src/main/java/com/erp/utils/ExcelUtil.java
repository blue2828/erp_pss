package com.erp.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ExcelUtil {
    @Autowired
    private StringUtil stringUtil;
    public HSSFWorkbook getExcelWb (String sheetName, String tbName, int[] columnWidth, String[] cellTitleName, List<?> data) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet hs = wb.createSheet(sheetName);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        hs.addMergedRegion(new CellRangeAddress(0, 0, 0, cellTitleName.length - 1));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        for (int i = 0; i < columnWidth.length; i ++)
            hs.setColumnWidth(i, columnWidth[i] * 255);
        String[] fieldName = stringUtil.getFieldName(data.get(0));
        for (int i = 0; i < data.size() + 2; i ++) {
            HSSFRow tempRow = hs.createRow(i);
            switch (i) {
                case 0 :
                    HSSFCell tb0cell = tempRow.createCell(0);
                    tb0cell.setCellValue(tbName);
                    tb0cell.setCellStyle(cellStyle);
                    break;
                case 1 :
                    cellStyle = wb.createCellStyle();
                    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    for (int j = 0; j < cellTitleName.length; j ++) {
                        HSSFCell cell = tempRow.createCell(j);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(cellTitleName[j]);
                    }
                    break;
                default :
                    for (int k = 0; k < fieldName.length; k ++) {
                        HSSFCell cell = tempRow.createCell(k);
                        cell.setCellStyle(cellStyle);
                        Object val = stringUtil.getFieldValueByName(fieldName[k], data.get(i - 2));
                        cell.setCellValue(val instanceof String ? (String) val : val instanceof Integer ? (String) val : val instanceof Date ?
                                stringUtil.formatTime((Date) val, "yyyy-MM-dd HH:mm:ss")  : "");
                    }
            }
        }
        return wb;
    }
}
