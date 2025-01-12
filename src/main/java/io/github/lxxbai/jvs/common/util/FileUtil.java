package io.github.lxxbai.jvs.common.util;

import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.jvs.common.exception.ClientException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lxxbai
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {


    /**
     * 获取用户的默认下载文件夹路径。
     *
     * @return 默认下载文件夹的路径，如果无法确定则返回null。
     */
    public static Path getDefaultPath() {
        // 获取用户主目录
        String userDir = System.getProperty("user.dir");
        if (StrUtil.isBlank(userDir)) {
            throw new ClientException("无法获取用户主目录");
        }
        return Paths.get(userDir).getParent().resolve("Jvs");
    }
}
