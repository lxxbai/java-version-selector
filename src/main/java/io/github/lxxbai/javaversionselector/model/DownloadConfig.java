package io.github.lxxbai.javaversionselector.model;

import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class DownloadConfig {

    /**
     * jdk保存地址
     */
    private String jdkSavePath;

    /**
     * 并行下载数量
     */
    private Integer parallelDownloads;

    /**
     * jdk安装地址
     */
    private String jdkInstallPath;

    /**
     * 设置为默认开启
     */
    private boolean defaultConfigured;
}
