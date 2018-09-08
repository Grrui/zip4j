package com.example.demo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

/**
 * 创建带密码的压缩文件
 *
 * @author Wang Erniu
 * @date 2018/9/8 19:00
 */
public class ZipFileWithPwd {
    private static void zipFile() throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile("D:\\test.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("111");
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
