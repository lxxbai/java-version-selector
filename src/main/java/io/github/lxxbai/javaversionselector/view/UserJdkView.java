
package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.util.*;
import io.github.lxxbai.javaversionselector.component.cell.GraphicTableCellFactory;
import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * @author lxxbai
 */
@FXView(url = "view/my_jdk.fxml")
@Component
public class UserJdkView {


    @Resource
    private UserJdkViewModel userJdkViewModel;
    @FXML
    private TableView<UserJdkVersionVO> tableView;
    @FXML
    private TableColumn<UserJdkVersionVO, String> vmVendor;
    @FXML
    private TableColumn<UserJdkVersionVO, String> mainVersion;
    @FXML
    private TableColumn<UserJdkVersionVO, String> javaVersion;
    @FXML
    private TableColumn<UserJdkVersionVO, String> action;
    @FXML
    private TableColumn<UserJdkVersionVO, String> status;

    @FXML
    public void initialize() throws Exception {
        ReadOnlyDoubleProperty width = tableView.widthProperty();
        //设置百分比宽度
        vmVendor.prefWidthProperty().bind(width.multiply(.19));
        mainVersion.prefWidthProperty().bind(width.multiply(.2));
        javaVersion.prefWidthProperty().bind(width.multiply(.2));
        action.prefWidthProperty().bind(width.multiply(.2));
        status.prefWidthProperty().bind(width.multiply(.19));
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        status.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().getDesc()));
        action.setCellFactory(buildActionCellFactory());
        tableView.setItems(userJdkViewModel.getJdkList());
    }

    /**
     * 构建状态列
     *
     * @return 工厂
     */
    private GraphicTableCellFactory<UserJdkVersionVO, String> buildActionCellFactory() {
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            UserJdkVersionVO vo = cellData.getData();
            //文件位置按钮，应用按钮
            HBox hBox = new HBox();
            JFXButton openFileButton = openFileButton(vo);
            JFXButton applyButton = buildApplyButton(vo);
            hBox.getChildren().addAll(applyButton, openFileButton);
            return hBox;
        });
    }

    /**
     * 构建安装按钮
     *
     * @param vo 数据
     * @return JFXButton
     */
    private JFXButton buildApplyButton(UserJdkVersionVO vo) {
        JFXButton installButton = JFXButtonUtil.buildSvgButton("svg/check-solid.svg", "应用");
        installButton.setOnAction(event -> {
            userJdkViewModel.applyJdk(vo);
            //添加环境变量
            ThreadPoolUtil.execute(() -> UserEnvUtil.addWindowsJdkHome(vo.getLocalHomePath()));

        });
        return installButton;
    }

    /**
     * 构建文件位置按钮
     *
     * @param vo 数据
     * @return JFXButton
     */
    private JFXButton openFileButton(UserJdkVersionVO vo) {
        JFXButton filePathButton = JFXButtonUtil.buildSvgButton("svg/folder-open-solid.svg", "打开地址");
        filePathButton.setOnAction(event ->
                Platform.runLater(() -> {
                    try {
                        DesktopUtil.openFileDirectory(new File(vo.getLocalHomePath()));
                    } catch (Exception e) {
                        AlertUtil.showError(StageUtil.getPrimaryStage(), "打开失败", "打开失败", "文件夹不存在!");
                    }
                }));
        return filePathButton;
    }
}