package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.util.JFXAlertUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.component.SvgButton;
import io.github.lxxbai.javaversionselector.component.cell.XxbTableCellFactory;
import io.github.lxxbai.javaversionselector.component.menu.MenuItem;
import io.github.lxxbai.javaversionselector.component.menu.SvgMenuItem;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
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
                    //判断用户是否已经安装
                    boolean b = jdkVersionViewModel.versionExists(jdkVersion.getUkVersion());
                    if (b) {
                        if (JFXAlertUtil.showSelectInfo(StageUtil.getPrimaryStage(), "提示", "您已经安装过该版本，是否覆盖安装？")) {
                            installViewModel.download(jdkVersion);
                        }
                    } else {
                        installViewModel.download(jdkVersion);
                    }
                });
                cell.setText("下载");
                cell.setGraphic(downloadButton);
            } else {
                cell.setGraphic(null);
                cell.setText(jdkVersion.getReleaseDate());
            }
        });
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