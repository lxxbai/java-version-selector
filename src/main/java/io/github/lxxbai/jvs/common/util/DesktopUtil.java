package io.github.lxxbai.jvs.common.util;

import cn.hutool.core.util.RuntimeUtil;
import io.github.lxxbai.jvs.common.enums.OsEnum;
import io.github.lxxbai.jvs.common.exception.ClientException;

import java.io.File;

public class DesktopUtil {


    /**
     * 替代方案：根据操作系统选择合适的命令打开文件夹并选中文件
     *
     * @param file 文件
     */
    public static void openFileDirectory(File file) {
        OsEnum os = OsUtil.getOs();
        switch (os) {
            case WINDOWS -> RuntimeUtil.execForStr("explorer.exe /select," + file.getAbsolutePath());
            case MAC -> RuntimeUtil.execForStr("open -R " + file.getAbsolutePath());
            case LINUX -> RuntimeUtil.execForStr("xdg-open " + file.getParent());
            default -> throw new ClientException("不支持的操作系统");
        }
    }
}
