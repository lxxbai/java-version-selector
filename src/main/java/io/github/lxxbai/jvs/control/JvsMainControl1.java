package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import io.github.lxxbai.jvs.common.util.AppContextUtil;
import io.github.lxxbai.jvs.component.SvgButton;
import io.github.lxxbai.jvs.component.XxbNumBadge;
import io.github.lxxbai.jvs.component.XxbPopup;
import io.github.lxxbai.jvs.component.menu.PopCellFactory;
import io.github.lxxbai.jvs.spring.AbstractFxmlView;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.FxmlViewUtil;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.InstallView;
import io.github.lxxbai.jvs.view.JdkVersionView;
import io.github.lxxbai.jvs.view.UserJdkView;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class JvsMainControl1 implements Initializable {

    @FXML
    private SvgButton settingsButton;
    @FXML
    private SvgButton myButton;
    @FXML
    private SvgButton downloadingButton;
    @FXML
    private SvgButton versionButton;
    @FXML
    private BorderPane mainBorderPane;

    @Resource
    private JdkVersionView jdkVersionView;

    @Resource
    private InstallView installView;

    @Resource
    private UserJdkView userJdkView;

    private XxbNumBadge xxbNumBadge;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(0, 10, 0, 10));
        JFXSnackbar snackbar = new JFXSnackbar(mainPane);
        //设置snackbar
        AppContextUtil.setSnackbar(snackbar);
        JFXDecorator decoratorView = new JFXDecorator(GUIState.getStage(), mainPane, false, true, true);
        mainBorderPane.setCenter(decoratorView);
        decoratorView.getStyleClass().add("main-center");
        //菜单切换
        versionButton.setOnMouseClicked(event -> mainPane.setCenter(jdkVersionView.getView()));
        downloadingButton.setOnMouseClicked(event -> mainPane.setCenter(installView.getView()));
        myButton.setOnMouseClicked(event -> mainPane.setCenter(userJdkView.getView()));
//        xxbNumBadge = new XxbNumBadge(downloadingButton, 15D, -10D);
        //获取pop内容
        List<AbstractFxmlView> popList = FxmlViewUtil.getGroupView("pop");
        JFXListView<AbstractFxmlView> popupContent = new JFXListView<>();
        popupContent.getStyleClass().add("settings-popup");
        popupContent.getItems().addAll(popList);
        popupContent.setFocusTraversable(false);
        popupContent.setCellFactory(new PopCellFactory());
        popupContent.setPrefWidth(70);
        XxbPopup popup = new XxbPopup(popupContent);
        settingsButton.setOnMouseClicked(event -> popup.showTopRight(settingsButton, 15, 90));
    }

    public void buttonOnclick(ActionEvent actionEvent) {
    }
}
