package io.github.lxxbai.javaversionselector.common.factory;

import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.model.MenuPage;
import javafx.scene.Node;

public class MenuPageFactory {

    public static MenuPage build(String menuName, Node content) {
        return new MenuPage(menuName, content);
    }

    public static MenuPage build(String menuName, String fxmlPath) {
        Node content = FXMLLoaderUtil.load(fxmlPath);
        return new MenuPage(menuName, content);
    }
}
