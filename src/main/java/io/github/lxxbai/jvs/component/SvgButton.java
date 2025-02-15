package io.github.lxxbai.jvs.component;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTooltip;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.jvs.common.util.ResourceUtil;
import io.github.lxxbai.jvs.common.util.SVGGlyphUtil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;

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

    private final DoubleProperty svgSize = new SimpleDoubleProperty(14);

    private final StringProperty svgTooltip = new SimpleStringProperty();

    private SVGGlyph svgGlyph;

    public SvgButton(String svgPath, double size, String tooltip) {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.svgGlyph = new SVGGlyph();
        this.setPadding(new Insets(3));
        this.svgPath.addListener((observableValue, old, newPath) -> {
            if (StrUtil.isNotBlank(newPath)) {
                //加载图标
                this.svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl(newPath));
                this.svgGlyph.setSize(svgSize.get(), svgSize.get());
                this.setCursor(Cursor.HAND);
                this.setGraphic(svgGlyph);
            }
        });
        this.svgSize.addListener((observableValue, old, newSize) -> {
            if (newSize != null) {
                svgGlyph.setSize(newSize.doubleValue(), newSize.doubleValue());
            }
        });
        this.svgTooltip.addListener((observableValue, old, newTooltip) -> {
            if (StrUtil.isNotBlank(newTooltip)) {
                JFXTooltip.install(this, new JFXTooltip(newTooltip), Pos.BOTTOM_CENTER);
            }
        });
        this.svgPath.setValue(svgPath);
        this.svgSize.setValue(size);
        this.svgTooltip.setValue(tooltip);
    }

    public SvgButton(String svgPath, String tooltip) {
        this(svgPath, 14, tooltip);
    }

    public SvgButton(String svgPath, double size) {
        this(svgPath, size, null);
    }


    public SvgButton() {
        this(null, 14, null);
    }

    public double getSvgSize() {
        return svgSize.get();
    }

    public void setSvgSize(double size) {
        this.svgSize.setValue(size);
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


    public DoubleProperty svgSizeProperty() {
        return svgSize;
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
}
