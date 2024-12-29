package io.github.lxxbai.javaversionselector.view.base;

import io.github.lxxbai.javaversionselector.component.menu.MenuItem;
import io.github.lxxbai.javaversionselector.spring.AbstractFxmlView;

/**
 * 菜单视图
 *
 * @author lxxbai
 */
public abstract class MenuView extends AbstractFxmlView {

    public abstract MenuItem getMenuItem();

    public abstract int order();
}
