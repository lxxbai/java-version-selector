package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum1 {


    //状态 未安装->安装中(下载中，解压中，配置中)->已安装
    //动作->下载->解压->配置->完成


    NOT_INSTALLED("NOT_INSTALLED", "未安装"),
    DOWNLOADING("DOWNLOADING", "下载中"),
    DOWNLOADED("DOWNLOADED", "已下载"),
    UNZIPPING("UNZIPPING", "解压中"),
    CONFIGURING("CONFIGURING", "配置中"),
    INSTALLING("INSTALLING", "正在安装"),
    INSTALLED_FAILURE("INSTALLED_FAILURE", "安装失败"),
    INSTALLED_PAUSE("INSTALLED_FAILURE", "安装暂停"),
    INSTALLED("INSTALLED", "已安装"),
    UNINSTALLING("UNINSTALLING", "正在卸载"),
    CURRENT("CURRENT", "当前版本"),
    ;
    private final String status;
    private final String desc;
}
