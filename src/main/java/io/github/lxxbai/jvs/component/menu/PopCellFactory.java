package io.github.lxxbai.jvs.component.menu;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXListCell;
import io.github.lxxbai.jvs.component.XxbSvg;
import io.github.lxxbai.jvs.spring.AbstractFxmlView;
import io.github.lxxbai.jvs.spring.FXMLView;
import io.github.lxxbai.jvs.spring.GUIState;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author lxxbai
 */
public class PopCellFactory implements Callback<ListView<AbstractFxmlView>, ListCell<AbstractFxmlView>> {
    @Override
    public ListCell<AbstractFxmlView> call(ListView<AbstractFxmlView> menuPageListView) {
        return new JFXListCell<>() {
            @Override
            protected void updateItem(AbstractFxmlView item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //加载图标
                    FXMLView annotation = item.getAnnotation();
                    String title = annotation.title();
                    if (StrUtil.isNotBlank(title)) {
                        setText(title);
                    }
                    String svgIcon = annotation.svgIcon();
                    if (StrUtil.isNotBlank(svgIcon)) {
                        XxbSvg xxbSvg = new XxbSvg(svgIcon);
                        setGraphic(xxbSvg);
                    }
                    this.setOnMouseClicked(event -> {
                        GUIState.showInNewStage(item.getView(), title);
                        getListView().getSelectionModel().clearSelection();
                    });
                }
            }
        };
    }
}
