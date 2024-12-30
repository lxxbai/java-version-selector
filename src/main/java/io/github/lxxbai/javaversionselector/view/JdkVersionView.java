package io.github.lxxbai.javaversionselector.view;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.common.util.JFXAlertUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.component.SvgButton;
import io.github.lxxbai.javaversionselector.component.cell.XxbTableCellFactory;
import io.github.lxxbai.javaversionselector.component.menu.MenuItem;
import io.github.lxxbai.javaversionselector.component.menu.SvgMenuItem;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import io.github.lxxbai.javaversionselector.model.ViewResult;
import io.github.lxxbai.javaversionselector.service.SettingsService;
import io.github.lxxbai.javaversionselector.spring.GUIState;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;


/**
 * @author lxxbai
 */
@FXView(url = "view/java_version.fxml")
@Component
public class JdkVersionView extends MenuContentView {

    @Resource
    private JdkVersionViewModel jdkVersionViewModel;
    @Resource
    private InstallViewModel installViewModel;
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

    @FXML
    public void initialize() {

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

    @Override
    public MenuItem getMenuItem() {
        return new SvgMenuItem("svg/home.svg", "版本");
    }

    @Override
    public int order() {
        return 1;
    }

    /**
     * 下载替换发布日期的cell
     *
     * @return 表格列工厂
     */
    private XxbTableCellFactory<JdkVersionVO, String> buildCellFactory() {
        JFXButton downloadButton = new SvgButton("svg/circle-down-regular.svg", 18, "下载");
        return XxbTableCellFactory.cellFactoryWithRowHover(cell -> {
            JdkVersionVO jdkVersion = cell.getData();
            TableRow<JdkVersionVO> tableRow = cell.getTableRow();
            if (tableRow.isHover()) {
                //下载
                downloadButton.setOnAction(event -> {
                    onDownloadButtonClick(jdkVersion);
                });
                cell.setText("下载");
                cell.setGraphic(downloadButton);
            } else {
                cell.setGraphic(null);
                cell.setText(jdkVersion.getReleaseDate());
            }
        });
    }

    @Resource
    private SettingsService settingsService;

    private void onDownloadButtonClick(JdkVersionVO jdkVersion) {
        boolean b = jdkVersionViewModel.versionExists(jdkVersion.getUkVersion());
        if (b) {
            if (!JFXAlertUtil.showSelectInfo(GUIState.getStage(), "提示", "您本地已安装该版本，是否覆盖安装？")) {
                return;
            }
        }
        //判断用户是否已经配置下载文件地址
        boolean configured = settingsService.baseDefaultConfigured();
        if (configured) {
            installViewModel.download(jdkVersion);
            return;
        }
        ViewResult<DownloadView, Node> result = FXMLLoaderUtil.loadFxView(DownloadView.class);
        JFXAlert<Boolean> alert = new JFXAlert<>(StageUtil.getPrimaryStage());
        alert.setResultConverter(button -> {
            String text = button.getText();
            return StrUtil.equals(text, "下载");
        });
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(result.getViewNode());
        Boolean chooseDownload = alert.showAndWait().orElse(false);
        if (chooseDownload) {
            installViewModel.download(jdkVersion);
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