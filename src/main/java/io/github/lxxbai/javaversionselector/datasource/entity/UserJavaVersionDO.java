
package io.github.lxxbai.javaversionselector.datasource.entity;

import io.github.lxxbai.javaversionselector.common.enums.StatusEnum;
import lombok.Data;


/**
 * 用户拥有的Java版本
 *
 * @author lxxbai
 */
@Data
public class UserJavaVersionDO {

    /**
     * 版本
     */
    private String version;

    /**
     * 本地路径
     */
    private String localPath;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 是否是当前版本
     */
    private Boolean current;

}