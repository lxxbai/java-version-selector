package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.component.menu.MenuItem;
import io.github.lxxbai.javaversionselector.component.menu.SvgMenuItem;
import io.github.lxxbai.javaversionselector.spring.FXMLView;
import io.github.lxxbai.javaversionselector.view.base.MenuView;


/**
 * @author lxxbai
 */
@FXMLView(value = "/view/new_java_version.fxml",css = "/css/pink-theme.css")
public class NewJdkVersionView extends MenuView {

    @Override
    public MenuItem getMenuItem() {
        return new SvgMenuItem("svg/home.svg", "版本");
    }

    @Override
    public int order() {
        return 0;
    }
}