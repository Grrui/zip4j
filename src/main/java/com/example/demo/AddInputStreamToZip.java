package com.example.demo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流的方式添加文件
 *
 * @author Wang Erniu
 * @date 2018/9/8 18:43
 */
public class AddInputStreamToZip {

    public static void addFile(){
        InputStream is = null;
        try {
            ZipFile zip = new ZipFile("D:\\test.zip");
            ZipParameters para = new ZipParameters();
            para.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            para.setFileNameInZip("ks/add.txt");
            para.setSourceExternalStream(true);
            is = new ByteArrayInputStream(new String("这是文件内容").getBytes());
            zip.addStream(is, para);
        } catch (ZipException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        addFile();
    }
}
