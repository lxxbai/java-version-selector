package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.*;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.service.SettingsService;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author lxxbai
 */
@Component
public class SettingsView {

    @Resource
    private SettingsViewModel settingsViewModel;
    @Resource
    private SettingsService settingsService;
    @FXML
    public JFXComboBox<Integer> parallelDownloadsComboBox;
    @FXML
    private JFXTextField downloadPathField;
    @FXML
    private JFXTextField jdkPathField;

    @FXML
    public void initialize() {
        parallelDownloadsComboBox.getValidators()
                .add(new RequiredFieldValidator("请选择数字"));
        //数据绑定
        parallelDownloadsComboBox.getItems().addAll(1, 2, 3, 4, 5);
        parallelDownloadsComboBox.valueProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getParallelDownloads));
        downloadPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getDownloadPath));
        downloadPathField.getValidators().add(new RequiredFieldValidator("请选择下载路径"));

        jdkPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkPathUrl));
        //初始化数据
        settingsViewModel.load();
    }


    @FXML
    private void chooseDownloadDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择下载文件存放地址");
        File selectedDirectory = directoryChooser.showDialog(downloadPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            downloadPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void chooseJdkDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择 JDK 文件放置地址");
        File selectedDirectory = directoryChooser.showDialog(jdkPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            jdkPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }


    /**
     * 检查配置，如果没有配置则弹出设置窗口
     */
    public void checkConfig() {
        //检查配置
        Platform.runLater(() -> {
            //检查是否有配置文件
            boolean configured = settingsService.configured();
            if (configured) {
                return;
            }
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Label("设置"));
            // 未配置，或者首次配置，显示提示框
            Node node = FXMLLoaderUtil.load("view/settings.fxml");
            content.setBody(node);
            // 添加关闭按钮
            JFXButton saveButton = new JFXButton("保存");
            content.setActions(saveButton);
            JFXAlert<Void> alert = new JFXAlert<>(StageUtil.getPrimaryStage());
            //保存数据并关闭
            saveButton.setOnAction(e -> {
                settingsViewModel.save();
                alert.close();
            });
            alert.setOverlayClose(false);
            alert.setContent(content);
            alert.showAndWait();
        });
    }
}