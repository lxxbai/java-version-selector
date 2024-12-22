
package io.github.lxxbai.javaversionselector.component.menu;

import javafx.scene.Node;
import lombok.Getter;

/**
 * @author lxxbai
 */
@Getter
public abstract class MenuItem {

    /**
     * 菜单名称
     */
    private final String menuName;

    /**
     * 菜单描述
     */
    private final String desc;

    /**
     * 菜单图标
     *
     * @return 菜单图标
     */
    abstract Node menuIcon();


    protected MenuItem(String menuName, String desc) {
        this.menuName = menuName;
        this.desc = desc;
    }
}
