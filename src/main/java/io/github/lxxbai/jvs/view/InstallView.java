package io.github.lxxbai.jvs.view;

import io.github.lxxbai.jvs.component.menu.MenuItem;
import io.github.lxxbai.jvs.component.menu.SvgBadgeMenuItem;
import io.github.lxxbai.jvs.spring.FXMLView;
import io.github.lxxbai.jvs.view.base.MenuView;
import lombok.Getter;

/**
 * @author lxxbai
 */
@Getter
@FXMLView(value = "/view/install_record.fxml", lazy = false)
public class InstallView extends MenuView {

    private final SvgBadgeMenuItem svgBadgeMenuItem;

    public InstallView() {
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
