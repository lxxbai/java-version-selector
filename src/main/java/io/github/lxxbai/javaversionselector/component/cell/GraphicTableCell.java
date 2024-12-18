

package io.github.lxxbai.javaversionselector.component.cell;

import io.github.lxxbai.javaversionselector.component.bo.CellDataBO;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

/**
 * @author lxxbai
 * @since JavaFX 2.2
 */
public class GraphicTableCell<S, T> extends TableCell<S, T> {

    /**
     * 构造函数
     */
    private final Function<CellDataBO<S>, Node> graphicFunc;

    public GraphicTableCell(Function<CellDataBO<S>, Node> graphicFunc) {
        this.graphicFunc = graphicFunc;
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(Function<CellDataBO<S>, Node> graphicFunc) {
        return list -> new GraphicTableCell<>(graphicFunc);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            S s = getTableView().getItems().get(getIndex());
            setText(null);
            CellDataBO<S> cellData = new CellDataBO<>();
            cellData.setData(s);
            cellData.setIndex(getIndex());
            Node graphic = graphicFunc.apply(cellData);
            setGraphic(graphic);
        }
    }
}
