package io.github.lxxbai.javaversionselector.component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTooltip;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.common.util.SVGGlyphUtil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * svg构建的button
 *
 * @author lxxbai
 */
public class SvgButton extends JFXButton {

    private static final String DEFAULT_STYLE_CLASS = "svg-button";

    /**
     * svg地址
     */
    private final StringProperty svgPath = new SimpleStringProperty();

    private final DoubleProperty svgWidth = new SimpleDoubleProperty(16);

    private final DoubleProperty svgHeight = new SimpleDoubleProperty(16);

    private final StringProperty svgTooltip = new SimpleStringProperty();

    private SVGGlyph svgGlyph;

    public SvgButton(String svgPath, double width, double height, String tooltip) {
        initialize();
        this.svgPath.set(svgPath);
        this.svgWidth.set(width);
        this.svgHeight.set(height);
        this.svgTooltip.set(tooltip);
    }

    public SvgButton(String svgPath, double size, String tooltip) {
        this(svgPath, size, size, tooltip);
    }

    public SvgButton(String svgPath, String tooltip) {
        this(svgPath, 16, 16, tooltip);
    }

    public static SvgButton create(String svgPath, double size, String tooltip) {
        return new SvgButton(svgPath, size, tooltip);
    }

    public static SvgButton create(String svgPath, String tooltip) {
        return new SvgButton(svgPath, tooltip);
    }

    public SvgButton() {
        initialize();
    }

    public String getSvgPath() {
        return svgPath.get();
    }

    public void setSvgPath(String path) {
        this.svgPath.set(path);
    }

    public final StringProperty svgPathProperty() {
        return this.svgPath;
    }


    public double getSvgWidth() {
        return this.svgWidth.get();
    }

    public DoubleProperty svgWidthProperty() {
        return this.svgWidth;
    }

    public void setSvgWidth(double svgWidth) {
        this.svgWidth.set(svgWidth);
    }

    public double getSvgHeight() {
        return svgHeight.get();
    }

    public DoubleProperty svgHeightProperty() {
        return svgHeight;
    }

    public void setSvgHeight(double svgHeight) {
        this.svgHeight.set(svgHeight);
    }

    public String getSvgTooltip() {
        return svgTooltip.get();
    }

    public void setSvgTooltip(String svgTooltip) {
        this.svgTooltip.set(svgTooltip);
    }

    public StringProperty svgTooltipProperty() {
        return svgTooltip;
    }

    private void initialize() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        JFXTooltip tooltip = new JFXTooltip();
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.textProperty().bind(svgTooltip);
        JFXTooltip.install(this, tooltip, Pos.BOTTOM_CENTER);
        svgPath.addListener((observableValue, old, newPath) -> {
            //加载图标
            svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl(newPath));
            svgGlyph.prefWidthProperty().bind(svgWidth);
            svgGlyph.prefHeightProperty().bind(svgHeight);
            this.setCursor(Cursor.HAND);
            this.setRipplerFill(Color.WHITE);
            this.setGraphic(svgGlyph);
        });
    }
}
