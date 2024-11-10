package io.github.lxxbai.javaversionselector.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
@TableName("T_USER_CONFIG_INFO")
public class UserConfigInfoDO {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置key
     */
    private String dicKey;

    /**
     * 配置值
     */
    private String dicValue;

    /**
     * 创建时间
     */
    private String createdAt;
}
