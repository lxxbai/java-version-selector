
package io.github.lxxbai.javaversionselector.datasource.entity;

import lombok.Data;


/**
 * 用户拥有的Java版本
 *
 * @author lxxbai
 */
@Data
public class UserJavaVersionDO1 {

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
     * 发布日期
     */
    private String releaseDate;

    /**
     * 本地java_home路径
     */
    private String localHomePath;

    /**
     * 状态 INSTALLED,CURRENT
     */
    private String status;

    /**
     * 是否是当前版本
     */
    private Boolean current;

    /**
     * 来源 : local or platform
     */
    private String source;
}