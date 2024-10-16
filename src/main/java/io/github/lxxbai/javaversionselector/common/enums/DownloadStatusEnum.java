
package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum DownloadStatusEnum {
    //未下载
    NO_DOWNLOADING("NO_DOWNLOADING", "未下载"),
    //下载中
    DOWNLOADING("DOWNLOADING", "下载中"),
    //下载失败
    DOWNLOAD_FAILURE("DOWNLOAD_FAILURE", "下载失败"),
    //下载完成
    DOWNLOADED("DOWNLOADED", "已下载"),
    ;
    private final String status;
    private final String desc;


    /**
     * 通过状态获取枚举
     *
     * @param status 状态
     * @return 下载状态
     */
    public static DownloadStatusEnum getByStatus(String status) {
        for (DownloadStatusEnum downloadStatusEnum : DownloadStatusEnum.values()) {
            if (downloadStatusEnum.getStatus().equals(status)) {
                return downloadStatusEnum;
            }
        }
        return NO_DOWNLOADING;
    }
}
