
package io.github.lxxbai.javaversionselector.datasource.entity;

import lombok.Data;

/**
 * 数据源结果对象
 *
 * @author lxxbai
 */
@Data
public class JavaVersionDO {

    /**
     * 版本
     */
    private String version;

    /**
     * 版本类型  oracle jdk or openjdk
     */
    private String versionType;

    /**
     * 主版本
     */
    private String mainVersion;

    /**
     * 详情版本
     */
    private String detailVersion;

    /**
     * 发布日期
     */
    private String releaseDate;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * windows 64位下载地址
     */
    private String x64WindowsDownloadUrl;
}