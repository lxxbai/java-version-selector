
package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.enums.ApplyStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.*;
import io.github.lxxbai.javaversionselector.component.SvgButton;
import io.github.lxxbai.javaversionselector.component.cell.GraphicTableCellFactory;
import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import jakarta.annotation.Resource;
import javafx.application.Platform;
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
    private JFXTextField versionFilter;

    @FXML
    public void initialize() throws Exception {
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
//        status.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().getDesc()));
        status.setCellFactory(buildStatusCellFactory());
        action.setCellFactory(buildActionCellFactory());
        tableView.setItems(userJdkViewModel.getJdkList());
        //绑定数据
        versionFilter.textProperty().bindBidirectional(userJdkViewModel.getFilterJavaVersion());
        //变更事件
        versionFilter.textProperty().addListener(str -> userJdkViewModel.filter());
    }

    /**
     * 构建状态列
     *
     * @return 工厂
     */
    private GraphicTableCellFactory<UserJdkVersionVO, String> buildStatusCellFactory() {
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            UserJdkVersionVO vo = cellData.getData();
            ApplyStatusEnum applyStatus =  vo.getStatus();
            String color = "#000000";
            switch (applyStatus) {
                case CURRENT-> color = "GREEN";
                case NOT_APPLY -> color = "GRAY";
            }
            return JFXButtonUtil.buildReadOnlyButton(vo.getStatus().getDesc(), color);
        });
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
        SvgButton installButton = new SvgButton("svg/check-solid.svg", "应用");
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
        SvgButton filePathButton = new SvgButton("svg/folder-open-solid.svg", 18, 16, "打开地址");
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