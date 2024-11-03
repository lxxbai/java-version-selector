
package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 安装状态枚举
 *
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum InstallStatusEnum {
    //未下载
    NO_DOWNLOADED("NO_DOWNLOADED", "未下载"),
    //下载中
    DOWNLOADING("DOWNLOADING", "下载中"),
    //下载失败
    DOWNLOAD_FAILURE("DOWNLOAD_FAILURE", "下载失败"),
    //下载暂停
    DOWNLOAD_PAUSE("DOWNLOAD_PAUSE", "下载暂停"),
    //下载完成
    DOWNLOADED("DOWNLOADED", "已下载"),
    //安装中
    INSTALLING("INSTALLING", "安装中"),
    //安装中
    INSTALLED_FAILURE("INSTALLED_FAILURE", "安装失败"),
    //安装完成
    INSTALLED("INSTALLED", "安装完成"),
    ;
    private final String status;
    private final String desc;


    /**
     * 通过状态获取枚举
     *
     * @param status 状态
     * @return 下载状态
     */
    public static InstallStatusEnum getByStatus(String status) {
        for (InstallStatusEnum downloadStatusEnum : InstallStatusEnum.values()) {
            if (downloadStatusEnum.getStatus().equals(status)) {
                return downloadStatusEnum;
            }
        }
        return NO_DOWNLOADED;
    }
}
