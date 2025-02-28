package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXListView;
import io.github.lxxbai.jvs.component.cell.XxbListCellFactory;
import io.github.lxxbai.jvs.spring.AbstractFxmlView;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.FxmlViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class SettingsControl implements Initializable {

    @FXML
    private BorderPane center;

    @FXML
    private JFXListView<AbstractFxmlView> menuList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //获取pop内容
        List<AbstractFxmlView> popList = FxmlViewUtil.getGroupView("system");
        menuList.getItems().addAll(popList);
        menuList.setCellFactory(XxbListCellFactory.create(x -> {
            AbstractFxmlView item = x.getItem();
            x.setText(item.getAnnotation().title());
        }));
        menuList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                center.setCenter(newValue.getView());
            }
        });
        menuList.getSelectionModel().select(0);
    }
}