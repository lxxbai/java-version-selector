package io.github.lxxbai.jvs.component;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.stage.Window;

import java.util.Objects;

/**
 * @author lxxbai
 */
public class XxbAlert extends JFXAlert<Boolean> {

    private final JFXDialogLayout dialogLayout;

    public XxbAlert(Window window, JFXDialogLayout dialogLayout) {
        super(window);
        this.dialogLayout = dialogLayout;
        // 点击背景是否关闭对话框
        this.setOverlayClose(false);
        setResultConverter(buttonType -> {
            if (Objects.nonNull(getResult())) {
                return getResult();
            }
            return !buttonType.getButtonData().isCancelButton();
        });
        //设置数据
        this.setContent(dialogLayout);
        //设置动画
        this.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
    }

    public JFXButton addOkButton() {
        return addButton(false, true, "确定");
    }

    public JFXButton addOkButton(boolean focus) {
        return addButton(focus, true, "确定");
    }

    public JFXButton addCancelButton(boolean focus) {
        return addButton(focus, false, "取消");
    }

    public JFXButton addButton(boolean focus, boolean result, String text) {
        JFXButton button = new JFXButton(text);
        button.setOnAction(e -> setResult(result));
        if (focus) {
            button.getStyleClass().add("custom-default-select-button");
            Platform.runLater(button::requestFocus);
        } else {
            button.getStyleClass().add("custom-font-button");
        }
        dialogLayout.getActions().add(button);
        return button;
    }
}
