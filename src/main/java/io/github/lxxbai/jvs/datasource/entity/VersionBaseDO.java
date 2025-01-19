package io.github.lxxbai.jvs.datasource.entity;

import cn.hutool.crypto.SecureUtil;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class VersionBaseDO extends BaseDO {

    /**
     * 供应商  oracle jdk or openjdk or other
     */
    private String vmVendor;


    /**
     * 主版本
     */
    private String mainVersion;


    /**
     * 版本
     */
    private String javaVersion;

    /**
     * 唯一版本号
     */
    private String ukVersion;

    /**
     * 获取唯一版本号
     *
     * @return 唯一版本号
     */
    public String getUkVersion() {
        if (ukVersion != null) {
            return ukVersion;
        }
        // md5(vendor + version)
        return SecureUtil.md5(vmVendor + javaVersion);
    }
}
