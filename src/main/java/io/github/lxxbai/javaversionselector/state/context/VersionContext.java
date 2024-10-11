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

    private String version;

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

    /**
     * 由于无后置处理, 所以需要添加一个下一步动作用于判断是否需要主动触发下一个action
     */
    private VersionActionEnum nextAction;
}
