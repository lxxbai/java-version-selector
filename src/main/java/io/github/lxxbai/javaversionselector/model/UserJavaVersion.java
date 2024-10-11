
package io.github.lxxbai.javaversionselector.model;

import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
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
     * 已解压本地路径
     */
    private String zipLocalPath;

    /**
     * 已解压本地路径
     */
    private String unzipLocalPath;

    /**
     * 状态
     */
    private VersionStatusEnum status;

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

    /**
     * mac下载地址
     */
    private String downloadUrl;

    /**
     * 是否安装中
     */
    private Boolean installing = false;
}