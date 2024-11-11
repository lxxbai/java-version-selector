package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.util.JFXValidUtil;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.common.util.SVGGlyphUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author lxxbai
 */
@FXView(url = "view/settings.fxml")
@Component
public class SettingsView {

    @Resource
    private SettingsViewModel settingsViewModel;

    @FXML
    private JFXComboBox<Integer> parallelDownloadsComboBox;

    @FXML
    private JFXTextField downloadPathField;

    @FXML
    private JFXTextField jdkPathField;

    @FXML
    private JFXDialogLayout diaLogLayout;

    private JFXAlert<Void> jfxAlert;

    @FXML
    public void initialize() {
        //数据绑定
        parallelDownloadsComboBox.getItems().addAll(1, 2, 3, 4, 5);
        parallelDownloadsComboBox.valueProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getParallelDownloads));
        downloadPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getDownloadPath));
        jdkPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkPathUrl));
        //校验
        JFXValidUtil.defaultValidator(downloadPathField, "请选择下载文件存放地址");
        JFXValidUtil.defaultValidator(jdkPathField, "请选择JDK文件放置地址");
        //检查配置
        showSettings();
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
     * 构建自定义按钮
     *
     * @return JFXButton
     */
    public JFXButton buildConfigButton() {
        JFXButton btnSettings = new JFXButton();
        btnSettings.setOnAction(e -> buildSettingsDialog(true));
        //加载图标
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl("svg/settings.svg"));
        svgGlyph.setFill(Color.WHITE);
        svgGlyph.setSize(15, 15);
        btnSettings.getStyleClass().add("jfx-decorator-button");
        btnSettings.setCursor(Cursor.HAND);
        btnSettings.setRipplerFill(Color.WHITE);
        btnSettings.setGraphic(svgGlyph);
        return btnSettings;
    }

    /**
     * 构建设置弹框
     */
    public void buildSettingsDialog(boolean overlayClose) {
        jfxAlert = new JFXAlert<>(StageUtil.getPrimaryStage());
        jfxAlert.setContent(diaLogLayout);
        //初始化数据
        settingsViewModel.load();
        jfxAlert.setOverlayClose(overlayClose);
        jfxAlert.showAndWait();
    }


    /**
     * 检查配置，如果没有配置则弹出设置窗口
     */
    public void showSettings() {
        //检查配置
        Platform.runLater(() -> {
            //检查是否有配置文件
            boolean configured = settingsViewModel.configured();
            if (configured) {
                return;
            }
            buildSettingsDialog(false);
        });
    }

    @FXML
    public void saveSettings() {
        if (jdkPathField.validate() && downloadPathField.validate()) {
            settingsViewModel.save();
            jfxAlert.close();
        }
    }
}