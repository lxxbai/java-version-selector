package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.JFXButtonUtil;
import io.github.lxxbai.javaversionselector.component.cell.GraphicTableCellFactory;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import jakarta.annotation.Resource;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;


/**
 * @author lxxbai
 */
@Component
public class JdkVersionView {
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
    private TableColumn<JdkVersionVO, String> action;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty width = tableView.widthProperty();
        //设置百分比宽度
        vmVendor.prefWidthProperty().bind(width.multiply(.1));
        mainVersion.prefWidthProperty().bind(width.multiply(.1));
        javaVersion.prefWidthProperty().bind(width.multiply(.1));
        releaseDate.prefWidthProperty().bind(width.multiply(.15));
        fileName.prefWidthProperty().bind(width.multiply(.3));
        fileSize.prefWidthProperty().bind(width.multiply(.15));
        action.prefWidthProperty().bind(width.multiply(.1));
        //绑定数据
        filterJavaVersion.textProperty().bindBidirectional(jdkVersionViewModel.getFilterJavaVersion());
        filterMainVersion.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterMainVersion());
        filterVmVendor.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterVmVendor());
        //变更事件
        filterJavaVersion.textProperty().addListener(str -> jdkVersionViewModel.filter());
        filterJavaVersion.setOnKeyPressed(keyEvent -> jdkVersionViewModel.filter());
        filterJavaVersion.textProperty().addListener(str -> jdkVersionViewModel.filter());
        filterVmVendor.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        filterMainVersion.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        releaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        action.setCellFactory(getTableColumCellFactory());
        tableView.setItems(jdkVersionViewModel.getJavaVersionList());
        filterVmVendor.setItems(jdkVersionViewModel.getVmVendorList());
        filterMainVersion.setItems(jdkVersionViewModel.getMainVersionList());
    }


    /**
     * 获取下载状态列的工厂
     *
     * @return 表格列工厂
     */
    private GraphicTableCellFactory<JdkVersionVO, String> getTableColumCellFactory() {
        //设置单元格工厂
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            JdkVersionVO vo = cellData.getData();
            InstallStatusEnum downloadStatus = vo.getInstallStatus();
            JFXButton downloadButton = new JFXButton();
            switch (downloadStatus) {
                case NO_DOWNLOADED, DOWNLOAD_FAILURE, DOWNLOADED -> {
                    downloadButton = JFXButtonUtil.buildSvgButton("svg/circle-down-regular.svg");
                    downloadButton.setOnAction(event -> {
                        //下载
                        installViewModel.download(vo);
                        //修改成下载中
                        jdkVersionViewModel.changeStatus(cellData.getIndex(), InstallStatusEnum.DOWNLOADING);
                    });
                }
                case DOWNLOADING -> {
                    downloadButton = JFXButtonUtil.buildDynamicButton("pic/downloading.gif");
                    downloadButton.setDisable(true);
                }
            }
            return downloadButton;
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