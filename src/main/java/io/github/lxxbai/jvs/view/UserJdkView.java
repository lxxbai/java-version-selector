package io.github.lxxbai.jvs.view;

import io.github.lxxbai.jvs.component.menu.MenuItem;
import io.github.lxxbai.jvs.component.menu.SvgMenuItem;
import io.github.lxxbai.jvs.spring.FXMLView;
import io.github.lxxbai.jvs.view.base.MenuFxmlView;
import io.github.lxxbai.jvs.view.base.MenuView;

/**
 * @author lxxbai
 */
@FXMLView(value = "/view/user_jdk.fxml")
public class UserJdkView extends MenuFxmlView {

    @Override
    public MenuItem getMenuItem() {
        return new SvgMenuItem("svg/user-large-solid.svg", "我的");
    }


    @Override
    public int order() {
        return 3;
    }
}
