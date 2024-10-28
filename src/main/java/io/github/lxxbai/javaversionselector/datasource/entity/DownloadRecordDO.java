package io.github.lxxbai.javaversionselector.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
@TableName("T_DOWNLOAD_RECORD")
public class DownloadRecordDO extends VersionBaseDO {

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
     * 下载状态
     */
    private String downloadStatus;

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
     * 下载的进度
     */
    private Double downloadProcess;

    /**
     * 创建时间
     */
    private String createdAt;
}
