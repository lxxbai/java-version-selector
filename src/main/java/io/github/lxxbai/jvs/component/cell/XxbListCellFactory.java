package io.github.lxxbai.jvs.component.cell;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.function.Consumer;

/**
 * @author lxxbai
 */
public class XxbListCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

    /**
     * 构造函数
     */
    private final Consumer<XxbListCell<T>> consumer;

    public XxbListCellFactory(Consumer<XxbListCell<T>> consumer) {
        this.consumer = consumer;
    }

    public static <T> XxbListCellFactory<T> create(Consumer<XxbListCell<T>> consumer) {
        return new XxbListCellFactory<>(consumer);
    }

    @Override
    public ListCell<T> call(ListView<T> listView) {
        return new XxbListCell<>(consumer);
    }
}
