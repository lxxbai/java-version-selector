package io.github.lxxbai.jvs.common.model;

import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class JdkInfo {

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
     * 本地JDK home地址
     */
    private String localHomePath;
}
