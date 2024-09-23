
package io.github.lxxbai.javaversionselector.model;

import io.github.lxxbai.javaversionselector.common.enums.StatusEnum;
import lombok.Data;


/**
 * Java版本
 *
 * @author lxxbai
 */
@Data
public class UserJavaVersion {

    /**
     * 版本
     */
    private String version;

    /**
     * 本地路径
     */
    private String localPath;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 是否是当前版本
     */
    private Boolean current;

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