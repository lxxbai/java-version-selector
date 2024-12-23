package io.github.lxxbai.javaversionselector.component;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.util.SVGGlyphUtil;
import javafx.scene.control.Label;
import javafx.stage.Window;

/**
 * @author lxxbai
 */
public class XxbAlert extends JFXAlert<Boolean> {

    private final JFXDialogLayout dialogLayout;

    public XxbAlert(Window window, String svgPath, String title, String content) {
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
        //设置数据
        this.setContent(dialogLayout);
        //设置动画
        this.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        setResultConverter(button -> {
            String text = button.getText();
            if (StrUtil.equalsAnyIgnoreCase(text, "确定", "是", "yes", "ok")) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

    public void addOkButton() {
        JFXButton button = new JFXButton("确定");
        button.getStyleClass().add("custom-font-button");
        button.setOnAction(e -> setResult(true));
        dialogLayout.getActions().add(button);
    }

    public void addCancelButton() {
        JFXButton cancelButton = new JFXButton("取消");
        cancelButton.getStyleClass().add("custom-font-button");
        cancelButton.setOnAction(e -> setResult(false));
        dialogLayout.getActions().add(cancelButton);
        cancelButton.requestFocus();
    }
}
