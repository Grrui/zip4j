package com.example.demo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import java.io.File;
import java.util.ArrayList;

/**
 * 添加文件到压缩文件中
 *
 * @author Wang Erniu
 * @date 2018/9/8 18:35
 */
public class AddFileToZip {

    public static void addFile() {
        try {
            ZipFile zipFile = new ZipFile("D:\\test.zip");
            ArrayList<File> addFiles = new ArrayList<>();
            addFiles.add(new File("D:\\addFile1.txt"));
            addFiles.add(new File("D:\\addFile2.txt"));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            // 目标路径
            parameters.setRootFolderInZip("ks/");
            zipFile.addFiles(addFiles, parameters);
            // 可以添加单个文件
//            zipFile.addFile(new File("D:\\addFile2.txt"),parameters);\
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addFile();
    }
}
