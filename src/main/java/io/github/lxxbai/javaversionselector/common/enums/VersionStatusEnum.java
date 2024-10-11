package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum VersionStatusEnum {

    NOT_INSTALLED("NOT_INSTALLED", "未安装", null),
    //下载中
    DOWNLOADING("DOWNLOADING", "下载中", NOT_INSTALLED),
    //下载失败
    DOWNLOAD_FAILURE("DOWNLOAD_FAILURE", "下载失败", null),
    //下载完成
    DOWNLOADED("DOWNLOADED", "已下载", null),
    //解压中
    UNZIPPING("UNZIPPING", "解压中", DOWNLOADED),
    //解压失败
    UNZIPPING_FAILURE("UNZIPPING_FAILURE", "解压失败", null),
    //解压完成
    UNZIPPED("UNZIPPED", "已解压", null),
    //配置中
    CONFIGURING("CONFIGURING", "配置中", UNZIPPED),
    //配置失败
    CONFIGURING_FAILURE("CONFIGURING_FAIL", "配置失败", null),
    //配置成功
    CONFIGURED("CONFIGURED", "已配置", null),
    //已安装
    INSTALLED("INSTALLED", "已安装", null),
    //应用中
    APPLYING("APPLYING", "应用中", INSTALLED),
    //安装并使用当前版本
    CURRENT("CURRENT", "当前版本", null),
    //卸载中
    UNINSTALLING("UNINSTALLING", "卸载中", INSTALLED),
    ;
    private final String status;
    private final String desc;
    //回滚状态,只有处理中的状态才有
    private final VersionStatusEnum rollbackStatus;
}
