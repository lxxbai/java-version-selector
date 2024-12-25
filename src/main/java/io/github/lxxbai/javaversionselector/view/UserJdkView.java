
package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.enums.ApplyStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.*;
import io.github.lxxbai.javaversionselector.component.SvgButton;
import io.github.lxxbai.javaversionselector.component.cell.XxbTableCellFactory;
import io.github.lxxbai.javaversionselector.component.menu.MenuItem;
import io.github.lxxbai.javaversionselector.component.menu.SvgMenuItem;
import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;


/**
 * @author lxxbai
 */
@FXView(url = "view/my_jdk.fxml")
@Component
public class UserJdkView extends MenuContentView {

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
    private TableColumn<UserJdkVersionVO, String> status;
    @FXML
    private JFXTextField versionFilter;

    @FXML
    public void initialize() throws Exception {
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        status.setCellFactory(buildStatusCellFactory());
        tableView.setItems(userJdkViewModel.getJdkList());
        javaVersion.setCellFactory(buildActionCellFactory());
        //绑定数据
        versionFilter.textProperty().bindBidirectional(userJdkViewModel.getFilterJavaVersion());
        //变更事件
        versionFilter.textProperty().addListener(str -> userJdkViewModel.filter());
    }

    @Override
    public MenuItem getMenuItem() {
        return new SvgMenuItem("svg/user-large-solid.svg", "我的");
    }


    @Override
    public int order() {
        return 3;
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
        SvgButton installButton = new SvgButton("svg/check-solid.svg", 16, 14, "应用");
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
        SvgButton filePathButton = new SvgButton("svg/folder-open-solid.svg", 16, 14, "打开地址");
        filePathButton.setOnAction(event ->
                ThreadPoolUtil.execute(() -> {
                    try {
                        DesktopUtil.openFileDirectory(new File(vo.getLocalHomePath()));
                    } catch (Exception e) {
                        JFXAlertUtil.showWarning(StageUtil.getPrimaryStage(), "告警", "文件夹不存在!");
                    }
                }));
        return filePathButton;
    }
}