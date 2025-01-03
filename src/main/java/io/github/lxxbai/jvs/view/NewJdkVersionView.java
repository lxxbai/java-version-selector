package io.github.lxxbai.jvs.view;

import io.github.lxxbai.jvs.component.menu.MenuItem;
import io.github.lxxbai.jvs.component.menu.SvgMenuItem;
import io.github.lxxbai.jvs.spring.FXMLView;
import io.github.lxxbai.jvs.view.base.MenuView;


/**
 * @author lxxbai
 */
@FXMLView(value = "/view/new_java_version.fxml")
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