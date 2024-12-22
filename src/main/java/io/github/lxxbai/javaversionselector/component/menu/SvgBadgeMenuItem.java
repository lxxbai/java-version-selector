
package io.github.lxxbai.javaversionselector.component.menu;

import io.github.lxxbai.javaversionselector.component.XxbBadge;
import io.github.lxxbai.javaversionselector.component.XxbNumBadge;
import javafx.scene.Node;
import lombok.Getter;

/**
 * @author lxxbai
 */
@Getter
public class SvgBadgeMenuItem extends SvgMenuItem {

    private final XxbNumBadge badge;

    public SvgBadgeMenuItem(String svgPath, String menuName, String desc) {
        super(svgPath, menuName, desc);
        Node node = super.menuIcon();
        this.badge = new XxbNumBadge(node, 10D, -10D);
    }

    public SvgBadgeMenuItem(String svgPath, String menuName) {
        this(svgPath, menuName, menuName);
    }


    @Override
    Node menuIcon() {
        return badge;
    }
}
