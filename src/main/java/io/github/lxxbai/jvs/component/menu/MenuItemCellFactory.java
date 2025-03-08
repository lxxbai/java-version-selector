package io.github.lxxbai.jvs.component.menu;

import com.jfoenix.controls.JFXTooltip;
import io.github.lxxbai.jvs.view.base.MenuFxmlView;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author lxxbai
 */
public class MenuItemCellFactory implements Callback<ListView<MenuFxmlView>, ListCell<MenuFxmlView>> {
    @Override
    public ListCell<MenuFxmlView> call(ListView<MenuFxmlView> menuPageListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(MenuFxmlView item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                    setTooltip(null);
                } else {
                    //加载图标
                    MenuItem menuItem = item.getMenuItem();
                    setGraphic(menuItem.menuIcon());
                    //添加提示
                    JFXTooltip.install(this, new JFXTooltip(menuItem.getMenuName()), Pos.BOTTOM_CENTER);
                }
            }
        };
    }
}
