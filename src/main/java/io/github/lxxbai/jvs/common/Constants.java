package io.github.lxxbai.jvs.common;


import cn.hutool.system.oshi.OshiUtil;
import io.github.lxxbai.jvs.common.util.OsUtil;

/**
 * 常量
 *
 * @author lxxbai
 */
public class Constants {

    public static final String LOCAL_APP_DATA = "LOCALAPPDATA";

    public static final String APP_NAME = "JavaVersionSelector";
    //版本列表地址
    public static final String OS_VERSIONS_URL = "https://gitee.com/shupeng.liao/static-resources/raw/master/java-version-selector/%s/%s/versions.json";

    public static final String VERSION_DATA_URL = buildDataUrl();

    public static final String STR_JAVA_HOME = "JAVA_HOME";

    public static final String DOWNLOAD_CONFIG_KEY = "DOWNLOAD_CONFIG";

    /**
     * 构建并返回操作系统版本的URL
     * 此方法用于生成一个特定于当前操作系统的URL，包含操作系统名称和位数信息
     * 通过调用OsUtil.getOsName()获取操作系统名称，并使用OshiUtil.getOs().getBitness()获取操作系统的位数
     * 然后将这些信息格式化为一个URL字符串
     *
     * @return 操作系统版本的URL字符串
     */
    public static String buildDataUrl() {
        // 获取当前操作系统,获取位数
        return OS_VERSIONS_URL.formatted(OsUtil.getOsName(), "x" + OshiUtil.getOs().getBitness());
    }

}
