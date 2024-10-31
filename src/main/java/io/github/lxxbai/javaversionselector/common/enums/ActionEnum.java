package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum ActionEnum {

    DOWNLOAD("DOWNLOAD", "下载"),
    //取消
    DOWNLOAD_CANCEL("INSTALL_CANCEL", "取消下载"),
    //失败
    DOWNLOAD_FAILURE("INSTALL_FAILURE", "下载失败"),
    //成功
    DOWNLOAD_SUCCESS("INSTALL_SUCCESS", "下载成功"),

    //应用
    APPLY("APPLY", "应用"),
    //继续安装
    CONTINUE_INSTALL("CONTINUE_INSTALL", "继续安装"),
    //卸载
    UNINSTALL("UNINSTALL", "卸载"),
    ;
    private final String status;
    private final String desc;
}
