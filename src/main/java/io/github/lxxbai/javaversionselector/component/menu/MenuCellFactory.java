package io.github.lxxbai.javaversionselector.component.menu;

import com.jfoenix.controls.JFXTooltip;
import io.github.lxxbai.javaversionselector.model.MenuPage;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * @author lxxbai
 */
public class MenuCellFactory implements Callback<ListView<MenuPage>, ListCell<MenuPage>> {
    @Override
    public ListCell<MenuPage> call(ListView<MenuPage> menuPageListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(MenuPage item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //加载图标
                    MenuIcon menuIcon = new MenuIcon(item.getPicPath());
                    setGraphic(menuIcon);
                    //添加提示
                    JFXTooltip tooltip = new JFXTooltip(item.getMenuName());
                    tooltip.setShowDelay(Duration.millis(100));
                    JFXTooltip.install(this, tooltip, Pos.BOTTOM_CENTER);
                }
            }
        };
    }
}
