package io.github.lxxbai.jvs.datasource.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.lxxbai.jvs.datasource.config.StrDateTypeHandler;
import lombok.Data;

import java.util.Date;

/**
 * @author lxxbai
 */
@Data
public class BaseDO {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_AT", fill = FieldFill.INSERT, typeHandler = StrDateTypeHandler.class)
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_AT", fill = FieldFill.INSERT_UPDATE, typeHandler = StrDateTypeHandler.class)
    private Date updatedAt;
}
