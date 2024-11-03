package io.github.lxxbai.javaversionselector.common.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTooltip;
import com.jfoenix.svg.SVGGlyph;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author lxxbai
 */
public class JFXButtonUtil {


    /**
     * 构建自定义按钮
     *
     * @return JFXButton
     */
    public static JFXButton buildSvgButton(String path) {
        JFXButton btnSettings = new JFXButton();
        //btnSettings.setOnAction(e -> buildSettingsDialog(true));
        //加载图标
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl(path));
        //derive(#d1e9ec, -20%)
        svgGlyph.setFill(Color.BURLYWOOD);
        svgGlyph.setSize(15, 15);
        btnSettings.getStyleClass().add("jfx-decorator-button");
        btnSettings.setCursor(Cursor.HAND);
        btnSettings.setRipplerFill(Color.WHITE);
        btnSettings.setGraphic(svgGlyph);
        return btnSettings;
    }

    /**
     * 构建自定义按钮
     *
     * @return JFXButton
     */
    public static JFXButton buildSvgButton(String path, String tooltipStr) {
        JFXButton btnSettings = new JFXButton();
        //加载图标
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl(path));
        //derive(#d1e9ec, -20%)
        svgGlyph.setFill(Color.BURLYWOOD);
        svgGlyph.setSize(15, 15);
        btnSettings.setCursor(Cursor.HAND);
        btnSettings.setRipplerFill(Color.WHITE);
        btnSettings.setGraphic(svgGlyph);
        JFXTooltip tooltip = new JFXTooltip(tooltipStr);
        tooltip.setShowDelay(Duration.millis(100));
        // 或者直接设置样式
        tooltip.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-text-fill: white;");
        JFXTooltip.install(btnSettings, tooltip, Pos.CENTER_RIGHT);
        return btnSettings;
    }

    /**
     * 构建动态图标按钮
     *
     * @return JFXButton
     */
    public static JFXButton buildDynamicButton(String path) {
        JFXButton btnSettings = new JFXButton();
        Image image = ResourceUtil.toImage(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        btnSettings.setCursor(Cursor.HAND);
        btnSettings.setRipplerFill(Color.WHITE);
        btnSettings.setGraphic(imageView);
        return btnSettings;
    }
}
