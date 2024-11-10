package io.github.lxxbai.javaversionselector.component.cell;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Consumer;
import java.util.function.Function;

public class TableColumCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private Function<S, String> textFunction;

    private Function<S, ObservableValue<String>> textProperty;

    private Function<S, Node> graphicFunction;

    private Function<S, ObservableValue<Node>> graphicProperty;

    private Consumer<TableCell<S, T>> cellInitializer;

    public TableColumCellFactory<S, T> setCellInitializer(Consumer<TableCell<S, T>> cellInitializer) {
        this.cellInitializer = cellInitializer;
        return this;
    }


    /**
     * 设置列表项文本
     */
    public TableColumCellFactory<S, T> withTextFunction(Function<S, String> toStringFunction) {
        if (this.textProperty != null) {
            throw new IllegalStateException("You have already assigned textProperty.");
        }
        this.textFunction = toStringFunction;
        return this;
    }

    /**
     * 绑定列表项文本为一个自动更新的属性
     */
    public TableColumCellFactory<S, T> withTextProperty(Function<S, ObservableValue<String>> toStringProperty) {
        if (this.textFunction != null) {
            throw new IllegalStateException("You have already assigned textFunction.");
        }
        this.textProperty = toStringProperty;
        return this;
    }

    /**
     * 设置列表项图标
     */
    public TableColumCellFactory<S, T> withGraphicFunction(Function<S, Node> graphicFunction) {
        if (this.graphicProperty != null) {
            throw new IllegalStateException("You have already assigned graphicProperty.");
        }
        this.graphicFunction = graphicFunction;
        return this;
    }

    /**
     * 绑定列表项图标为一个自动更新的属性
     */
    public TableColumCellFactory<S, T> withGraphicProperty(Function<S, ObservableValue<Node>> graphicProperty) {
        if (this.graphicFunction != null) {
            throw new IllegalStateException("You have already assigned graphicFunction.");
        }
        this.graphicProperty = graphicProperty;
        return this;
    }


    @Override
    public TableCell<S, T> call(TableColumn<S, T> stTableColumn) {
        TableCell<S, T> tableCell = new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (textProperty().isBound()) {
                    textProperty().unbind();
                }
                if (graphicProperty().isBound()) {
                    graphicProperty().unbind();
                }
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    S s = getTableView().getItems().get(getIndex());
                    setCellText(this, s);
                    setCellGraphic(this, s);
                }
            }
        };
        if (cellInitializer != null) {
            cellInitializer.accept(tableCell);
        }
        return tableCell;
    }


    private void setCellGraphic(TableCell<S, T> cell, S item) {
        if (graphicFunction != null) {
            cell.setGraphic(graphicFunction.apply(item));
        }
        if (graphicProperty != null) {
            cell.graphicProperty().unbind();
            cell.graphicProperty().bind(graphicProperty.apply(item));
        }
    }

    private void setCellText(TableCell<S, T> cell, S item) {
        if (textFunction != null) {
            cell.setText(textFunction.apply(item));
        }
        if (textProperty != null) {
            cell.textProperty().unbind();
            cell.textProperty().bind(textProperty.apply(item));
        }
        if (textFunction == null && textProperty == null) {
            cell.setText(null);
        }
    }
}
