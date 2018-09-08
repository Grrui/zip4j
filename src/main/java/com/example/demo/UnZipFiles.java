package com.example.demo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * 解压到指定文件夹
 *
 * @author Wang Erniu
 * @date 2018/9/8 18:34
 */
public class UnZipFiles {

    private static void unzip() {
        try {
            ZipFile zipFile = new ZipFile("D:\\test.zip");
            // 如果解压需要密码
//            if(zipFile.isEncrypted()) {
//                zipFile.setPassword("111");
//            }
            zipFile.extractAll("D:\\test");
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        unzip();
    }
}
