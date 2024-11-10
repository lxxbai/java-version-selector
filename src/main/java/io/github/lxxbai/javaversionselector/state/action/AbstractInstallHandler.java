package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.ActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.state.StateMachineHandler;
import io.github.lxxbai.javaversionselector.state.context.InstallContext;

/**
 * @author lxxbai
 */
public abstract class AbstractInstallHandler implements StateMachineHandler<InstallStatusEnum, ActionEnum, InstallContext> {

    @Override
    public boolean isSatisfied(InstallContext context) {
        return true;
    }
}
