package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTooltip;
import io.github.lxxbai.jvs.common.util.AppContextUtil;
import io.github.lxxbai.jvs.component.XxbPopup;
import io.github.lxxbai.jvs.component.menu.MenuItemCellFactory;
import io.github.lxxbai.jvs.component.menu.SettingsCellFactory;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.base.MenuFxmlView;
import io.github.lxxbai.jvs.view.base.MenuView;
import io.github.lxxbai.jvs.view.settings.BaseSettingsView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class JvsMainControl implements Initializable {

    @FXML
    private VBox settingsBox;

    @FXML
    private JFXListView<MenuFxmlView> menuViewList;

    @FXML
    private BorderPane mainBorderPane;

    @Autowired
    private List<MenuFxmlView> menuFxmlViews;

    @Autowired
    private List<BaseSettingsView> settingsViews;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //排序
        menuFxmlViews.sort(Comparator.comparingInt(MenuView::order));
        //设置菜单的工厂
        menuViewList.setCellFactory(new MenuItemCellFactory());
        //禁止焦点,不然好像有些问题
        menuViewList.setFocusTraversable(false);
        menuViewList.getItems().addAll(menuFxmlViews);
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(0, 10, 0, 10));
        JFXSnackbar snackbar = new JFXSnackbar(mainPane);
        //设置snackbar
        AppContextUtil.setSnackbar(snackbar);
        JFXDecorator decoratorView = new JFXDecorator(GUIState.getStage(), mainPane, false, true, true);
        mainBorderPane.setCenter(decoratorView);
        decoratorView.getStyleClass().add("main-center");
        //菜单切换
        menuViewList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            mainPane.setCenter(newValue.getView());
        });
        //默认选中
        menuViewList.getSelectionModel().select(menuFxmlViews.get(0));
        //设置列表
        JFXTooltip.install(settingsBox, new JFXTooltip("设置"));
        //排序
        settingsViews.sort(Comparator.comparingInt(x -> x.getAnnotation().order()));
        JFXListView<BaseSettingsView> popupContent = new JFXListView<>();
        popupContent.getStyleClass().add("settings-popup");
        popupContent.getItems().addAll(settingsViews);
        popupContent.setFocusTraversable(false);
        popupContent.setCellFactory(new SettingsCellFactory());
        popupContent.setPrefWidth(70);
        XxbPopup popup = new XxbPopup(popupContent);
        settingsBox.setOnMouseClicked(event -> popup.showTopRight(settingsBox, 0, 90));
    }
}
