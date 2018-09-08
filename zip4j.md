# Zip4j学习

之前开发过程中遇到一个需求：需要往压缩文件的某些目录下插入一些文件。之前使用了一些方案（例如ZipOutputStream），添加原理是新建一
个压缩文件，然后拷贝所有的文件，拷贝过程中添加新的文件进去，速度比较慢。后来发现了Zip4j，可以选择将文件只打包不压缩，然后可以特
别灵活的添加、删除文件。  

zip4j功能比较强大，支持加密、解密压缩，支持文件的添加、删除等，所以想好好学习一波。

## 1.Zip4j介绍
zip4j官网：http://www.lingala.net/zip4j/  可以在"download"页面下载官方示例进行学习。  
**特征：**  
1. 从Zip文件创建，添加，提取，更新，删除文件
2. 读/写密码保护的Zip文件
3. 支持AES 128/256加密
4. 支持标准邮​​编加密
5. 支持Zip64格式
6. 支持存储（无压缩）和Deflate压缩方法
7. 从Split Zip文件创建或提取文件（例如：z01，z02，... zip）
8. 支持Unicode文件名
9. 进度监视器

## 2.实例
这里只提供几个我用到的例子。
pom文件添加
```xml
<dependency>
    <groupId>net.lingala.zip4j</groupId>
    <artifactId>zip4j</artifactId>
    <version>1.3.2</version>
</dependency>
```
### 2.1. 压缩文件
```java
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import java.io.File;

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
```
**注释：**  
1. 压缩方式
  - COMP_STORE = 0;（仅打包，不压缩）
  - COMP_DEFLATE = 8;（默认） 
  - COMP_AES_ENC = 99; 加密压缩
2. 压缩级别
 - DEFLATE_LEVEL_FASTEST = 1; (速度最快，压缩比最小)
 - DEFLATE_LEVEL_FAST = 3; (速度快，压缩比小)
 - DEFLATE_LEVEL_NORMAL = 5;  (一般)
 - DEFLATE_LEVEL_MAXIMUM = 7; 
 - DEFLATE_LEVEL_ULTRA = 9;  


### 2.2.解压文件 
```java
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class UnZipFiles {

    private static void unzip() {
        try {
            ZipFile zipFile = new ZipFile("D:\\test.zip");
            zipFile.extractAll("D:\\test");
            // 如果解压需要密码
//            if(zipFile.isEncrypted()) {
//                zipFile.setPassword("111");
//            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        unzip();
    }
}
```

### 2.3.添加文件到压缩文件中
```java
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
//            zipFile.addFile(new File("D:\\addFile2.txt"),parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addFile();
    }
}
```
**注释：**  
发现一个问题，这种方式添加文件时，只能添加一次，再次添加会失败，没有发现原因。

### 2.4.以流的方式添加文件
```java
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
```
**注释：**  
这种方想比直接添加文件优势在于可以多次添加。
### 2.5.删除压缩文件中的文件
```java
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

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
```
**注释：**  
测试发现不能删除压缩文件中的文件夹

### 2.6.创建带密码的压缩文件
```java
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

public class ZipFileWithPwd {
    private static void zipFile() throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile("D:\\test.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles( true );
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
```

由于时间问题，并没有研究太多，只测试了几个基本操作。更多的例子，可以参考官网的例子。  
**参考文章：**  
https://blog.csdn.net/u011165335/article/details/50496930