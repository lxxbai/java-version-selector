package io.github.lxxbai.jvs.controller;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import io.github.lxxbai.jvs.common.util.AppContextUtil;
import io.github.lxxbai.jvs.component.menu.MenuItemCellFactory;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.base.MenuView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class JvsMainController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private JFXListView<MenuView> listView;

    @Autowired
    private List<MenuView> menuViews;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //排序
        menuViews.sort(Comparator.comparingInt(MenuView::order));
        //设置菜单的工厂
        listView.setCellFactory(new MenuItemCellFactory());
        //禁止焦点,不然好像有些问题
        listView.setFocusTraversable(false);
        listView.getItems().addAll(menuViews);
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(0, 10, 0, 10));
        JFXSnackbar snackbar = new JFXSnackbar(mainPane);
        //设置snackbar
        AppContextUtil.setSnackbar(snackbar);
        JFXDecorator decoratorView = new JFXDecorator(GUIState.getStage(), mainPane);
        decoratorView.getStyleClass().add("main-center");
        //菜单切换
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("版本:17.0.4下载完成"), Duration.seconds(3)));
            if (newValue == null) {
                return;
            }
            mainPane.setCenter(newValue.getView());
        });
        //默认选中
        listView.getSelectionModel().select(menuViews.get(0));
        mainBorderPane.setCenter(decoratorView);
    }
}
