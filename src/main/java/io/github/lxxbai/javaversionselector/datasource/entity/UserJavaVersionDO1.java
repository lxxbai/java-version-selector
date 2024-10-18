
package io.github.lxxbai.javaversionselector.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 用户拥有的Java版本
 *
 * @author lxxbai
 */
@Data
@TableName("T_USER_JAVA_VERSION")
public class UserJavaVersionDO1 extends VersionBaseDO {

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
    @TableField(exist = false)
    private Boolean current;

    /**
     * 来源 : local or platform
     */
    private String source;
}