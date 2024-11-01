

package io.github.lxxbai.javaversionselector.component.cell;

import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

/**
 * @author liaosp
 * @since JavaFX 2.2
 */
public class GraphicTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    /**
     * 构造函数
     */
    private final Function<S, Node> graphicFunc;

    private GraphicTableCellFactory(Function<S, Node> graphicFunc) {
        this.graphicFunc = graphicFunc;
    }

    /**
     * 设置列表项图标
     */
    public static <S, T> GraphicTableCellFactory<S, T> withGraphicFunc(Function<S, Node> graphicFunction) {
        return new GraphicTableCellFactory<>(graphicFunction);
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> stTableColumn) {
        return new GraphicTableCell<>(graphicFunc);
    }
}
