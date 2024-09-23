package io.github.lxxbai.javaversionselector.common.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class DialogUtils {

    /**
     * 显示一个信息对话框
     *
     * @param title   对话框标题
     * @param header  对话框头部文本
     * @param content 对话框内容文本
     */
    public static void showInfoDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 显示一个警告对话框
     *
     * @param title   对话框标题
     * @param header  对话框头部文本
     * @param content 对话框内容文本
     */
    public static void showWarningDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 显示一个错误对话框
     *
     * @param title   对话框标题
     * @param header  对话框头部文本
     * @param content 对话框内容文本
     */
    public static void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * 显示一个确认对话框
     *
     * @param title   对话框标题
     * @param header  对话框头部文本
     * @param content 对话框内容文本
     * @return 用户的选择结果
     */
    public static Optional<ButtonType> showConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    /**
     * 显示一个输入对话框
     *
     * @param title   对话框标题
     * @param header  对话框头部文本
     * @param content 对话框内容文本
     * @return 用户输入的值
     */
    public static Optional<String> showInputDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        return dialog.showAndWait();
    }
}