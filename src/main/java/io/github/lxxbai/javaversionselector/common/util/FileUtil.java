package io.github.lxxbai.javaversionselector.common.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 获取用户的默认下载文件夹路径。
     *
     * @return 默认下载文件夹的路径，如果无法确定则返回null。
     */
    public static Path getDefaultDownloadPath() {
        // 获取用户主目录
        String userHome = System.getProperty("user.home");
        if (userHome == null || userHome.isEmpty()) {
            return null;
        }
        return Paths.get(userHome, "Downloads");
    }
}
