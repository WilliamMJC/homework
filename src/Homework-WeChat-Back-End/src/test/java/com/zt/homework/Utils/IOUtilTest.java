package com.zt.homework.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IOUtilTest {
    @Value("${homeDir}")
    private String homeDir;

    @Test
    public void createFile() {
        String path = "test.txt";
        if ((!IOUtil.createFile(path))) throw new AssertionError();
    }

    @Test
    public void deleteFile() {
        String path = "hehehehehhehe.txt";
        if ((!IOUtil.deleteFile(path))) throw new AssertionError();
    }

    @Test
    public void createFolder() {
        String path = "test";
        if ((!IOUtil.createFolder(path))) throw new AssertionError();
    }

    @Test
    public void isDir() {
        String path = "test";
        if ((!IOUtil.isDir(path))) throw new AssertionError();
    }

    @Test
    public void getFileList() {
        File[] files = IOUtil.getFileList("");
        assertSame(5, files.length);
    }

    @Test
    public void storeFile() throws FileNotFoundException {
        String path = homeDir + "haha.txt";
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        if ((!IOUtil.storeFile("4" + File.separator + "haha.txt", in))) throw new AssertionError();
    }

    @Test
    public void file2Zip() {
        String path = "haha.txt";
        String zipPath = "haha.zip";
        if ((!IOUtil.file2Zip(path, zipPath))) throw new AssertionError();
    }

    @Test
    public void folder2Zip() {
        String path = "\\48\\4";
        String zipPath = "\\48\\4.zip";        if ((!IOUtil.folder2Zip(path, zipPath))) throw new AssertionError();
    }

    @Test
    public void getFileSize() {
        String path = "haha.txt";
        long size = IOUtil.getFileSize(path);
        System.out.println(size);
        assertEquals(43, size);
    }

    @Test
    public void getFolderSize() {
        String path = "4";
        long size = IOUtil.getFolderSize(path);
        System.out.println(size);
        assertEquals(12,982, size);
    }

    @Test
    public void fileSizeFormat() {
        String path = "4";
        long size = IOUtil.getFolderSize(path);
        System.out.println(size);
        assertEquals("12.68K", IOUtil.fileSizeFormat(size));
    }

    @Test
    public void readTxt() throws IOException {
        File file = new File(homeDir + "新建文本文档.txt");
        InputStream in = new FileInputStream(file);
        List<String> lines = IOUtil.readTxt(in);
        for(String line : lines) {
            System.out.println(line);
        }
        assertEquals(9, lines.size());
    }
}