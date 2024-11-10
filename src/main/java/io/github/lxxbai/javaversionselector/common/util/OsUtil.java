package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.system.OsInfo;
import io.github.lxxbai.javaversionselector.common.enums.OsEnum;

/**
 * @author lxxbai
 */
public class OsUtil {

    private static final OsInfo OS_INFO = new OsInfo();

    /**
     * 获取操作系统名称
     *
     * @return 操作系统名称
     */
    public static String getOsName() {
        if (OS_INFO.isWindows()) {
            return OsEnum.WINDOWS.getCode();
        } else if (OS_INFO.isLinux()) {
            return OsEnum.LINUX.getCode();
        } else if (OS_INFO.isMac()) {
            return OsEnum.MAC.getCode();
        } else {
            throw new RuntimeException("不支持的操作系统");
        }
    }

    /**
     * 获取操作系统名称
     *
     * @return 操作系统名称
     */
    public static OsEnum getOs() {
        if (OS_INFO.isWindows()) {
            return OsEnum.WINDOWS;
        } else if (OS_INFO.isLinux()) {
            return OsEnum.LINUX;
        } else if (OS_INFO.isMac()) {
            return OsEnum.MAC;
        } else {
            throw new RuntimeException("不支持的操作系统");
        }
    }

    public static boolean isWindows() {
        return OS_INFO.isWindows();
    }
}
