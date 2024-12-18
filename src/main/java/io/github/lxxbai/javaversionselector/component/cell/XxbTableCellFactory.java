

package io.github.lxxbai.javaversionselector.component.cell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Consumer;

/**
 * @author lxxbai
 * @since JavaFX 2.2
 */
public class XxbTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    /**
     * 构造函数
     */
    private final Consumer<XxbTableCell<S, T>> consumer;

    private XxbTableCellFactory(Consumer<XxbTableCell<S, T>> consumer) {
        this.consumer = consumer;
    }

    /**
     * 设置列表项图标
     */
    public static <S, T> XxbTableCellFactory<S, T> cellFactory(Consumer<XxbTableCell<S, T>> consumer) {
        return new XxbTableCellFactory<>(consumer);
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> tableColumn) {
        return new XxbTableCell<>(consumer);
    }
}
