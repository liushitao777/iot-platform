

package com.cpiinfo.iot.commons.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Excel工具类
 *
 * @author liwenjie
 * @date 2020-05-11
 */
public class ExcelUtils {


    public static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 导入xls格式的Excel
     *
     * @param file Excel文件(路径+文件名)
     * @return
     */
    public static List<String> importExcelXls(File file) {
        List<String> dataList = new ArrayList<>();
        HSSFWorkbook workbook = null;
        InputStream inputStream = null;
        try {
            // 读取Excel文件
            inputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(inputStream);
            // 循环工作表
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet sheet = workbook.getSheetAt(numSheet);
                if (sheet == null)
                    continue;
                // 循环行
                for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    HSSFRow row = sheet.getRow(rowNum);
                    if (row == null)
                        continue;
                    // 循环单元格
                    StringBuffer rowSBuff = new StringBuffer();
                    int index = 1;
                    int lastCellNum = row.getLastCellNum();
                    for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
                        HSSFCell cell = row.getCell(cellNum);
                        String value = getCellValueByCell(cell);
                        if (StringUtils.isBlank(value)) {
                            index++;
                        }
						/*if (cell == null)
							continue;*/
                        rowSBuff.append(getCellValueByCell(cell) + "^");
                    }
                    if (index >= lastCellNum) {
                        continue;
                    }
                    String str = rowSBuff.toString();
                    dataList.add(str.substring(0, str.length() - 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }

    /**
     * 导入xlsx格式的Excel
     *
     * @param file Excel文件(路径+文件名)
     * @return
     */
    public static List<String> importExcelXlsx(File file) {
        List<String> dataList = new ArrayList<>();
        XSSFWorkbook workbook = null;
        InputStream inputStream = null;
        try {
            // 读取Excel文件
            inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            // 循环工作表
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet sheet = workbook.getSheetAt(numSheet);
                if (sheet == null)
                    continue;
                // 循环行
                for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    XSSFRow row = sheet.getRow(rowNum);
                    if (!isRowNull(row)) // 单独判断行第一列是否为空
                        continue;
                    // 循环单元格
                    StringBuffer rowSBuff = new StringBuffer();
                    int index = 1;
                    int lastCellNum =  row.getLastCellNum();
                    for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
                        XSSFCell cell = row.getCell(cellNum);
                        String value =getCellValueByCell(cell);
						/*if (cell == null)
							continue;*/
                        rowSBuff.append(value + "^");
                    }
                    if (index >= lastCellNum) {
                        continue;
                    }
                    String str = rowSBuff.toString();
                    dataList.add(str.substring(0, str.length() - 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }

    private static boolean isRowNull(XSSFRow row) {
        if(row == null)
            return false;
        XSSFCell cell = row.getCell(0);
        if(StringUtils.isEmpty(getCellValueByCell(cell)))
            return false;
        return true;
    }

    /**
     * 获取单元格内容
     *
     * @param cell
     * @return
     */
    private static String getCellValueByCell(Cell cell) {
        String cellValue = "";
        // 判断是否为null或空串
        if (cell == null || cell.toString().trim().equals(""))
            return cellValue;
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING://Cell.CELL_TYPE_STRING: // 字符串类型
                cellValue = cell.getStringCellValue().trim();
                cellValue = (cellValue == null || "".equals(cellValue)) ? "" : cellValue;
                break;
            case BOOLEAN://Cell.CELL_TYPE_BOOLEAN: // 布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC://Cell.CELL_TYPE_NUMERIC: // 数值类型
                cellValue = new DecimalFormat("#.###############").format(cell.getNumericCellValue());
                break;
            case FORMULA://Cell.CELL_TYPE_FORMULA: // 表达式类型
                cellValue = cell.getNumericCellValue() + "";
                break;
            case ERROR://Cell.CELL_TYPE_ERROR: // 异常类型
                cellValue = "";
                break;
            case BLANK://Cell.CELL_TYPE_BLANK: // 空类型
                cellValue = "";
                break;
            default: // 默认取空字符串
                cellValue = "";
                break;
        }
        return cellValue;
    }

    /**
     * 导出xls格式Excel
     *
     * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据源
     * @return
     */
    public static boolean exportXlsExcel(File file, List<String> dataList) {
        boolean flag = false;
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        // 添加数据内容
        if (dataList != null && !dataList.isEmpty()) {
            // 循环行
            for (int i = 0; i < dataList.size(); i++) {
                HSSFRow row = sheet.createRow(i);
                String data = dataList.get(i);
                String[] datas = data.split("\\^");
                // 循环单元格
                for (int j = 0; j < datas.length; j++) {
                    String str = datas[j];
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(str);
                }
            }
        }
        // 写Excel文件
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                    workbook = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 导出xlsx格式Excel
     *
     * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据源
     * @return
     */
    public static boolean exportXlsxExcel(File file, List<String> dataList) {
        boolean flag = false;
        // 创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        // 添加数据内容
        if (dataList != null && !dataList.isEmpty()) {
            // 循环行
            for (int i = 0; i < dataList.size(); i++) {
                XSSFRow row = sheet.createRow(i);
                String data = dataList.get(i);
                String[] datas = data.split("^");
                // 循环单元格
                for (int j = 0; j < datas.length; j++) {
                    String str = datas[j];
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(str);
                }
            }
        }
        // 写Excel文件
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                    workbook = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * Excel导出
     *
     * @param response      response
     * @param fileName      文件名
     * @param list          数据List
     * @param pojoClass     对象Class
     */
    public static void exportExcel(HttpServletResponse response, String fileName, Collection<?> list,
                                   Class<?> pojoClass) throws IOException {
        if(StringUtils.isBlank(fileName)){
            //当前日期
            fileName = DateUtils.format(new Date());
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, list);
        Sheet sheet1 = workbook.getSheetAt(0);
        sheet1.setDefaultColumnWidth(50*256);
        sheet1.setDefaultRowHeight((short)(2*256));
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    /**
     * Excel导出，先sourceList转换成List<targetClass>，再导出
     *
     * @param response      response
     * @param fileName      文件名
     * @param sourceList    原数据List
     * @param targetClass   目标对象Class
     */
    public static void exportExcelToTarget(HttpServletResponse response, String fileName, Collection<?> sourceList,
                                           Class<?> targetClass) throws Exception {
        List<Object> targetList = new ArrayList<>(sourceList.size());
        for(Object source : sourceList){
            Object target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        }

        exportExcel(response, fileName, targetList, targetClass);
    }
}
