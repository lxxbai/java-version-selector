package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 卸载事件处理器
 *
 * @author lxxbai
 */
@Component
public class UnInstallSuccessHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.SUCCESS;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.UNINSTALLING};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.NOT_INSTALLED;
    }

    @Override
    public boolean isSatisfied(VersionContext context) {
        return true;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
    }
}
