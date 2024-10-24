package io.github.lxxbai.javaversionselector.common.factory;

import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DownloadUtil;
import io.github.lxxbai.javaversionselector.component.fx.DownloadProgress;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Objects;

/**
 * 状态
 *
 * @author lxxbai
 */
public class VersionStatusCellFactory implements Callback<TableColumn<UserJavaVersion, String>, TableCell<UserJavaVersion, String>> {

    public VersionStatusCellFactory() {
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
                VersionStatusEnum status = userJavaVersion.getStatus();
                switch (status) {
                    //刷新后进度条丢失
                    case DOWNLOADING -> {
                        DownloadProgress downloadProgress = DownloadUtil.get(userJavaVersion.getVersion());
                        if (Objects.nonNull(downloadProgress) && !downloadProgress.getTask().isCancelled()) {
                            //开启下载
                            downloadProgress.start();
                            setGraphic(downloadProgress.getContent());
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                    default -> setText(status.getDesc());
                }
            }
        };
    }
}
