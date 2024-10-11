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
    //取消
    CANCEL("CANCEL", "取消安装"),
    //失败
    FAILURE("FAILURE", "失败"),
    //成功
    SUCCESS("SUCCESS", "成功"),
    //完成
    COMPLETE("COMPLETE", "完成"),
    //解压
    UNZIP("UNZIP", "解压"),
    //配置
    CONFIG("CONFIG", "配置"),
    //应用
    APPLY("APPLY", "应用"),
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
