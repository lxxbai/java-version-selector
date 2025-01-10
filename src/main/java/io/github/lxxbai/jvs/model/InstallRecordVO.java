package io.github.lxxbai.jvs.model;

import io.github.lxxbai.jvs.common.enums.InstallStatusEnum;
import io.github.lxxbai.jvs.component.DownloadProgressBar;
import io.github.lxxbai.jvs.component.XxbDownloadBar;
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
     * DOWNLOAD_FILE_FOLDERS
     * 下载文件存放文件夹
     */
    private String downloadFileFolder;

    /**
     * JDK_PACKAGE_URL
     * jdk安装包地址
     */
    private String jdkPackageUrl;

    /**
     * 安装文件夹
     */
    private String installedFolder;

    /**
     * INSTALLED_JAVA_HOME
     * jdk安装成功后的home路径
     */
    private String installedJavaHome;

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

    private XxbDownloadBar xxbDownloadBar;

    private Runnable installTask;

    private String ukInstallCode;

    public String getUkInstallCode() {
        return String.valueOf(id);
    }

    public boolean isDownloading() {
        return installStatus == InstallStatusEnum.DOWNLOADING || installStatus == InstallStatusEnum.DOWNLOAD_PAUSE;
    }
}
