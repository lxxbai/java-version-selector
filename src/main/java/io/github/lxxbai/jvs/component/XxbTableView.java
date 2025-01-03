package io.github.lxxbai.jvs.component;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 定制tableview
 *
 * @author lxxbai
 */
public class XxbTableView<S> extends TableView<S> {

    private static final String LAST_CELLS_CLASS = "last-column";
    private static final String FIRST_CELLS_CLASS = "first-column";


    public XxbTableView() {
        // 监听列变化,添加样式
        this.getColumns().addListener((ListChangeListener<TableColumn<S, ?>>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    // 第一列.最后一列删除样式
                    change.getList().forEach(column -> column.getStyleClass().removeAll(LAST_CELLS_CLASS, FIRST_CELLS_CLASS));
                    if (!change.getList().isEmpty()) {
                        // 最后一列添加样式
                        change.getList().get(change.getList().size() - 1).getStyleClass().add(LAST_CELLS_CLASS);
                        // 第一列添加样式
                        change.getList().get(0).getStyleClass().add(FIRST_CELLS_CLASS);
                    }
                }
            }
        });
        // 默认不选中第一行
        Platform.runLater(() -> {
            // 取消默认的第一行选中
            getSelectionModel().clearSelection();
        });
    }
}
