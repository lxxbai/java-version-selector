

package io.github.lxxbai.javaversionselector.component.cell;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXTooltip;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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
        } else {
            String desc = StrUtil.toString(item);
            JFXTooltip jfxTooltip = new JFXTooltip(desc);
            setText(desc);
            jfxTooltip.setPos(Pos.BOTTOM_CENTER);
            JFXTooltip.install(this, jfxTooltip);
        }
    }
}
