
package io.github.lxxbai.javaversionselector.datasource.entity;

import cn.hutool.crypto.SecureUtil;
import lombok.Data;

/**
 * java版本信息
 *
 * @author lxxbai
 */
@Data
public class JavaVersionDO1 {

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
     * 发布日期
     */
    private String releaseDate;

    /**
     * 是否可直接下载
     */
    private boolean canDownload;

    /**
     * 下载/跳转下载地址
     */
    private String downloadUrl;


    private String ukVersion;


    public String getUkVersion() {
        // md5(vendor + version)
        return SecureUtil.md5(vmVendor + javaVersion);
    }
}