package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.state.StateMachineHandler;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;

/**
 * @author lxxbai
 */
public abstract class AbstractVersionHandler implements StateMachineHandler<VersionStatusEnum, VersionActionEnum, VersionContext> {

    @Resource
    protected ApplicationContext applicationContext;


    @Override
    public boolean isSatisfied(VersionContext context) {
        return true;
    }

}
