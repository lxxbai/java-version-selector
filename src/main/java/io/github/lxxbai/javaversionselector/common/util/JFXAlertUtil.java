package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class JFXAlertUtil {

    private static JFXDialogLayout buildCommonDialogLayout(String title, String content) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        if (StrUtil.isNotBlank(title)) {
            dialogLayout.setHeading(new Label(title));
        }
        if (StrUtil.isNotBlank(content)) {
            dialogLayout.setBody(new Label(content));
        }
        JFXButton yesButton = new JFXButton("确定");
        JFXButton noButton = new JFXButton("取消");
        dialogLayout.setActions(yesButton, noButton);
        return dialogLayout;
    }
    public static void showCommonDialog(Stage ownerStage, String title, String content) {
        JFXAlert alert = new JFXAlert(ownerStage);
        alert.setContent(buildCommonDialogLayout(title,content));
        alert.setOverlayClose(false);
        alert.showAndWait();
    }


    /**
     * 显示信息对话框
     *
     * @param ownerStage 主要窗口
     * @param title      对话框标题
     * @param header     对话框头部
     * @param content    对话框内容
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
     *
     * @param ownerStage 主要窗口
     * @param title      对话框标题
     * @param header     对话框头部
     * @param content    对话框内容
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
     *
     * @param ownerStage 主要窗口
     * @param title      对话框标题
     * @param header     对话框头部
     * @param content    对话框内容
     */
    public static void showError(Stage ownerStage, String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(ownerStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

