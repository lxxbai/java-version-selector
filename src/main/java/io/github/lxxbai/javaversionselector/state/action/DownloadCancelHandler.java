package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DownloadUtil;
import io.github.lxxbai.javaversionselector.component.fx.DownloadProgress;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 下载取消处理器
 *
 * @author lxxbai
 */
@Component
public class DownloadCancelHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.CANCEL;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.DOWNLOADING};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.NOT_INSTALLED;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        //取消下载
        DownloadProgress downloadProgress = DownloadUtil.get(context.getVersion());
        if (Objects.nonNull(downloadProgress)) {
            downloadProgress.cancel();
        }
        //移除下载进度条
        DownloadUtil.remove(context.getVersion());
    }
}
