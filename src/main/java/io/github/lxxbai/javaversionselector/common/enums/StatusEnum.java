package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    NOT_INSTALLED("NOT_INSTALLED", "未安装"),
    INSTALLED("INSTALLED", "已安装"),
    DOWNLOADING("DOWNLOADING", "下载中"),
    DOWNLOADED("DOWNLOADED", "已下载"),
    UNZIPPING("UNZIPPING", "解压中"),
    CONFIGURING("CONFIGURING", "配置中"),
    INSTALLING("INSTALLING", "正在安装"),
    INSTALLED_FAILURE("INSTALLED_FAILURE", "安装失败"),
    INSTALLED_PAUSE("INSTALLED_FAILURE", "安装暂停"),
    UNINSTALLING("UNINSTALLING", "正在卸载"),
    CURRENT("CURRENT", "当前版本"),
    ;
    private final String status;
    private final String desc;
}
