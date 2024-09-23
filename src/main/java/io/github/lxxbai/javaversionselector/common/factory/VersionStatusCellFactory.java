package io.github.lxxbai.javaversionselector.common.factory;

import io.github.lxxbai.javaversionselector.common.enums.StatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DialogUtils;
import io.github.lxxbai.javaversionselector.component.DownloadProgress;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import io.github.lxxbai.javaversionselector.view.JavaVersionViewModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class VersionStatusCellFactory implements Callback<TableColumn<UserJavaVersion, String>, TableCell<UserJavaVersion, String>> {


    private static final Map<String, DownloadProgress> DOWNLOAD_PROGRESS_MAP = new ConcurrentHashMap<>();

    /**
     * 数据管理器
     */
    private final JavaVersionViewModel javaVersionViewModel;

    public VersionStatusCellFactory(JavaVersionViewModel javaVersionViewModel) {
        this.javaVersionViewModel = javaVersionViewModel;
    }


    @Override
    public TableCell<UserJavaVersion, String> call(TableColumn<UserJavaVersion, String> tableColumn) {

        return new TableCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                UserJavaVersion userJavaVersion = getTableView().getItems().get(getIndex());
                String version = userJavaVersion.getVersion();
                StatusEnum status = userJavaVersion.getStatus();
                switch (status) {
                    case INSTALLED_PAUSE -> {
                        DownloadProgress downloadProgress = DOWNLOAD_PROGRESS_MAP.get(version);
                        if (Objects.nonNull(downloadProgress)) {
                            setGraphic(downloadProgress.getContent());
                            //退出
                            downloadProgress.cancel();
                            return;
                        }
                        setText(status.getDesc());
                    }
                    case INSTALLING -> {
                        DownloadProgress downloadProgress = DOWNLOAD_PROGRESS_MAP.get(version);
                        if (Objects.nonNull(downloadProgress) && !downloadProgress.getTask().isCancelled()) {
                            setGraphic(downloadProgress.getContent());
                            return;
                        }
                        downloadProgress = buildDownloadProgress(userJavaVersion, getIndex());
                        DOWNLOAD_PROGRESS_MAP.put(version, downloadProgress);
                        setGraphic(downloadProgress.getContent());
                    }
                    default -> {
                        DOWNLOAD_PROGRESS_MAP.remove(version);
                        setText(status.getDesc());
                    }
                }
            }
        };
    }


    /**
     * 构建下载进度条
     *
     * @param userJavaVersion 版本信息
     * @param index           下标
     * @return 下载进度条
     */
    public DownloadProgress buildDownloadProgress(UserJavaVersion userJavaVersion, int index) {
        //获取下载地址
        String url = userJavaVersion.getX64WindowsDownloadUrl();
        DownloadProgress downloadProgress = new DownloadProgress(100, 15, url, "D:\\Java\\", "下载中");
        //失败
        downloadProgress.getTask().setOnFailed(event -> {
            DialogUtils.showErrorDialog("下载失败", "下载失败", "下载失败");
            javaVersionViewModel.downloadStatusChange(index, StatusEnum.INSTALLED_FAILURE);
        });
        //下载成功
        downloadProgress.getTask().setOnSucceeded(event -> {
            javaVersionViewModel.downloadStatusChange(index, StatusEnum.INSTALLED);
        });
        //当做暂停
        downloadProgress.getTask().setOnCancelled(event -> {
            javaVersionViewModel.downloadStatusChange(index, StatusEnum.INSTALLED_PAUSE);
        });
        // 启动下载任务
        downloadProgress.start();
        return downloadProgress;
    }
}
