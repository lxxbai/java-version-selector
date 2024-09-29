package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum VersionActionEnum {

    INSTALL("INSTALL", "安装"),
    //暂停下载
    DOWNLOAD_PAUSE("DOWNLOAD_PAUSE", "暂停下载"),
    //下载失败
    DOWNLOAD_FAILURE("DOWNLOAD_FAILURE", "下载失败"),
    //下载完成
    DOWNLOADED("DOWNLOADED", "下载完成"),
    //继续下载
    CONTINUE_DOWNLOAD("CONTINUE_DOWNLOAD", "继续下载"),
    //取消下载
    CANCEL_DOWNLOAD("CANCEL_DOWNLOAD", "取消下载"),
    //解压
    UNZIP("UNZIP", "解压"),
    //配置
    CONFIG("CONFIG", "配置"),
    //继续安装
    CONTINUE_INSTALL("CONTINUE_INSTALL", "继续安装"),
    //重新安装
    REINSTALL("REINSTALL", "重新安装"),
    //卸载
    UNINSTALL("UNINSTALL", "卸载"),
    ;
    private final String status;
    private final String desc;
}
