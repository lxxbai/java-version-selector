package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DialogUtils;
import io.github.lxxbai.javaversionselector.common.util.DownloadUtil;
import io.github.lxxbai.javaversionselector.component.fx.DownloadProgress;
import io.github.lxxbai.javaversionselector.event.StatusChangeEvent;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 安装事件处理器
 *
 * @author lxxbai
 */
@Component
public class InstallHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.INSTALL;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.NOT_INSTALLED};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.DOWNLOADING;
    }


    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        //构建下载进度条
        UserJavaVersion userJavaVersion = context.getUserJavaVersion();
        DownloadProgress downloadProgress = buildDownloadProgress(userJavaVersion);
        //设置本地路径
        userJavaVersion.setLocalPath(downloadProgress.getTask().getLocalPath());
        userJavaVersion.setInstalling(true);
        //放入缓存
        DownloadUtil.put(userJavaVersion.getVersion(), downloadProgress);
    }


    /**
     * 构建下载进度条
     *
     * @param userJavaVersion 版本信息
     * @return 下载进度条
     */
    private DownloadProgress buildDownloadProgress(UserJavaVersion userJavaVersion) {
        //获取下载地址
        String url = userJavaVersion.getX64WindowsDownloadUrl();
        DownloadProgress downloadProgress = new DownloadProgress(100, 15, url, "D:\\Java\\", "下载中");
        String version = userJavaVersion.getVersion();
        //失败
        downloadProgress.getTask().setOnFailed(event -> {
            DialogUtils.showErrorDialog("下载失败", "下载失败", "下载失败");
            applicationContext.publishEvent(new StatusChangeEvent(version, VersionActionEnum.FAILURE));
        });
        //下载成功
        downloadProgress.getTask().setOnSucceeded(event ->
                applicationContext.publishEvent(new StatusChangeEvent(version, VersionActionEnum.SUCCESS)));
        //当做暂停
        downloadProgress.getTask().setOnCancelled(event ->
                applicationContext.publishEvent(new StatusChangeEvent(version, VersionActionEnum.CANCEL)));
        return downloadProgress;
    }
}
