package io.github.lxxbai.jvs.component.builder;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.jvs.common.util.SVGGlyphUtil;
import io.github.lxxbai.jvs.component.XxbAlert;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Window;

/**
 * @author lxxbai
 */
public final class XxbAlertBuilder {

    private Window window;

    private Node head;

    private Node content;

    private double prefWidth;

    private String contentStyleClass;

    public XxbAlertBuilder() {
        prefWidth = 300D;
    }

    public static XxbAlertBuilder builder() {
        return new XxbAlertBuilder();
    }

    public XxbAlertBuilder title(String title) {
        return title(title, null);
    }

    public XxbAlertBuilder title(String title, String svgPath) {
        Label label = new Label(title);
        if (StrUtil.isNotBlank(svgPath)) {
            SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(svgPath);
            svgGlyph.setSize(20, 20);
            label.setGraphic(svgGlyph);
        }
        this.head = label;
        return this;
    }

    public XxbAlertBuilder window(Window window) {
        this.window = window;
        return this;
    }

    public XxbAlertBuilder content(Node content) {
        this.content = content;
        return this;
    }

    public XxbAlertBuilder content(Node content, double prefWidth) {
        this.content = content;
        this.prefWidth = prefWidth;
        return this;
    }

    public XxbAlertBuilder content(String content) {
        this.content = new Label(content);
        return this;
    }

    public XxbAlertBuilder contentStyleClass(String contentStyleClass) {
        this.contentStyleClass = contentStyleClass;
        return this;
    }

    public XxbAlertBuilder content(String content, double prefWidth) {
        this.content = new Label(content);
        this.prefWidth = prefWidth;
        return this;
    }

    public XxbAlert build() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(head);
        jfxDialogLayout.setBody(content);
        if (StrUtil.isNotBlank(contentStyleClass)){
            content.getStyleClass().add(contentStyleClass);
        }
        jfxDialogLayout.setPrefWidth(prefWidth);
        return new XxbAlert(window, jfxDialogLayout);
    }
}
