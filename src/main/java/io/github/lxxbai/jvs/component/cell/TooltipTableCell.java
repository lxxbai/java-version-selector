

package io.github.lxxbai.jvs.component.cell;

import cn.hutool.core.util.StrUtil;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/**
 * @author liaosp
 * @since JavaFX 2.2
 */
public class TooltipTableCell<S, T> extends TableCell<S, T> {

    public TooltipTableCell() {

    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn() {
        return list -> new TooltipTableCell<>();
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            setTooltip(null);
        } else {
            String desc = StrUtil.toString(item);
            setText(desc);
            setGraphic(null);
            setTooltip(new Tooltip(desc));
        }
    }
}
