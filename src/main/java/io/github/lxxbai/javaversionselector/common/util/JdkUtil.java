package io.github.lxxbai.javaversionselector.common.util;

import java.io.File;

/**
 * @author lxxbai
 */
public class JdkUtil {


    /**
     * 获取解压后的路径
     *
     * @param zipFile zip文件
     * @return jdk Home地址
     */
    public static String getJavaHomePath(File zipFile) {
        if (zipFile.isDirectory()) {
            //判断是不是JDK目录
            Boolean isHome = isJdkHome(zipFile);
            if (isHome) {
                return zipFile.getPath();
            }
            File[] files = zipFile.listFiles();
            if (files != null && files.length == 1) {
                return getJavaHomePath(files[0]);
            }
            return null;
        }
        return null;
    }


    /**
     * 判断是否是JDK目录
     *
     * @param directory 目录
     * @return 是否是JDK目录
     */
    public static Boolean isJdkHome(File directory) {
        if (!directory.isDirectory()) {
            return false;
        }
        // 检查关键子目录
        File binDir = new File(directory, "bin");
        File libDir = new File(directory, "lib");
        File includeDir = new File(directory, "include");
        return binDir.exists() && libDir.exists() && includeDir.exists();
    }
}
