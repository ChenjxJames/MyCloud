package com.chenxiaoyu.cloud.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author ：
 * @date ：Created in 2020/6/18 4:37
 * @description：
 */
public class FileUtils {

    public static String getMd5(MultipartFile file) throws Exception {
        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean delete(String filePath) {
        File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    public static String upload(MultipartFile file, String fileName) throws Exception {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upload\\" + fileName;
        java.io.File file2 = new java.io.File(path);
        if (!file2.getParentFile().exists()) {
            boolean createFile = file2.getParentFile().mkdirs();
            if (!createFile) {
                System.out.println("创建失败");
            }
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file2));
        out.write(file.getBytes());
        out.flush();
        out.close();
        return path;
    }
}
