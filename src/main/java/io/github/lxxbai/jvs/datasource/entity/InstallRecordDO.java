package io.github.lxxbai.jvs.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
@TableName("T_INSTALL_RECORD")
public class InstallRecordDO extends VersionBaseDO {

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
     * 下载文件存放文件夹
     */
    private String downloadFileFolder;

    /**
     * jdk安装包地址
     */
    private String jdkPackageUrl;

    /**
     * 安装文件夹
     */
    private String installedFolder;

    /**
     * jdk安装成功后的home路径
     */
    private String installedJavaHome;

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
}
