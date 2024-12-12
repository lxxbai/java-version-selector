package io.github.lxxbai.javaversionselector.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableColumn;

/**
 * 百分比宽度列
 *
 * @author lxxbai
 */
public class RateTableColumn<S, T> extends TableColumn<S, T> {

    /**
     * 百分比宽度
     */
    private final DoubleProperty rateWidth = new SimpleDoubleProperty(1);


    public RateTableColumn() {
        // 监听TableView的Padding
        tableViewProperty().addListener((ov, oldTableView, newTableView) -> {
                    // 监听TableView的Padding
                    newTableView.paddingProperty().addListener((obv, oldInsets, newInsets) -> {
                        prefWidthProperty().unbind();
                        prefWidthProperty().bind(newTableView.widthProperty()
                                .subtract(newInsets.getLeft() + newInsets.getRight())
                                .multiply(rateWidth));
                    });
                }
        );
        this.setResizable(false);
    }

    public final DoubleProperty rateWidthProperty() {
        return this.rateWidth;
    }

    public final double getRateWidth() {
        return this.rateWidth.get();
    }

    public final void setRateWidth(double value) throws IllegalArgumentException {
        if (value >= 0 && value <= 1) {
            this.rateWidthProperty().set(value);
        } else {
            throw new IllegalArgumentException(String.format("The provided percentage width is not between 0.0 and 1.0. Value is: %1$s", value));
        }
    }
}