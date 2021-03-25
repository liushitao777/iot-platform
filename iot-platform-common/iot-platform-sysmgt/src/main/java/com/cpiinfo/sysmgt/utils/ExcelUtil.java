/**
 * 
 */
package com.cpiinfo.sysmgt.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 
 * @Title: ExcelUnit.java
 * @Package com.cpiinfo.jyspsc.utils
 * @Description: TODO()
 * @author 程志宏
 * @date 2017年7月27日下午1:55:21
 * @version V1.0
 */
public class ExcelUtil {
	
	/** 
	* @Title: readExcelWithoutTitle 
	* @Description: TODO(导入Excel文件) 
	* @param @param filepath
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<List<List<String>>>    返回类型 
	* @throws 
	*/
	public static List<List<List<String>>> readExcelWithoutTitle(String filepath) throws Exception{
        InputStream is = null;  
        Workbook wb = null;  
        try {  
            is = new FileInputStream(filepath);  
            wb = WorkbookFactory.create(is);
              
            List<List<List<String>>> result = new ArrayList<List<List<String>>>();//对应excel文件  
              
            int sheetSize = wb.getNumberOfSheets();  
            for (int i = 0; i < sheetSize; i++) {//遍历sheet页  
                Sheet sheet = wb.getSheetAt(i);  
                List<List<String>> sheetList = new ArrayList<List<String>>();//对应sheet页  
                  
                int rowSize = sheet.getLastRowNum() + 1;  
                for (int j = 1; j < rowSize; j++) {//遍历行  
                    Row row = sheet.getRow(j);  
                    if (row == null) {//略过空行  
                        continue;  
                    }  
                    int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列  
                    List<String> rowList = new ArrayList<String>();//对应一个数据行  
                    for (int k = 0; k < cellSize; k++) {  
                        Cell cell = row.getCell(k);  
                        String value = null;  
                        if (cell != null) {  
                            value = cell.toString();
                            if(value.endsWith(".0")){
                            	value = value.replace(".0", "");
                            }
                        }  
                        rowList.add(value);  
                    }  
                    sheetList.add(rowList);  
                }  
                result.add(sheetList);  
            }  
              
            return result;  
        } catch (FileNotFoundException e) {  
            throw e;  
        } finally {  
            if (wb != null) {  
                wb.close();  
            }  
            if (is != null) {  
                is.close();  
            }  
        }  
    }
	
	/**
	 * 读取指定的excel内容，并按sheet和单元格名称返回
	 * @param excelContent
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static Map<String, Map<String, Object>> readExcelCells(InputStream excelContent) throws EncryptedDocumentException, InvalidFormatException, IOException{
		Map<String, Map<String, Object>> wbDatas = new LinkedHashMap<String, Map<String, Object>>();
		Workbook wb = WorkbookFactory.create(excelContent);
		DataFormatter formatter = new DataFormatter();
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		int sheetSize = wb.getNumberOfSheets();  
        for (int i = 0; i < sheetSize; i++) {//遍历sheet页  
            Sheet sheet = wb.getSheetAt(i);
            Map<String, Object> mapSheetData = new HashMap<String, Object>();
            wbDatas.put(sheet.getSheetName(), mapSheetData);
            int rowSize = sheet.getLastRowNum() + 1;  
            for (int j = 0; j < rowSize; j++) {//遍历行  
                Row row = sheet.getRow(j);  
                if (row == null) {//略过空行  
                    continue;  
                }  
                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列  
                for (int k = 0; k < cellSize; k++) {  
                    Cell cell = row.getCell(k);  
                    if(cell != null) {
                        String addr = cell.getAddress().formatAsString();
                        Object val = getCellValue(formatter, cell, formulaEvaluator);
                        mapSheetData.put(addr, val);
                    }
                }
            }  
        }  
		return wbDatas;
	}

	private static Object getCellValue(DataFormatter formatter, Cell cell, FormulaEvaluator formulaEvaluator) {
		Object val = null;
		switch(cell.getCellType()) {
		    case STRING:
		    	val = cell.getStringCellValue();
		    	break;
		    case NUMERIC:
		    	if(HSSFDateUtil.isCellDateFormatted(cell)) {
		    		val = cell.getDateCellValue();
		    	}
		    	else {
		    		double celVal = cell.getNumericCellValue();
		    		if(!Double.isInfinite(celVal) && !Double.isNaN(celVal)) {
		    			val = BigDecimal.valueOf(celVal);
		    		}
		    	}
		    	break;
		    case BOOLEAN:
		    	val = cell.getBooleanCellValue();
		    	break;
		    case FORMULA:
		    	try {
		    		if(HSSFDateUtil.isCellDateFormatted(cell)) {
		        		val = cell.getDateCellValue();
		        	}
		        	else {
		        		val = BigDecimal.valueOf(cell.getNumericCellValue());
		        	}
		    	}catch(Exception e) {
		    		try {
		    			val = formatter.formatCellValue(cell, formulaEvaluator);
		    		}
		    		catch(Exception ee) {
		    			val = cell.getCellFormula();
		    		}
		    	}
		    	break;
		    default:
		    	val = formatter.formatCellValue(cell); 
		}
		return val;
	}
	
	/** 
	* @Title: readExcelWithoutTitle 
	* @Description: TODO(导入Excel文件) 
	* @param @param filepath
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<List<List<String>>>    返回类型 
	* @throws 
	*/
	public static List<List<List<String>>> readExcelWithoutTitleByInputStream(InputStream is) throws Exception{  
        Workbook wb = null;  
        try {  
            wb = WorkbookFactory.create(is);
            List<List<List<String>>> result = new ArrayList<List<List<String>>>();//对应excel文件  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        	DataFormatter formatter = new DataFormatter();
        	FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            int sheetSize = wb.getNumberOfSheets();  
            for (int i = 0; i < sheetSize; i++) {//遍历sheet页  
                Sheet sheet = wb.getSheetAt(i);  
                List<List<String>> sheetList = new ArrayList<List<String>>();//对应sheet页  
                  
                int rowSize = sheet.getLastRowNum() + 1;  
                for (int j = 2; j < rowSize; j++) {//遍历行  
                    Row row = sheet.getRow(j);  
                    if (row == null) {//略过空行  
                        continue;  
                    }  
                    if(j == 2){
                    	System.out.println("===");
                    }
                    int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列  
                    List<String> rowList = new ArrayList<String>();//对应一个数据行  
                    for (int k = 0; k < cellSize; k++) { 
                    	Cell cell = row.getCell(k);  
                        String value = null;  
                        if (cell != null) {  
                        	boolean isMerge = isMergedRegion(sheet, j, cell.getColumnIndex());
                            //判断是否具有合并单元格
                            if(isMerge) {
                            	value = getMergedRegionValue(sheet, row.getRowNum(), cell.getColumnIndex());
                            }else {
                            	Object val = getCellValue(formatter, cell, formulaEvaluator);
                            	if(val instanceof Date) {
                            		value = sdf.format((Date)val);
                            	}
                            	else {
                            		value = val == null ? "" : val.toString();
                            	}
                            }
                            if(value != null && value.endsWith(".0")){
                            	value = value.replace(".0", "");
                            }
                        }  
                        rowList.add(value);  
                    }  
                    sheetList.add(rowList);  
                }  
                result.add(sheetList);  
            }  
              
            return result;  
        } catch (FileNotFoundException e) {  
            throw e;  
        } finally {  
            if (wb != null) {  
                wb.close();  
            }  
            if (is != null) {  
                is.close();  
            }  
        }  
    }

    public static Map<String,List<List<String>>> readExcelInputStream(InputStream is) throws Exception{
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
            Map<String,List<List<String>>> result = new LinkedHashMap<String,List<List<String>>>();//对应excel文件
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DataFormatter formatter = new DataFormatter();
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            int sheetSize = wb.getNumberOfSheets();
            for (int i = 0; i < sheetSize; i++) {//遍历sheet页
                Sheet sheet = wb.getSheetAt(i);
                List<List<String>> sheetList = new ArrayList<List<String>>();//对应sheet页
                int rowSize = sheet.getLastRowNum() + 1;
                for (int j = 2; j < rowSize; j++) {//遍历行
                    Row row = sheet.getRow(j);
                    if (row == null) {//略过空行
                        continue;
                    }
                    if(j == 2){
                        System.out.println("===");
                    }
                    int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                    List<String> rowList = new ArrayList<String>();//对应一个数据行
                    for (int k = 0; k < cellSize; k++) {
                        Cell cell = row.getCell(k);
                        String value = null;
                        if (cell != null) {
                            boolean isMerge = isMergedRegion(sheet, j, cell.getColumnIndex());
                            //判断是否具有合并单元格
                            if(isMerge) {
                                value = getMergedRegionValue(sheet, row.getRowNum(), cell.getColumnIndex());
                            }else {
                                Object val = getCellValue(formatter, cell, formulaEvaluator);
                                if(val instanceof Date) {
                                    value = sdf.format((Date)val);
                                }
                                else {
                                    value = val == null ? "" : val.toString();
                                }
                            }
                            if(value != null && value.endsWith(".0")){
                                value = value.replace(".0", "");
                            }
                        }
                        rowList.add(value);
                    }
                    sheetList.add(rowList);
                }
                result.put( sheet.getSheetName(),sheetList);
            }

            return result;
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (wb != null) {
                wb.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }



	
	/**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private static boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return fCell.toString();
                }
            }
        }

        return null ;
    }
}
