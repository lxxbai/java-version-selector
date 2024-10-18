package io.github.lxxbai.javaversionselector.common.factory;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
import io.github.lxxbai.javaversionselector.model.JavaVersionVO;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * 操作按钮
 *
 * @author lxxbai
 */
public class JavaVersionStatusFactory implements Callback<TableColumn<JavaVersionVO, String>, TableCell<JavaVersionVO, String>> {

    @Override
    public TableCell<JavaVersionVO, String> call(TableColumn<JavaVersionVO, String> tableColumn) {
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
                JavaVersionVO javaVersionVO = getTableView().getItems().get(getIndex());
                DownloadStatusEnum downloadStatus = javaVersionVO.getDownloadStatus();
                HBox hBox = new HBox();
                JFXButton downloadButton = new JFXButton();
                hBox.getChildren().addAll(downloadButton);
                switch (downloadStatus) {
                    case NO_DOWNLOADING,DOWNLOAD_FAILURE -> {
                        downloadButton.setText("下载");
                        downloadButton.setOnAction(event -> {
                            //发送下载事件
                        });
                    }
                    case DOWNLOADING -> {
                        downloadButton.setText("下载中");
                        downloadButton.setDisable(true);
                    }
                    case DOWNLOADED -> {
                        downloadButton.setText("已下载");
                        downloadButton.setDisable(true);
                    }
                }
                setGraphic(hBox);
            }
        };
    }

}
