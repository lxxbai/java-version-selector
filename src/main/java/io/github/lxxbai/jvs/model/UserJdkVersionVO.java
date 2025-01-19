package io.github.lxxbai.jvs.model;

import io.github.lxxbai.jvs.common.enums.ApplyStatusEnum;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class UserJdkVersionVO {


    private Integer id;


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

    /**
     * 是否是新的jdk
     */
    private boolean isNewJdk;

}
