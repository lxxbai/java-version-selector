
package io.github.lxxbai.jvs.component.menu;

import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.jvs.common.util.ResourceUtil;
import io.github.lxxbai.jvs.common.util.SVGGlyphUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author lxxbai
 */
public class SvgMenuItem extends MenuItem {

    /**
     * svg路径
     */
    private final VBox menuIcon;

    public SvgMenuItem(String svgPath, String menuName, String desc) {
        this(svgPath, menuName, desc, 25);
    }

    public SvgMenuItem(String svgPath, String menuName, String desc, double size) {
        super(menuName, desc);
        //加载图标
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(ResourceUtil.getUrl(svgPath));
        svgGlyph.setFill(Color.WHITE);
        svgGlyph.setSize(size, size);
        menuIcon = new VBox(svgGlyph);
        menuIcon.setAlignment(Pos.CENTER);
    }

    public SvgMenuItem(String svgPath, String menuName) {
        this(svgPath, menuName, menuName);
    }

    public SvgMenuItem(String svgPath, String menuName, double size) {
        this(svgPath, menuName, menuName, size);
    }

    public SvgMenuItem(String svgPath, double size) {
        this(svgPath, "", size);
    }

    @Override
    public Node menuIcon() {
        return menuIcon;
    }
}
