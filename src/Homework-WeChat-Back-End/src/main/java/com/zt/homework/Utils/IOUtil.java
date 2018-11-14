package com.zt.homework.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class IOUtil {

    private static String homeDir;

    @Value("${homeDir}")
    public void setHomeDir(String s) {
        homeDir = s;
    }


    /**
     * 创建一个新文件
     * @param path
     * @return 是否创建成功
     */
    public static boolean createFile(String path) {
        System.out.println(homeDir);
        File file = new File(homeDir + path);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个文件
     * @param path
     * @return 文件是否被删除
     */
    public static boolean deleteFile(String path) {
        File file = new File(homeDir + path);
        return file.exists() && file.delete();
    }

    /**
     * 创建一个文件夹
     * @param path
     * @return 文件夹是否创建成功
     */
    public static boolean createFolder(String path) {
        File file = new File(homeDir + path);
        return file.mkdir();
    }

    /**
     * 判断一个指定的路径是否是文件夹
     * @param path
     * @return boolean
     */
    public static boolean isDir(String path) {
        File file = new File(homeDir + path);
        return file.isDirectory();
    }

    /**
     * 列出指定目录的全部文件
     * @param path
     * @return File[]
     */
    public static File[] getFileList(String path) {
        File file = new File(homeDir + path);
        return file.listFiles();
    }

    /**
     * 存储文件
     * @param path
     * @param in
     * @return 文件是否存储成功
     */
    public static boolean storeFile(String path, InputStream in) {
        BufferedInputStream bis;
        BufferedOutputStream bos;
        try {
            bis = new BufferedInputStream(in);
            bos = new BufferedOutputStream(new FileOutputStream(homeDir + path));
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
            bos.close();
            bis.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 压缩文件
     * @param path
     * @param zipPath
     * @return 文件是否压缩成功
     */
    public static boolean file2Zip(String path, String zipPath) {
        File file = new File(homeDir + path);
        File zipFile = new File(homeDir + zipPath);
        try {
            InputStream in = new FileInputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            zipOut.setComment("hello");
            int temp = 0;
            while ((temp = in.read()) != -1) {
                zipOut.write(temp);
            }
            in.close();;
            zipOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 压缩文件夹
     * @param path
     * @param zipPath
     * @return 压缩是否成功
     */
    public static boolean folder2Zip(String path, String zipPath) {
        File file = new File(homeDir + path);
        File zipFile = new File(homeDir + zipPath);
        InputStream in = null;
        try {
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.setComment("hello");
            if(file.isDirectory()) {
                File[] files = file.listFiles();
                for(int i =0; i < files.length; i++) {
                    in = new FileInputStream(files[i]);
                    zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
                    int temp = 0;
                    while ((temp = in.read()) != -1)
                        zipOut.write(temp);
                }
                if (in != null) {
                    in.close();
                }
            }
            zipOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得文件大小
     * @param path
     * @return long
     */
    public static long getFileSize(String path) {
        File file = new File(homeDir + path);
        return file.length();
    }

    /**
     * 获得文件夹大小
     * @param path
     * @return long
     */
    public static long getFolderSize(String path) {
        long size = 0;
        File file = new File(homeDir + path);
        File[] files = file.listFiles();
        for(int i = 0, len = files.length; i < len; i++) {
            if(files[i].isDirectory()) {
                size = size + getFileSize(path + File.separator + files[i].getName());
            } else {
                size = size + files[i].length();
            }
        }
        return size;
    }

    public static String fileSizeFormat(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) +"G";
        }
        return fileSizeString;
    }

    /**
     * 读取txt文件
     * @param in
     * @return 由每一行文本组成的string数组
     * @throws IOException
     */
    public static List<String> readTxt(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        List<String> lines = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }

        return lines;
    }

    public static Boolean isFolderEmpty(String path) {
        File file = new File(homeDir + path);
        return file.listFiles().length == 0;
    }
}
