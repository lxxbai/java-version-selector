package io.github.lxxbai.jvs.component;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.stage.Window;

/**
 * @author lxxbai
 */
public class XxbStaticAlert extends JFXAlert<Boolean> {

    private final JFXDialogLayout dialogLayout;

    public XxbStaticAlert(Window window, JFXDialogLayout dialogLayout) {
        super(window);
        this.dialogLayout = dialogLayout;
        // 清除默认按钮
        dialogLayout.getActions().clear();
        setContent(dialogLayout);
        setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        setOverlayClose(false);
        //设置结果转换器,不是退出的就是true
        setResultConverter(button -> button.getButtonData() != ButtonBar.ButtonData.CANCEL_CLOSE);
    }


    public void cancel(boolean isDefault, String text) {
        addButton(isDefault, false, text);
    }

    public void ok(boolean isDefault, String text) {
        addButton(isDefault, true, text);
    }

    /**
     * 添加按钮
     *
     * @param isDefault  是否自动获取焦点
     * @param result 结果
     * @param text   按钮文本
     * @return 按钮
     */
    public JFXButton addButton(boolean isDefault, boolean result, String text) {
        JFXButton button = new JFXButton(text);
        button.setOnAction(e -> setResult(result));
        if (isDefault) {
            button.getStyleClass().add("custom-default-select-button");
            Platform.runLater(button::requestFocus);
        }else{
            button.getStyleClass().add("custom-font-button");
        }
        dialogLayout.getActions().add(button);
        return button;
    }
}
