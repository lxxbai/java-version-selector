package io.github.lxxbai.javaversionselector.common.factory;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DownloadUtil;
import io.github.lxxbai.javaversionselector.model.DownloadVO;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * 进度条
 *
 * @author lxxbai
 */
public class JdkDownloadStatusFactory implements Callback<TableColumn<DownloadVO, String>, TableCell<DownloadVO, String>> {

    @Override
    public TableCell<DownloadVO, String> call(TableColumn<DownloadVO, String> tableColumn) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                // 获取当前行数据
                DownloadVO javaVersionVO = getTableView().getItems().get(getIndex());
                DownloadStatusEnum downloadStatus = javaVersionVO.getDownloadStatus();
                JFXButton downloadButton = new JFXButton();
                DownloadUtil.get(javaVersionVO.getJavaVersion()).start();
                switch (downloadStatus) {
                    case DOWNLOADING -> {
                        downloadButton.setText("下载中");
                        downloadButton.setDisable(true);
                    }
                    case DOWNLOAD_PAUSE -> {
                        downloadButton.setText("下载暂停");
                        downloadButton.setDisable(true);
                    }
                    case DOWNLOADED -> setText("已下载");
                }
            }
        };
    }

}
