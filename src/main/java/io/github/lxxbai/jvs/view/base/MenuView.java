package io.github.lxxbai.jvs.view.base;

import io.github.lxxbai.jvs.component.menu.MenuItem;
import io.github.lxxbai.jvs.spring.AbstractFxmlView;

/**
 * 菜单视图
 *
 * @author lxxbai
 */
public abstract class MenuView extends AbstractFxmlView {

    private MenuItem menuItem;

    /**
     * 获取菜单项
     *
     * @return 菜单项
     */
    public abstract MenuItem getMenuItem();

    /**
     * 获取菜单项的顺序
     *
     * @return 菜单项的顺序
     */
    public abstract int order();
}
