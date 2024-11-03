
package io.github.lxxbai.javaversionselector.common.factory;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.JFXButtonUtil;
import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * 操作
 *
 * @author lxxbai
 */
public class JdkDownloadActionFactory implements Callback<TableColumn<InstallRecordVO, String>, TableCell<InstallRecordVO, String>> {

    @Override
    public TableCell<InstallRecordVO, String> call(TableColumn<InstallRecordVO, String> tableColumn) {
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
                InstallRecordVO downloadVO = getTableView().getItems().get(getIndex());
                InstallStatusEnum downloadStatus = downloadVO.getDownloadStatus();
                //开始下载按钮，暂停按钮，文件位置按钮，删除按钮
                HBox hBox = new HBox();
                JFXButton filePathButton = JFXButtonUtil.buildSvgButton("svg/folder-regular.svg");
                JFXButton deleteButton = JFXButtonUtil.buildSvgButton("svg/trash-solid.svg");
                switch (downloadStatus) {
                    case DOWNLOADING -> {
                        //暂停按钮
                        JFXButton pauseButton = JFXButtonUtil.buildSvgButton("svg/pause-solid.svg");
                        hBox.getChildren().addAll(pauseButton, filePathButton, deleteButton);
                    }
                    case DOWNLOAD_PAUSE -> {
                        //开始下载按钮
                        JFXButton startButton = JFXButtonUtil.buildSvgButton("svg/play-solid.svg");
                        hBox.getChildren().addAll(startButton, filePathButton, deleteButton);
                    }
                    case DOWNLOAD_FAILURE, DOWNLOADED -> hBox.getChildren().addAll(filePathButton, deleteButton);
                }
                setGraphic(hBox);
            }
        };
    }
}
