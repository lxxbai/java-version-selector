package io.github.lxxbai.javaversionselector.component.menu;

import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.model.MenuPage;
import io.github.lxxbai.javaversionselector.model.ViewResult;
import javafx.scene.Node;

/**
 * @author lxxbai
 */
public class MenuPageFactory {

    public static MenuPage build(String menuName, String picPath, Class<?> clazz) {
        ViewResult<?, Node> nodeViewResult = FXMLLoaderUtil.loadFxView(clazz);
        return new MenuPage(menuName, picPath, nodeViewResult.getViewNode());
    }
}
