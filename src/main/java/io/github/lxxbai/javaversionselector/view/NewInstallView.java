package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.component.menu.MenuItem;
import io.github.lxxbai.javaversionselector.component.menu.SvgBadgeMenuItem;
import io.github.lxxbai.javaversionselector.spring.FXMLView;
import io.github.lxxbai.javaversionselector.view.base.MenuView;
import lombok.Getter;

/**
 * @author lxxbai
 */
@FXMLView(value = "/view/new_install_record.fxml")
public class NewInstallView extends MenuView {

    @Getter
    private final SvgBadgeMenuItem svgBadgeMenuItem;

    public NewInstallView() {
        svgBadgeMenuItem = new SvgBadgeMenuItem("svg/download-solid.svg", "进度");
    }

    @Override
    public MenuItem getMenuItem() {
        return svgBadgeMenuItem;
    }

    @Override
    public int order() {
        return 2;
    }
}
