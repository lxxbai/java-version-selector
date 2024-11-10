
package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.AlertUtil;
import io.github.lxxbai.javaversionselector.common.util.DesktopUtil;
import io.github.lxxbai.javaversionselector.common.util.JFXButtonUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.component.DownloadProgressBar;
import io.github.lxxbai.javaversionselector.component.cell.GraphicTableCellFactory;
import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
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
        action.setCellFactory(buildActionCellFactory());
        tableView.setItems(installViewModel.getDownLoadList());
    }


    /**
     * 构建状态列
     *
     * @return 工厂
     */
    private GraphicTableCellFactory<InstallRecordVO, String> buildStatusTableCellFactory() {
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            InstallRecordVO installRecordVO = cellData.getData();
            // 获取当前行数据
            InstallStatusEnum downloadStatus = installRecordVO.getInstallStatus();
            switch (downloadStatus) {
                case DOWNLOADING, DOWNLOAD_PAUSE -> {
                    return installRecordVO.getDownloadProgressBar().getContent();
                }
                case DOWNLOAD_FAILURE -> {
                    return JFXButtonUtil.buildSvgHBox("svg/warn.svg", "下载失败");
                }
                case DOWNLOADED -> {
                    return JFXButtonUtil.buildSvgHBox("svg/check-solid.svg", "下载完成");
                }
                case INSTALLING -> {
                    return JFXButtonUtil.buildSvgHBox("svg/check-solid.svg", "安装中");
                }
                case INSTALLED -> {
                    return JFXButtonUtil.buildSvgHBox("svg/check-solid.svg", "安装完成");
                }
                case INSTALLED_FAILURE -> {
                    return JFXButtonUtil.buildSvgHBox("svg/warn.svg", "安装失败");
                }
                default -> {
                    return null;
                }
            }
        });
    }


    /**
     * 构建操作列
     *
     * @return 工厂
     */
    private GraphicTableCellFactory<InstallRecordVO, String> buildActionCellFactory() {
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            InstallRecordVO installRecordVO = cellData.getData();
            Integer index = cellData.getIndex();
            // 获取当前行数据
            InstallStatusEnum downloadStatus = installRecordVO.getInstallStatus();
            //开始下载按钮，暂停按钮，文件位置按钮，删除按钮
            HBox hBox = new HBox();
            JFXButton filePathButton = buildFilePathButton(installRecordVO);
            JFXButton deleteButton = buildDeleteButton(index, installRecordVO);
            switch (downloadStatus) {
                case DOWNLOADING -> {
                    //暂停按钮
                    JFXButton pauseButton = buildPauseButtonButton(index, installRecordVO);
                    hBox.getChildren().addAll(pauseButton, filePathButton, deleteButton);
                }
                case DOWNLOAD_PAUSE -> {
                    //开始下载按钮
                    JFXButton startButton = buildStartButton(installRecordVO);
                    hBox.getChildren().addAll(startButton, filePathButton, deleteButton);
                }
                case DOWNLOAD_FAILURE -> {
                    JFXButton retryButton = buildRetryButton(installRecordVO);
                    hBox.getChildren().addAll(retryButton, filePathButton, deleteButton);
                }
                case INSTALLED_FAILURE, DOWNLOADED -> {
                    JFXButton installButton = buildInstallButton(installRecordVO);
                    hBox.getChildren().addAll(installButton, filePathButton, deleteButton);
                }
                case INSTALLING -> hBox.getChildren().addAll(filePathButton);
                case INSTALLED -> hBox.getChildren().addAll(filePathButton, deleteButton);
            }
            return hBox;
        });
    }


    /**
     * 构建文件位置按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildFilePathButton(InstallRecordVO installRecordVO) {
        JFXButton filePathButton = JFXButtonUtil.buildSvgButton("svg/folder-regular.svg", "打开文件地址");
        filePathButton.setOnAction(event ->
                Platform.runLater(() -> {
                    try {
                        DesktopUtil.openFileDirectory(new File(installRecordVO.getJdkPackageUrl()));
                    } catch (Exception e) {
                        AlertUtil.showError(StageUtil.getPrimaryStage(), "打开文件失败", "打开文件失败", "文件不存在!");
                    }
                }));
        return filePathButton;
    }

    /**
     * 构建删除按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildDeleteButton(int index, InstallRecordVO installRecordVO) {
        JFXButton deleteButton = JFXButtonUtil.buildSvgButton("svg/trash-solid.svg", "删除");
        deleteButton.setOnAction(event -> {
            //删除文件
            installViewModel.deleteRecord(index, installRecordVO);
        });
        return deleteButton;
    }


    /**
     * 构建暂停按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildPauseButtonButton(int index, InstallRecordVO installRecordVO) {
        JFXButton pauseButton = JFXButtonUtil.buildSvgButton("svg/pause-solid.svg", "暂停");
        pauseButton.setOnAction(event -> {
            DownloadProgressBar downloadProgressBar = installRecordVO.getDownloadProgressBar();
            if (Objects.nonNull(downloadProgressBar)) {
                downloadProgressBar.cancel();
            }
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOAD_PAUSE);
            installViewModel.changeStatus(index, installRecordVO);
        });
        return pauseButton;
    }

    /**
     * 构建开始按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildStartButton(InstallRecordVO installRecordVO) {
        JFXButton startButton = JFXButtonUtil.buildSvgButton("svg/play-solid.svg", "开始");
        startButton.setOnAction(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOADING);
            installViewModel.changeStatus(installRecordVO);
        });
        return startButton;
    }

    /**
     * 构建开始按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildRetryButton(InstallRecordVO installRecordVO) {
        JFXButton retryButton = JFXButtonUtil.buildSvgButton("svg/repeat-solid.svg", "重试");
        retryButton.setOnAction(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOADING);
            installViewModel.changeStatus(installRecordVO);
        });
        return retryButton;
    }

    /**
     * 构建安装按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildInstallButton(InstallRecordVO installRecordVO) {
        JFXButton installButton = JFXButtonUtil.buildSvgButton("svg/install-solid.svg", "安装");
        installButton.setOnAction(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLING);
            installViewModel.install(installRecordVO);
        });
        return installButton;
    }
}