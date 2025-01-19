package io.github.lxxbai.jvs.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
@TableName("T_USER_CONFIG_INFO")
public class UserConfigInfoDO extends BaseDO {

    /**
     * 配置key
     */
    private String dicKey;

    /**
     * 配置值
     */
    private String dicValue;
}
