
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
     * 发布日期
     */
    private String releaseDate;
    /**
     * windows 64位下载地址
     */
    private String x64WindowsDownloadUrl;

    /**
     * windows 32位下载地址
     */
    private String x32WindowsDownloadUrl;

    /**
     * mac下载地址
     */
    private String macDownloadUrl;
}