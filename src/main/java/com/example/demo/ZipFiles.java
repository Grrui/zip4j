package com.example.demo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

/**
 * 压缩文件夹
 *
 * @author Wang Erniu
 * @date 2018/9/8 18:34
 */
public class ZipFiles {

    private static void zipFile() throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile("D:\\test.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        // 要打包的文件夹
        File currentFile = new File("D:\\test");
        File[] fs = currentFile.listFiles();
        // 遍历test文件夹下所有的文件、文件夹
        for (File f : fs) {
            if (f.isDirectory()) {
                zipFile.addFolder(f.getPath(), parameters);
            } else {
                zipFile.addFile(f, parameters);
            }
        }
    }

    public static void main(String[] args) throws ZipException {
        zipFile();
    }
}