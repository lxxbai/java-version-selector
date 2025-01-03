package io.github.lxxbai.jvs.view;

import io.github.lxxbai.jvs.common.util.FXMLLoaderUtil;
import io.github.lxxbai.jvs.component.menu.MenuItem;
import io.github.lxxbai.jvs.component.menu.MenuPage;
import javafx.scene.Node;

/**
 * 菜单内容视图
 *
 * @author lxxbai
 */
public abstract class MenuContentView {


    /**
     * 菜单
     *
     * @return 菜单信息
     */
    public abstract MenuItem getMenuItem();

    /**
     * 菜单对应的视图
     *
     * @return 视图
     */
    public Node getView() {
        return FXMLLoaderUtil.loadFxView(getClass()).getViewNode();
    }


    /**
     * 菜单排序
     *
     * @return 排序
     */
    public int order() {
        return 0;
    }

    /**
     * 转换为菜单页
     *
     * @return 菜单页
     */
    public MenuPage toMenuPage() {
        return new MenuPage(getMenuItem(), getView(), order());
    }
}
