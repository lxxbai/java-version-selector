package io.github.lxxbai.jvs.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.jvs.common.enums.ApplyStatusEnum;
import io.github.lxxbai.jvs.common.util.*;
import io.github.lxxbai.jvs.component.RateTableColumn;
import io.github.lxxbai.jvs.component.SvgButton;
import io.github.lxxbai.jvs.component.cell.XxbTableCellFactory;
import io.github.lxxbai.jvs.model.UserJdkVersionVO;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.UserJdkViewModel;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class UserJdkController implements Initializable {

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
    private TableColumn<UserJdkVersionVO,String> javaHome;
    @FXML
    private TableColumn<UserJdkVersionVO, String> status;
    @FXML
    private JFXTextField versionFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaHome.setCellValueFactory(new PropertyValueFactory<>("localHomePath"));
        status.setCellFactory(buildStatusCellFactory());
        tableView.setItems(userJdkViewModel.getJdkList());
        javaVersion.setCellFactory(buildActionCellFactory());
        //绑定数据
        versionFilter.textProperty().bindBidirectional(userJdkViewModel.getFilterJavaVersion());
        //变更事件
        versionFilter.textProperty().addListener(str -> userJdkViewModel.filter());
    }

    /**
     * 构建状态
     *
     * @return 表格列工厂
     */
    private XxbTableCellFactory<UserJdkVersionVO, String> buildStatusCellFactory() {
        return XxbTableCellFactory.cellFactory(cell -> {
            UserJdkVersionVO vo = cell.getData();
            ApplyStatusEnum applyStatus = vo.getStatus();
            cell.setText(null);
            cell.setGraphic(Objects.equals(applyStatus, ApplyStatusEnum.CURRENT) ?
                    JFXButtonUtil.buildReadOnlyButton("当前版本", "GREEN")
                    : JFXButtonUtil.buildReadOnlyButton("未应用", "GRAY"));
        });
    }

    /**
     * 构建状态
     *
     * @return 表格列工厂
     */
    private XxbTableCellFactory<UserJdkVersionVO, String> buildActionCellFactory() {
        return XxbTableCellFactory.cellFactoryWithRowHover(cell -> {
            UserJdkVersionVO vo = cell.getData();
            if (!cell.getTableRow().isHover()) {
                cell.setGraphic(null);
                cell.setText(vo.getJavaVersion());
                return;
            }
            //文件位置按钮，应用按钮
            HBox hBox = new HBox();
            hBox.setSpacing(3);
            hBox.setAlignment(Pos.CENTER_LEFT);
            if (Objects.equals(ApplyStatusEnum.NOT_APPLY, vo.getStatus())) {
                JFXButton applyButton = buildApplyButton(vo);
                hBox.getChildren().add(applyButton);
            }
            JFXButton openFileButton = openFileButton(vo);
            hBox.getChildren().add(openFileButton);
            cell.setGraphic(hBox);
            cell.setText(null);
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
            ThreadPoolUtil.execute(() -> {
                UserEnvUtil.addWindowsJdkHome(vo.getLocalHomePath());
                Platform.runLater(() -> JFXMsgAlertUtil
                        .showInfo(GUIState.getStage(), "提示", "已切换到 " + vo.getMainVersion() + " 版本"));
            });

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
        SvgButton filePathButton = new SvgButton("svg/folder-open-solid.svg", "打开地址");
        filePathButton.setOnAction(event ->
                ThreadPoolUtil.execute(() -> {
                    try {
                        DesktopUtil.openFileDirectory(new File(vo.getLocalHomePath()));
                    } catch (Exception e) {
                        JFXMsgAlertUtil.showWarning(GUIState.getStage(), "告警", "文件夹不存在!");
                    }
                }));
        return filePathButton;
    }


    public void scanLocalJdk(ActionEvent actionEvent) {
    }
}
