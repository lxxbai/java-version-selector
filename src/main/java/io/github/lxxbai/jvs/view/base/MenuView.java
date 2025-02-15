package io.github.lxxbai.jvs.view.base;

import io.github.lxxbai.jvs.component.menu.MenuItem;

/**
 * 菜单视图
 *
 * @author lxxbai
 */
public interface MenuView {

    /**
     * 获取菜单项
     *
     * @return 菜单项
     */
    MenuItem getMenuItem();

    /**
     * 获取菜单项的顺序
     *
     * @return 菜单项的顺序
     */
    int order();
}
