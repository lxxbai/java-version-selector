package io.github.lxxbai.javaversionselector.common.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class AlertUtil {

    /**
     * 显示信息对话框
     * @param ownerStage 主要窗口
     * @param title 对话框标题
     * @param header 对话框头部
     * @param content 对话框内容
     */
    public static void showInfo(Stage ownerStage, String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(ownerStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 显示警告对话框
     * @param ownerStage 主要窗口
     * @param title 对话框标题
     * @param header 对话框头部
     * @param content 对话框内容
     */
    public static void showWarning(Stage ownerStage, String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(ownerStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 显示错误对话框
     * @param ownerStage 主要窗口
     * @param title 对话框标题
     * @param header 对话框头部
     * @param content 对话框内容
     */
    public static void showError(Stage ownerStage, String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(ownerStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 显示确认对话框
     * @param ownerStage 主要窗口
     * @param title 对话框标题
     * @param header 对话框头部
     * @param content 对话框内容
     * @return 用户选择的按钮类型
     */
    public static ButtonType showConfirmation(Stage ownerStage, String title, String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(ownerStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Add custom buttons
        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(ButtonType.CANCEL);
    }
}

