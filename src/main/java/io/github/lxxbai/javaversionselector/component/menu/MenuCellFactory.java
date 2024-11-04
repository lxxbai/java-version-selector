package io.github.lxxbai.javaversionselector.component.menu;

import io.github.lxxbai.javaversionselector.model.MenuPage;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MenuCellFactory implements Callback<ListView<MenuPage>, ListCell<MenuPage>> {
    @Override
    public ListCell<MenuPage> call(ListView<MenuPage> menuPageListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(MenuPage item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getMenuName());
                }
            }
        };
    }
}
