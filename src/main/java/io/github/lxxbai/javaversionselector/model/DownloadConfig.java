package io.github.lxxbai.javaversionselector.model;

import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class DownloadConfig {

    /**
     * 下载路径
     */
    private String downloadPath;
    /**
     * 并行下载数量
     */
    private Integer parallelDownloads;

    /**
     * jdk home路径
     */
    private String jdkPathUrl;
}
