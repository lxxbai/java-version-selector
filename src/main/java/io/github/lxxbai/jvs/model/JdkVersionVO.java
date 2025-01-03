package io.github.lxxbai.jvs.model;

import io.github.lxxbai.jvs.common.enums.InstallStatusEnum;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class JdkVersionVO {

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
     * 发布日期
     */
    private String releaseDate;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 是否可直接下载
     */
    private boolean canDownload;

    /**
     * 下载/跳转下载地址
     */
    private String downloadUrl;

    /**
     * 下载状态
     */
    private InstallStatusEnum installStatus;
}
