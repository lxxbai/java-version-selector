package io.github.lxxbai.jvs.component.cell;

import com.jfoenix.controls.JFXListCell;

import java.util.function.Consumer;

/**
 * @author lxxbai
 */
public class XxbListCell<T> extends JFXListCell<T> {

    /**
     * 构造函数
     */
    private final Consumer<XxbListCell<T>> consumer;

    public XxbListCell(Consumer<XxbListCell<T>> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            setOnMouseClicked(null);
            setTooltip(null);
        } else {
            consumer.accept(this);
        }
    }
}
