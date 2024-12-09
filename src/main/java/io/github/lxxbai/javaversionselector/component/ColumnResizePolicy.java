package io.github.lxxbai.javaversionselector.component;

import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.function.Consumer;

/**
 * @author lxxbai
 */
public class ColumnResizePolicy<T> implements Callback<TableView.ResizeFeatures<T>, Boolean> {

    private final Consumer<TableView<T>> tableViewConsumer;

    public ColumnResizePolicy(Consumer<TableView<T>> tableViewConsumer) {
        this.tableViewConsumer = tableViewConsumer;
    }

    @Override
    public Boolean call(TableView.ResizeFeatures<T> resizeFeatures) {
        TableView.UNCONSTRAINED_RESIZE_POLICY.call(resizeFeatures);
        TableView<T> table = resizeFeatures.getTable();
        tableViewConsumer.accept(table);
        return true;
    }
}