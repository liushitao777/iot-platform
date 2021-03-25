package com.cpiinfo.iot.commons.utils;

import com.cpiinfo.iot.commons.exception.IOTException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author ShuPF
 * @Date 2020-09-02 17:43
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 上传文件
     * @param filePath 路径
     * @param multipartFiles 文件流
     * @return 地址集合
     */
    public static List<String> upImg(String filePath, MultipartFile[] multipartFiles) {
        return upImg(filePath, null, multipartFiles);
    }

    /**
     * 上传文件
     * @param filePath 路径
     * @param multipartFiles 文件流
     * @param suffix 后缀
     * @return 地址集合
     */
    public static List<String> upImg(String filePath,String suffix, MultipartFile[] multipartFiles) {
        if(multipartFiles!=null&&multipartFiles.length>0) {
            // 文件目录 + node_id +步骤
            //String filePath = baseKnoeledgePath + File.separator + nodeId+File.separator+step;
            // 判断目录是否存在，不存在则创建
            createDir(filePath);

            List<String> paths = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                File file = null;

                String fileName = "";
                fileName = multipartFile.getOriginalFilename();
                if (StringUtils.isNotBlank(suffix)) {
                    fileName = fileName.substring(0, fileName.indexOf(".")) + (suffix.contains(".") ? suffix : ("." + suffix));
                }
                file = new File(filePath + File.separator + fileName);
                try {
                    multipartFile.transferTo(file);
                } catch (IOException e) {
                    LOGGER.error("BaseKnowledgeDetailServiceImpl insertNodeDetails is error", "上传文件异常" + e.getMessage());
                    throw new IOTException("上传文件异常" + e.getMessage());
                }

                paths.add(filePath + File.separator + fileName);
                //存文件表
            }

            return paths;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 删除文件
     * @param url 地址
     * @return 成功与否
     */
    public static boolean deleteFiles(String url) {
        boolean res = false;

        if(url!=null&&!url.equals("")) {
            File file = new File(url);
            if (file.exists()) {
                res = file.delete();
            }
            //删除表中的记录
        }

        return res;
    }

    /**
     * ZIP解压
     * @param zipFile 压缩文件
     * @param outDir 解压存放地址
     * @throws IOException
     */
    public static void unZip(File zipFile, String outDir) throws IOException {
        createDir(outDir);

        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration enumeration = zip.entries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);

            if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir + zipEntryName);
                fileDir.mkdir();
                continue;
            }

            File file = new File(outDir, zipEntryName);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 采用命令行方式解压文件
     *  附文：Linux 安装WinRAR
     *        1. wget https://www.rarlab.com/rar/rarlinux-x64-5.9.1.tar.gz
     *        2. tar -zxf rarlinux-x64-5.9.1.tar.gz
     *        3. cd rar目录
     *        4. make
     *        5. make install
     *        安装完成unrar,安装默认路径是/usr/local/bin
     *        里面有rar 和 unrar可执行文件  /usr/local/bin/unrar，路径写这个路径即可
     * @param zipFile 压缩文件
     * @param destDir 解压结果路径
     * @param cmdPath WinRAR.exe 安装路径
     * @return
     */
    public static boolean unRAR(File zipFile, String destDir,String cmdPath) {
        createDir(destDir);

        // 解决路径中存在/..格式的路径问题
        destDir = new File(destDir).getAbsoluteFile().getAbsolutePath();
        while(destDir.contains("..")) {
            String[] sepList = destDir.split("\\\\");
            destDir = "";
            for (int i = 0; i < sepList.length; i++) {
                if(!"..".equals(sepList[i]) && i < sepList.length -1 && "..".equals(sepList[i+1])) {
                    i++;
                } else {
                    destDir += sepList[i] + File.separator;
                }
            }
        }

        boolean bool = false;
        if (!zipFile.exists()) {
            return false;
        }

        // 开始调用命令行解压，参数-o+是表示覆盖的意思
        //cmdPath = "E:\\workspace\\operation-uprr-manage\\src\\main\\resources\\cmd\\WinRAR.exe";
        String cmd = cmdPath + " X -o+ " + zipFile + " " + destDir;
        LOGGER.info(cmd);
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            if (proc.waitFor() != 0) {
                if (proc.exitValue() == 0) {
                    bool = false;
                }
            } else {
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("解压" + (bool ? "成功" : "失败"));
        return bool;
    }

    /**
     *  获取路径目录下的所有文件名
     * @param path 文件路径
     * @return 所有的文件名
     */
    public static List<String> getFileNames(String path) {
        List<String> list = new ArrayList<>();

        File file = new File(path);
        File[] fs = file.listFiles();
        if (fs == null || fs.length < 1) {
            return null;
        }

        for(File f:fs){
            if(!f.isDirectory())	{
                System.out.println(path + File.separator + f.getName());
                list.add(f.getName());

            }
        }

        return list;
    }

    private static void createDir(String outPath) {
        File outFileDir = new File(outPath);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }
    }

    public static void main(String[] args) {
        //getFileNames("C:\\Users\\Administrator\\Desktop\\photo");
    }

}
