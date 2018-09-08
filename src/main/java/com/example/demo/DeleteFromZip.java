package com.example.demo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * 删除压缩文件中的文件
 *
 * @author Wang Erniu
 * @date 2018/9/8 19:00
 */
public class DeleteFromZip {

    public static void deleteFile() {
        try {
            ZipFile zipFile = new ZipFile("D:\\test.zip");
            zipFile.removeFile("ks/add");
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        deleteFile();
    }
}
