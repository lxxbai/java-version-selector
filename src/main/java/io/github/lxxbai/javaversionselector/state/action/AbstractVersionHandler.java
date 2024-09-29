package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.state.StateMachineHandler;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;

/**
 * @author lxxbai
 */
public abstract class AbstractVersionHandler implements StateMachineHandler<VersionStatusEnum, VersionActionEnum, VersionContext> {

}
