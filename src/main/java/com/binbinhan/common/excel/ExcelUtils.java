package com.binbinhan.common.excel;

import org.apache.poi.ss.usermodel.Row;

/**
 * Excel简单工具包
 *
 * @作者 binbinhan
 * @时间 2020-05-07 21:56
 */
public class ExcelUtils {


    /**
     * 上传文件校验
     * @param importExcel
     * @param sheetNames
     * @param headNames
     * @param rowNum
     * @return
     */
    public static String analysisImportFile(ImportExcel importExcel, String sheetNames, String headNames, Integer rowNum) {
        Row headRow = importExcel.getRow(1);
        String rowName = "";
        for (int i = 0; i < rowNum; i++) {
            rowName += importExcel.getCellValue(headRow, i) + ",";
        }
        String sheetName = headRow.getSheet().getSheetName();
        if (!sheetNames.equals(sheetName)) {
            return "上传文件错误,请按照要求编辑Excel";
        }
        if (!headNames.equals(rowName)) {
            return "请按照模板要求编辑Excel";
        }
        return "";
    }

}
