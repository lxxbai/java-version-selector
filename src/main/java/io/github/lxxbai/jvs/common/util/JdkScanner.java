package io.github.lxxbai.jvs.common.util;

import io.github.lxxbai.jvs.common.exception.ClientException;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lxxbai
 */
public class JdkScanner {


    /**
     * 扫描指定目录及其子目录，查找所有JDK安装。
     *
     * @param rootDir 根目录
     * @return 包含所有JDK安装路径的列表
     */
    public List<File> scanForJdks(File rootDir) {
        if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
            throw new ClientException("文件夹不存在: " + rootDir);
        }
        List<File> jdkPaths = new ArrayList<>();
        scanDirectory(rootDir, jdkPaths);
        return jdkPaths;
    }

    /**
     * 递归扫描目录，查找JDK安装。
     *
     * @param dir      当前目录
     * @param jdkPaths 存储找到的JDK路径的列表
     */
    private void scanDirectory(File dir, List<File> jdkPaths) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (isJdk(file)) {
                        jdkPaths.add(file);
                    } else {
                        scanDirectory(file, jdkPaths);
                    }
                }
            }
        }
    }

    /**
     * 检查给定的目录是否是一个JDK安装。
     *
     * @param dir 目录
     * @return 如果是JDK安装，则返回true；否则返回false
     */
    public static boolean isJdk(File dir) {
        if (!dir.isDirectory()) {
            return false;
        }
        // 检查是否存在 bin 和 lib 目录
        File binDir = new File(dir, "bin");
        File libDir = new File(dir, "lib");
        if (binDir.exists() && binDir.isDirectory() && libDir.exists() && libDir.isDirectory()) {
            // 检查 bin 目录下是否存在 java 和 javac 可执行文件
            File javaExec = new File(binDir, OsUtil.isWindows() ? "java.exe" : "java");
            File javacExec = new File(binDir, OsUtil.isWindows() ? "javac.exe" : "javac");
            boolean b = javaExec.exists() && javaExec.isFile() && javacExec.exists() && javacExec.isFile();
            if (b) {
                // 检查是否是JDK
                return JdkUtil.isJdk(dir);
            }
        }
        return false;
    }

    /**
     * 检查给定的目录是否是一个JDK安装。
     *
     * @param path 目录
     * @return 如果是JDK安装，则返回true；否则返回false
     */
    public static boolean isJdk(Path path) {
        return isJdk(path.toFile());
    }
}