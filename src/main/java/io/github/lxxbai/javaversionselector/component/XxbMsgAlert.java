package io.github.lxxbai.javaversionselector.component;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.util.SVGGlyphUtil;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.util.Objects;

/**
 * @author lxxbai
 */
public class XxbMsgAlert extends JFXAlert<Boolean> {

    private final JFXDialogLayout dialogLayout;

    public XxbMsgAlert(Window window, String svgPath, String title, String content) {
        super(window);
        // 点击背景是否关闭对话框
        this.setOverlayClose(false);
        this.dialogLayout = new JFXDialogLayout();
        //设置标题
        Label headingLabel = new Label(title);
        dialogLayout.setHeading(headingLabel);
        if (StrUtil.isNotBlank(svgPath)) {
            SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(svgPath);
            svgGlyph.setSize(20);
            headingLabel.setGraphic(svgGlyph);
        }
        dialogLayout.setBody(new Label(content));
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

    public void addOkButton() {
        addButton(false, true, "确定");
    }

    public void addOkButton(boolean focus) {
        addButton(focus, true, "确定");
    }

    public void addCancelButton(boolean focus) {
        addButton(focus, false, "取消");
    }

    public JFXButton addButton(boolean focus, boolean result, String text) {
        JFXButton button = new JFXButton(text);
        button.getStyleClass().add("custom-font-button");
        button.setOnAction(e -> setResult(result));
        if (focus) {
            Platform.runLater(button::requestFocus);
        }
        dialogLayout.getActions().add(button);
        return button;
    }
}
