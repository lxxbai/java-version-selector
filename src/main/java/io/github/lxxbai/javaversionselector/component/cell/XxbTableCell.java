

package io.github.lxxbai.javaversionselector.component.cell;

import javafx.scene.control.TableCell;

import java.util.function.Consumer;

/**
 * @author lxxbai
 */
public class XxbTableCell<S, T> extends TableCell<S, T> {

    /**
     * 构造函数
     */
    private final Consumer<XxbTableCell<S, T>> consumer;

    public XxbTableCell(Consumer<XxbTableCell<S, T>> consumer) {
        this.consumer = consumer;
    }


    public final S getData() {
        return getTableView().getItems().get(getIndex());
    }

    public final int getCurrentIndex() {
        return getIndex();
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            consumer.accept(this);
        }
    }
}
