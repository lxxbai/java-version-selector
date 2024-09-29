package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * @author lxxbai
 */
@Component
public class ReInstallHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.REINSTALL;
    }

    @Override
    public VersionStatusEnum[] froms() {
        //失败的都可以点击安装
        return new VersionStatusEnum[]{VersionStatusEnum.DOWNLOAD_FAILURE,
                VersionStatusEnum.UNZIPPING_FAILURE, VersionStatusEnum.CONFIGURING_FAILURE};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.DOWNLOADING;
    }

    @Override
    public boolean isSatisfied(VersionContext context) {
        return true;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
    }
}
