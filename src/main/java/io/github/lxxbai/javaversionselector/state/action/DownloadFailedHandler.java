package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DownloadUtil;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 安装事件处理器
 *
 * @author lxxbai
 */
@Component
public class DownloadFailedHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.FAILURE;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.DOWNLOADING};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.DOWNLOAD_FAILURE;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        //移除下载进度条
        DownloadUtil.remove(context.getVersion());
    }
}
