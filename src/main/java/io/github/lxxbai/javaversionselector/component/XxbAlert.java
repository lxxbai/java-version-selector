package io.github.lxxbai.javaversionselector.component;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Window;

/**
 * @author lxxbai
 */
public class XxbAlert extends JFXAlert<Boolean> {

    private static final String BUTTON_STYLE_CLASS = "custom-font-button";

    private final JFXDialogLayout dialogLayout;

    private String okText = "Ok";

    public XxbAlert(Window window, JFXDialogLayout dialogLayout) {
        super(window);
        this.dialogLayout = dialogLayout;
        setContent(dialogLayout);
        setResultConverter(button -> {
            String text = button.getText();
            return StrUtil.equalsAnyIgnoreCase(okText, text);
        });
    }


    public void addOkButton(String text, boolean focus) {
        addButton(true, text, focus);
        okText = text.trim();
    }

    public void addButton(boolean result, String text, boolean focus) {
        JFXButton button = new JFXButton(text);
        button.getStyleClass().add(BUTTON_STYLE_CLASS);
        button.setOnAction(e -> setResult(result));
        dialogLayout.getActions().add(button);
        if (focus) {
            button.requestFocus();
        }
    }

    public void addCancelButton(String text, boolean focus) {
        addButton(false, text, focus);
    }
}
