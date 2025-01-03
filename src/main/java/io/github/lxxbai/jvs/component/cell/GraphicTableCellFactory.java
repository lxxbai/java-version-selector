

package io.github.lxxbai.jvs.component.cell;

import io.github.lxxbai.jvs.component.bo.CellDataBO;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

/**
 * @author lxxbai
 */
public class GraphicTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    /**
     * 构造函数
     */
    private final Function<CellDataBO<S>, Node> graphicFunc;

    private GraphicTableCellFactory(Function<CellDataBO<S>, Node> graphicFunc) {
        this.graphicFunc = graphicFunc;
    }

    /**
     * 设置列表项图标
     */
    public static <S, T> GraphicTableCellFactory<S, T> withGraphicFunc(Function<CellDataBO<S>, Node> graphicFunc) {
        return new GraphicTableCellFactory<>(graphicFunc);
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> stTableColumn) {
        return new GraphicTableCell<>(graphicFunc);
    }
}
