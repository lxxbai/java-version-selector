package io.github.lxxbai.jvs.common.util;

import io.github.lxxbai.jvs.component.XxbMsgAlert;
import javafx.stage.Stage;


/**
 * @author lxxbai
 */
public class JFXMsgAlertUtil {

    /**
     * 显示信息对话框
     *
     * @param title   对话框标题
     * @param content 对话框内容
     */
    public static void showInfo(Stage ownerStage, String title, String content) {
        XxbMsgAlert alert = new XxbMsgAlert(ownerStage, "svg/info.svg", title, content);
        alert.addOkButton(true);
        alert.showAndWait();
    }


    /**
     * 显示确认和取消对话框
     *
     * @param title   对话框标题
     * @param content 对话框内容
     */
    public static Boolean showSelectInfo(Stage ownerStage, String title, String content) {
        XxbMsgAlert alert = new XxbMsgAlert(ownerStage, "svg/info.svg", title, content);
        alert.addOkButton();
        alert.addCancelButton(true);
        return alert.showAndWait().orElse(false);
    }

    /**
     * 显示警告对话框
     *
     * @param title   对话框标题
     * @param content 对话框内容
     */
    public static void showWarning(Stage ownerStage, String title, String content) {
        XxbMsgAlert alert = new XxbMsgAlert(ownerStage, "svg/warn.svg", title, content);
        alert.addOkButton(true);
        alert.showAndWait();
    }

    /**
     * 显示错误对话框
     *
     * @param title   对话框标题
     * @param content 对话框内容
     */
    public static void showError(Stage ownerStage, String title, String content) {
        XxbMsgAlert alert = new XxbMsgAlert(ownerStage, "svg/error.svg", title, content);
        alert.addOkButton(true);
        alert.showAndWait();
    }
}

