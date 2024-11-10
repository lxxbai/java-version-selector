
package io.github.lxxbai.javaversionselector.component.cell;

import com.jfoenix.controls.JFXProgressBar;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * A class containing a {@link TableCell} implementation that draws a
 * {@link ProgressBar} node inside the cell.
 *
 * @param <S> The type of the elements contained within the TableView.
 * @since JavaFX 2.2
 */
public class ProgressBarTableCell<S> extends TableCell<S, Double> {

    /* *************************************************************************
     *                                                                         *
     * Static cell factories                                                   *
     *                                                                         *
     **************************************************************************/

    public static <S> Callback<TableColumn<S,Double>, TableCell<S,Double>> forTableColumn() {
        return param -> new ProgressBarTableCell<S>();
    }



    /* *************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/

    private final JFXProgressBar progressBar;

    private ObservableValue<Double> observable;



    /* *************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a default {@link ProgressBarTableCell} instance
     */
    public ProgressBarTableCell() {
        this.getStyleClass().add("progress-bar-table-cell");
        this.progressBar = new JFXProgressBar();
        this.progressBar.setMaxWidth(Double.MAX_VALUE);
    }



    /* *************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override public void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            progressBar.progressProperty().unbind();
            final TableColumn<S,Double> column = getTableColumn();
            observable = column == null ? null : column.getCellObservableValue(getIndex());

            if (observable != null) {
                progressBar.progressProperty().bind(observable);
            } else if (item != null) {
                progressBar.setProgress(item);
            }
            setGraphic(progressBar);
        }
    }
}
