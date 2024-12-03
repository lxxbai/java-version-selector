package io.github.lxxbai.javaversionselector.component.menu;

import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.common.util.SVGGlyphUtil;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author lxxbai
 */
public class MenuIcon extends VBox {

    public MenuIcon(String iconPath) {
        //加载图标
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl(iconPath));
        svgGlyph.setFill(Color.WHITE);
        svgGlyph.setSize(20, 21);
        this.getChildren().addAll(svgGlyph);
        this.getStyleClass().add("menu-item");
        this.setAlignment(Pos.CENTER);
    }
}
