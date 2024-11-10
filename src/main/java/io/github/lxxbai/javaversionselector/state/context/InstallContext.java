package io.github.lxxbai.javaversionselector.state.context;

import io.github.lxxbai.javaversionselector.common.enums.ActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class InstallContext {

    private Integer id;

    /**
     * 当前状态
     */
    private InstallStatusEnum installStatus;

    /**
     * 动作
     */
    private ActionEnum versionAction;

    /**
     * 由于无后置处理, 所以需要添加一个下一步动作用于判断是否需要主动触发下一个action
     */
    private ActionEnum nextAction;
}
