package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.jvs.common.enums.ApplyStatusEnum;
import io.github.lxxbai.jvs.common.util.*;
import io.github.lxxbai.jvs.component.SvgButton;
import io.github.lxxbai.jvs.component.cell.XxbTableCellFactory;
import io.github.lxxbai.jvs.event.JdkScannerEvent;
import io.github.lxxbai.jvs.model.UserJdkVersionVO;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.model.UserJdkViewModel;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class UserJdkControl implements Initializable {

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
    private TableColumn<UserJdkVersionVO, String> javaHome;
    @FXML
    private TableColumn<UserJdkVersionVO, String> status;
    @FXML
    private JFXTextField versionFilter;
    @FXML
    private JFXButton scanButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scanButton.setTooltip(new Tooltip("扫描本地Jdk并导入"));
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
                // 创建一个HBox作为容器
                HBox hbox = new HBox();
                hbox.setSpacing(3);
                hbox.setAlignment(Pos.CENTER_LEFT);
                Label newV = new Label("New");
                newV.getStyleClass().add("badge-new");
                newV.setTranslateY(-5);
                hbox.getChildren().addAll(new Label(vo.getJavaVersion()), newV);
                cell.setText(null);
                cell.setGraphic(hbox);
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
            JFXButton deleteButton = buildDeleteButton(vo);
            hBox.getChildren().addAll(openFileButton, deleteButton);
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
                        .showInfo(GUIState.getStage(), "提示", "已切换到 " + vo.getJavaVersion() + " 版本"));
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
        filePathButton.setOnAction(event -> DesktopUtil.asyncOpenFileDirectory(vo.getLocalHomePath()));
        return filePathButton;
    }

    /**
     * 构建删除按钮
     *
     * @param userJdkVersion 数据
     * @return JFXButton
     */
    private JFXButton buildDeleteButton(UserJdkVersionVO userJdkVersion) {
        SvgButton deleteButton = new SvgButton("svg/trash-solid.svg", "删除");
        deleteButton.setOnAction(event -> {
            Boolean deleteFlag = JFXMsgAlertUtil.showSelectInfo(GUIState.getStage(), "提示", "确定删除吗？(只删除记录)");
            if (deleteFlag) {
                //删除记录,文件不删除
                userJdkViewModel.deleteRecord(userJdkVersion);
            }
        });
        return deleteButton;
    }

    /**
     * 扫描本地jdk
     */
    public void scanLocalJdk() {
        //发消息给扫描控制器
        PublishUtil.publishEvent(new JdkScannerEvent());
    }
}
