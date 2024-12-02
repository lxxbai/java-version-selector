package io.github.lxxbai.javaversionselector.component.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.common.util.SVGGlyphUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author lxxbai
 */
public class MenuIcon extends VBox {

    public MenuIcon(String iconPath, String desc) {
        JFXButton btnSettings = new JFXButton();
        //加载图标
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl(iconPath));
        //derive(#d1e9ec, -20%)
        svgGlyph.setFill(Color.BURLYWOOD);
        svgGlyph.setSize(30, 30);
        Label label = new Label(desc);
        this.getChildren().addAll(svgGlyph, label);
        this.getStyleClass().add("menu-item");
    }
}
