package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.jfoenix.validation.RequiredFieldValidator;
import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.service.SettingsService;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
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
        //数据绑定
        parallelDownloadsComboBox.getItems().addAll(1, 2, 3, 4, 5);
        parallelDownloadsComboBox.valueProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getParallelDownloads));
        downloadPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getDownloadPath));
        jdkPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkPathUrl));
        // 初始化 Validator
        RequiredFieldValidator validator = new RequiredFieldValidator();
        // NOTE adding error class to text area is causing the cursor to disapper
        validator.setMessage("Please type something!");
        FontIcon warnIcon = new FontIcon(MaterialDesign.MDI_BARCODE);
        warnIcon.getStyleClass().add("error");
        validator.setIcon(warnIcon);
        jdkPathField.getValidators().add(validator);
        jdkPathField.focusedProperty().addListener((o, oldVal, newVal) ->
        {
            if (!newVal)
            {
                jdkPathField.validate();
            }
        });

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
     * 构建自定义按钮
     *
     * @return JFXButton
     */
    public JFXButton buildConfigButton() throws Exception {
        JFXButton btnSettings = new JFXButton();
        btnSettings.setOnAction(e -> buildSettingsDialog(false));
        SVGGlyph svgGlyph = SVGGlyphLoader.loadGlyph(ResourceUtil.getUrl("icons/4-settings.svg"));
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
    public void buildSettingsDialog(boolean initAlert) {
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
        alert.setOverlayClose(!initAlert);
        alert.setContent(content);
        alert.showAndWait();
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
            buildSettingsDialog(true);
        });
    }
}