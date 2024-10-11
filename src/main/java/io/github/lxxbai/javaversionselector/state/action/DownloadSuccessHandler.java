package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 安装事件处理器
 *
 * @author lxxbai
 */
@Component
public class DownloadSuccessHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.SUCCESS;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.DOWNLOADING};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.DOWNLOADED;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        //执行完后,发送事件,自动执行解压
        context.setNextAction(VersionActionEnum.UNZIP);
    }
}
