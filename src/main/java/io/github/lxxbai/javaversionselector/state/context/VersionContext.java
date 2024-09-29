package io.github.lxxbai.javaversionselector.state.context;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class VersionContext {

    /**
     * 当前状态
     */
    private VersionStatusEnum versionStatus;

    private Integer index;

    /**
     * 业务数据
     */
    private UserJavaVersion userJavaVersion;

    /**
     * 动作
     */
    private VersionActionEnum versionAction;
}
