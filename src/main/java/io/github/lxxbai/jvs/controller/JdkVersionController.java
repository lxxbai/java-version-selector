package io.github.lxxbai.jvs.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.jvs.common.util.JFXMsgAlertUtil;
import io.github.lxxbai.jvs.component.SvgButton;
import io.github.lxxbai.jvs.component.XxbStaticAlert;
import io.github.lxxbai.jvs.component.cell.XxbTableCellFactory;
import io.github.lxxbai.jvs.model.DownloadConfig;
import io.github.lxxbai.jvs.model.JdkVersionVO;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.*;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class JdkVersionController implements Initializable {

    @Resource
    private JdkVersionViewModel jdkVersionViewModel;
    @Resource
    private NewInstallViewModel newInstallViewModel;
    @Resource
    private SettingsViewModel settingsViewModel;
    @Resource
    private DownloadSettingView downloadSettingView;
    @FXML
    private JFXTextField filterJavaVersion;
    @FXML
    private JFXComboBox<String> filterMainVersion;
    @FXML
    private JFXComboBox<String> filterVmVendor;
    @FXML
    private TableView<JdkVersionVO> tableView;
    @FXML
    private TableColumn<JdkVersionVO, String> vmVendor;
    @FXML
    private TableColumn<JdkVersionVO, String> mainVersion;
    @FXML
    private TableColumn<JdkVersionVO, String> javaVersion;
    @FXML
    private TableColumn<JdkVersionVO, String> releaseDate;
    @FXML
    private TableColumn<JdkVersionVO, String> fileName;
    @FXML
    private TableColumn<JdkVersionVO, String> fileSize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //绑定数据
        filterJavaVersion.textProperty().bindBidirectional(jdkVersionViewModel.getFilterJavaVersion());
        filterMainVersion.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterMainVersion());
        filterVmVendor.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterVmVendor());
        //变更事件
        filterJavaVersion.textProperty().addListener(str -> jdkVersionViewModel.filter());
        filterVmVendor.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        filterMainVersion.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        tableView.setItems(jdkVersionViewModel.getJavaVersionList());
        filterVmVendor.setItems(jdkVersionViewModel.getVmVendorList());
        filterMainVersion.setItems(jdkVersionViewModel.getMainVersionList());
        releaseDate.setCellFactory(buildCellFactory());
    }


    /**
     * 下载替换发布日期的cell
     *
     * @return 表格列工厂
     */
    private XxbTableCellFactory<JdkVersionVO, String> buildCellFactory() {
        JFXButton downloadButton = new SvgButton("svg/circle-down-regular.svg", 20, "下载");
        return XxbTableCellFactory.cellFactoryWithRowHover(cell -> {
            JdkVersionVO jdkVersion = cell.getData();
            TableRow<JdkVersionVO> tableRow = cell.getTableRow();
            if (tableRow.isHover()) {
                //下载
                downloadButton.setOnAction(event -> onDownloadButtonClick(jdkVersion));
                cell.setText("下载");
                cell.setGraphic(downloadButton);
            } else {
                cell.setGraphic(null);
                cell.setText(jdkVersion.getReleaseDate());
            }
        });
    }


    /**
     * 点击下载按钮
     *
     * @param jdkVersion java版本
     */
    private void onDownloadButtonClick(JdkVersionVO jdkVersion) {
        boolean b = jdkVersionViewModel.versionExists(jdkVersion.getUkVersion());
        if (b) {
            if (!JFXMsgAlertUtil.showSelectInfo(GUIState.getStage(), "提示", "您本地已安装该版本，是否覆盖安装？")) {
                return;
            }
        }
        //获取下载配置
        DownloadConfig model = settingsViewModel.getModelProperty().getModel();
        //判断用户是否已经配置下载文件地址,已配置
        if (model.isDefaultConfigured()) {
            newInstallViewModel.download(jdkVersion, model.getJdkSavePath(), model.getJdkInstallPath());
            return;
        }
        //弹框
        XxbStaticAlert staticAlert = new XxbStaticAlert(GUIState.getStage(), downloadSettingView.getDetailView());
        staticAlert.cancel(false, "取消");
        staticAlert.ok(true, "确定");
        //未配置,出弹框
        Boolean chooseDownload = staticAlert.showAndWait().orElse(false);
        if (chooseDownload) {
            //重新获取配置
            model = settingsViewModel.getModelProperty().getModel();
            //保存配置
            settingsViewModel.save();
            //下载
            newInstallViewModel.download(jdkVersion, model.getJdkSavePath(), model.getJdkInstallPath());
        } else {
            settingsViewModel.load();
        }
    }


    @FXML
    private void onUpdateDataButtonClick() {
        jdkVersionViewModel.refresh();
    }

    @FXML
    private void resetFilter() {
        jdkVersionViewModel.resetFilter();
    }
}
