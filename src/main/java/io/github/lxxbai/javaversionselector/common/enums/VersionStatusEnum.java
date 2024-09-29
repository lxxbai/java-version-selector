package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum VersionStatusEnum {

    NOT_INSTALLED("NOT_INSTALLED", "未安装"),
    //下载中
    DOWNLOADING("DOWNLOADING", "下载中"),
    //下载暂停
    DOWNLOAD_PAUSE("DOWNLOAD_PAUSE", "下载暂停"),
    //下载失败
    DOWNLOAD_FAILURE("DOWNLOAD_FAILURE", "下载失败"),
    //下载完成
    DOWNLOADED("DOWNLOADED", "已下载"),
    //解压中
    UNZIPPING("UNZIPPING", "解压中"),
    //解压失败
    UNZIPPING_FAILURE("UNZIPPING_FAILURE", "解压失败"),
    //解压完成
    UNZIPPED("UNZIPPED", "已解压"),
    //配置中
    CONFIGURING("CONFIGURING", "配置中"),
    //配置失败
    CONFIGURING_FAILURE("CONFIGURING_FAIL", "配置失败"),
    //配置成功
    CONFIGURED("CONFIGURED", "已配置"),
    //已安装
    INSTALLED("INSTALLED", "已安装"),
    //安装并使用当前版本
    CURRENT("CURRENT", "当前版本"),
    ;
    private final String status;
    private final String desc;
}
