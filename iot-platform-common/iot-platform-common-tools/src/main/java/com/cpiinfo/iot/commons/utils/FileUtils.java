package com.cpiinfo.iot.commons.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 转换上传文件流为文件流
     * @param mfile
     * @param suffix
     * @return
     */
    public static File convertUploadFileStream(MultipartFile mfile, StringBuffer suffix){
        String fileName = mfile.getOriginalFilename();
        File file = new File(fileName);
        OutputStream out = null;
        try {
            // 获取文件流，以文件流的方式输出到新文件
            //InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(file);
            byte[] ss = mfile.getBytes();
            for (byte s : ss) {
                out.write(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        suffix.append(fileName.substring(fileName.lastIndexOf(".") + 1));
        LOGGER.info("covert to file"+file.getAbsolutePath());
        return file;
    }

    public static File convertUploadFileStream(MultipartFile mfile, String fileName, StringBuffer suffix){
        fileName = fileName + File.separator + mfile.getResource().getFilename();
        File file = new File(fileName);
        OutputStream out = null;
        try {
            // 获取文件流，以文件流的方式输出到新文件
            //InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(file);
            byte[] ss = mfile.getBytes();
            for (byte s : ss) {
                out.write(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        suffix.append(fileName.substring(fileName.lastIndexOf(".") + 1));
        LOGGER.info("covert to file"+file.getAbsolutePath());
        return file;
    }

    /**
     * 过滤转换文件流为数据集合
     * @param file
     * @param suffix
     * @return
     */
    public static List<String> filterFormatDataList(File file, String suffix){
        LOGGER.info("filter Format DataList");
        List<String> dataList;
        try {
            if("csv".equals(suffix)){
                if(isUtf_8Encode(file))
                    dataList = CSVUtils.importCsv(file,"UTF-8");
                else
                    dataList = CSVUtils.importCsv(file,"GBK");
            }else if ("xls".equals(suffix))
                dataList = ExcelUtils.importExcelXls(file);
            else if ("xlsx".equals(suffix))
                dataList = ExcelUtils.importExcelXlsx(file);
            else
                dataList = new ArrayList<>();
        } catch (Exception e) {
            dataList = new ArrayList<>();
        }

        file.delete();//删除临时文件
        return dataList;
    }

    /**
     * 判断文件格式是否为UTF-8
     * @param file
     * @return
     */
    public static boolean isUtf_8Encode(File file){
        boolean flag = false;
        try {
            InputStream inputStream= new FileInputStream(file);
            byte[] b = new byte[3];
            inputStream.read(b);
            inputStream.close();
            if (b[0] == -17 && b[1] == -69 && b[2] == -65){//判断是否是utf-8编码
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 文件下载
     * @param response
     * @param fileName
     * @param path
     */
    public static void downLoad(HttpServletResponse response, String fileName, String path) {
        try {
            response.setContentType("multipart/form-data;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            InputStream inputStream = new FileInputStream(new File(path + fileName));
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    public static boolean isFile(String filepath) {
        File f = new File(filepath);
        return f.exists() && f.isFile();
    }

    public static boolean isDir(String dirPath) {
        File f = new File(dirPath);
        return f.exists() && f.isDirectory();
    }

    /**
     * 创建多级目录
     * @param path
     */
    public static void makeDirs(String path) {
        File file = new File(path);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }else {
            System.out.println("创建目录失败："+path);
        }
    }

    /**
     * 文件或目录是否存在
     */
    public static boolean exists(String path) {
        return new File(path).exists();
    }

    @SneakyThrows
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (NoSuchElementException e){
            throw new IOException("excel文件不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return list;
    }
}
