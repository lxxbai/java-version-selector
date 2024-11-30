package io.github.lxxbai.javaversionselector.model;

import io.github.lxxbai.javaversionselector.common.enums.ApplyStatusEnum;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class UserJdkVersionVO {


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
     * 状态
     */
    private ApplyStatusEnum status;

    /**
     * 唯一版本号
     */
    private String ukVersion;

    /**
     * 本地JDK home地址
     */
    private String localHomePath;


    private String ukCode;

    public String getUkCode() {
        return ukVersion + localHomePath;
    }
}
