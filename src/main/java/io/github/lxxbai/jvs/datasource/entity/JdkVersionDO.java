
package io.github.lxxbai.jvs.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * java版本信息
 *
 * @author lxxbai
 */
@TableName("T_JDK_VERSION")
@Data
public class JdkVersionDO extends VersionBaseDO {

    /**
     * 发布日期
     */
    private String releaseDate;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 是否可直接下载
     */
    private boolean canDownload;

    /**
     * 下载/跳转下载地址
     */
    private String downloadUrl;
}