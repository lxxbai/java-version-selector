
package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.JFXButtonUtil;
import io.github.lxxbai.javaversionselector.component.cell.GraphicTableCellFactory;
import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
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
public class InstallView {

    @Resource
    private InstallViewModel installViewModel;
    @FXML
    private TableView<InstallRecordVO> tableView;
    @FXML
    private TableColumn<InstallRecordVO, String> vmVendor;
    @FXML
    private TableColumn<InstallRecordVO, String> mainVersion;
    @FXML
    private TableColumn<InstallRecordVO, String> javaVersion;
    @FXML
    private TableColumn<InstallRecordVO, String> fileName;
    @FXML
    private TableColumn<InstallRecordVO, String> fileSize;
    @FXML
    private TableColumn<InstallRecordVO, String> action;
    @FXML
    private TableColumn<InstallRecordVO, String> status;

    @FXML
    public void initialize() throws Exception {
        ReadOnlyDoubleProperty width = tableView.widthProperty();
        //设置百分比宽度
        vmVendor.prefWidthProperty().bind(width.multiply(.1));
        mainVersion.prefWidthProperty().bind(width.multiply(.1));
        javaVersion.prefWidthProperty().bind(width.multiply(.1));
        fileName.prefWidthProperty().bind(width.multiply(.2));
        fileSize.prefWidthProperty().bind(width.multiply(.15));
        action.prefWidthProperty().bind(width.multiply(.2));
        status.prefWidthProperty().bind(width.multiply(.15));
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        status.setCellFactory(buildStatusTableCellFactory());
//        action.setCellFactory(buildStatusTableCellFactory());
        tableView.setItems(installViewModel.getDownLoadList());
    }


    private GraphicTableCellFactory<InstallRecordVO, String> buildStatusTableCellFactory() {
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            InstallRecordVO installRecordVO = cellData.getData();
            // 获取当前行数据
            InstallStatusEnum downloadStatus = installRecordVO.getDownloadStatus();
            switch (downloadStatus) {
                case DOWNLOADING, DOWNLOAD_PAUSE -> {
                    return installRecordVO.getDownloadProgressBar().getContent();
                }
                case DOWNLOAD_FAILURE -> {
                    return JFXButtonUtil.buildSvgButton("svg/warn.svg", "下载失败");
                }
                case DOWNLOADED -> {
                    return JFXButtonUtil.buildSvgButton("svg/check-solid.svg", "下载完成");
                }
                case INSTALLING -> {
                    return JFXButtonUtil.buildSvgButton("svg/check-solid.svg", "安装中");
                }
                case INSTALLED -> {
                    return JFXButtonUtil.buildSvgButton("svg/check-solid.svg", "安装完成");
                }
                default -> {
                    return null;
                }
            }
        });
    }
}