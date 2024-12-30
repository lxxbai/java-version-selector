package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.component.menu.MenuItem;
import io.github.lxxbai.javaversionselector.component.menu.SvgMenuItem;
import io.github.lxxbai.javaversionselector.spring.FXMLView;
import io.github.lxxbai.javaversionselector.view.base.MenuView;

/**
 * @author lxxbai
 */
@FXMLView(value = "/view/new_my_jdk.fxml")
public class NewMyJdkView extends MenuView {

    @Override
    public MenuItem getMenuItem() {
        return new SvgMenuItem("svg/user-large-solid.svg", "我的");
    }


    @Override
    public int order() {
        return 3;
    }
}
