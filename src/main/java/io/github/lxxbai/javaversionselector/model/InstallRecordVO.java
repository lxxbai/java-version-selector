package io.github.lxxbai.javaversionselector.model;

import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.component.DownloadProgressBar;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class InstallRecordVO {


    private Integer id;

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
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 下载/跳转下载地址
     */
    private String downloadUrl;

    /**
     * 下载文件地址
     */
    private String downloadFileUrl;

    /**
     * jdk地址
     */
    private String jdkPathUrl;

    /**
     * 安装状态
     */
    private InstallStatusEnum installStatus;

    /**
     * 下载的进度
     */
    private Double downloadProgress;

    /**
     * 创建时间
     */
    private String createdAt;


    /**
     * 下载完成时间
     */
    private String downloadEndAt;


    private DownloadProgressBar downloadProgressBar;
}
