package io.github.lxxbai.jvs.common.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.jvs.common.model.JdkInfo;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 判断是否是JDK目录
     *
     * @param homePath 目录
     * @return 是否是JDK目录
     */
    public static Boolean checkJdk(File homePath) {
        try { // 执行命令并捕获输出
            String result = RuntimeUtil.execForStr(homePath + "\\bin\\java", "-version");
            return StrUtil.contains(result, "java version");
        } catch (Exception e) {
            return false;
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
                return checkJdk(dir);
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


    /**
     * 使用 RuntimeUtil 执行 java -XshowSettings:properties -version 命令
     * 并将结果解析为 Map<String, String>
     *
     * @param homePath java home 路径
     */
    public static Map<String, String> getJvmProperties(String homePath) {
        // 执行命令并捕获输出
        String result = RuntimeUtil.execForStr(homePath + "\\bin\\java", "-XshowSettings:properties", "-version");
        // 创建一个 Map 来存储键值对
        Map<String, String> propertiesMap = new HashMap<>();
        // 分行处理输出，并解析为 key = value 格式
        String[] lines = result.split("\n");
        for (String line : lines) {
            // 判断是否包含 "="，即 key = value 格式
            if (line.contains("=")) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    propertiesMap.put(key, value);
                }
            }
        }
        return propertiesMap;
    }

    /**
     * 将给定的 java home 路径转换为 JdkInfo 对象
     *
     * @param homePath java home 路径
     * @return JdkInfo
     */
    public static JdkInfo toJavaInfo(String homePath) {
        Map<String, String> jvmProperties = getJvmProperties(homePath);
        //获取版本信息
        String javaVersion = jvmProperties.get("java.version");
        String vendor = jvmProperties.get("java.vm.vendor");
        Integer mainVersionInt = MapUtil.getInt(jvmProperties, "java.class.version");
        String mainVersion = Objects.isNull(mainVersionInt) ? "未知" : "JDK" + (mainVersionInt - 44);
        JdkInfo jdkInfo = new JdkInfo();
        jdkInfo.setVmVendor(vendor);
        jdkInfo.setMainVersion(mainVersion);
        jdkInfo.setJavaVersion(javaVersion);
        jdkInfo.setLocalHomePath(homePath);
        return jdkInfo;
    }
}
