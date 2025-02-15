package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXListView;
import io.github.lxxbai.jvs.component.cell.XxbListCellFactory;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.view.settings.SystemSettingsView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Comparator;
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
    private JFXListView<SystemSettingsView> menuList;

    @Autowired
    private List<SystemSettingsView> settingsViews;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingsViews.sort(Comparator.comparingInt(x -> x.getAnnotation().order()));
        menuList.getItems().addAll(settingsViews);
        menuList.setCellFactory(XxbListCellFactory.create(x -> {
            SystemSettingsView item = x.getItem();
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