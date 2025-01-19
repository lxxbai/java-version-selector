package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.jvs.common.enums.InstallStatusEnum;
import io.github.lxxbai.jvs.common.util.DesktopUtil;
import io.github.lxxbai.jvs.common.util.JFXButtonUtil;
import io.github.lxxbai.jvs.common.util.JFXMsgAlertUtil;
import io.github.lxxbai.jvs.component.SvgButton;
import io.github.lxxbai.jvs.component.XxbDownloadBar;
import io.github.lxxbai.jvs.component.cell.XxbTableCellFactory;
import io.github.lxxbai.jvs.model.InstallRecordVO;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.InstallView;
import io.github.lxxbai.jvs.view.model.InstallViewModel;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class JdkInstallControl implements Initializable {

    @Resource
    private InstallView installView;

    @Resource
    private InstallViewModel installViewModel;
    @FXML
    public JFXTextField versionFilter;
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
    private TableColumn<InstallRecordVO, String> status;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        status.setCellFactory(buildStatusCellFactory());
        fileSize.setCellFactory(buildActionCellFactory());
        tableView.setItems(installViewModel.getDownLoadList());
        //绑定数据
        versionFilter.textProperty().bindBidirectional(installViewModel.getFilterJavaVersion());
        //变更事件
        versionFilter.textProperty().addListener((observable, oldValue, newValue) -> installViewModel.filter());
        //绑定下载数量
        installView.getSvgBadgeMenuItem().getBadge().numProperty().bindBidirectional(installViewModel.downloadCountProperty());
    }

    /**
     * 构建状态列
     *
     * @return 工厂
     */
    private XxbTableCellFactory<InstallRecordVO, String> buildStatusCellFactory() {
        return XxbTableCellFactory.cellFactory(cell -> {
            InstallRecordVO installRecordVO = cell.getData();
            // 获取当前行数据
            InstallStatusEnum downloadStatus = installRecordVO.getInstallStatus();
            Node node = switch (downloadStatus) {
                case DOWNLOADING, DOWNLOAD_PAUSE -> installRecordVO.getXxbDownloadBar().getDownloadBar();
                case DOWNLOAD_FAILURE -> JFXButtonUtil.buildReadOnlyButton("下载失败", "red");
                case DOWNLOADED -> JFXButtonUtil.buildReadOnlyButton("下载完成", "green");
                case INSTALLING -> JFXButtonUtil.buildReadOnlyButton("安装中", "pink");
                case INSTALLED -> JFXButtonUtil.buildReadOnlyButton("安装完成", "green");
                case INSTALLED_FAILURE -> JFXButtonUtil.buildReadOnlyButton("安装失败", "red");
                default -> JFXButtonUtil.buildReadOnlyButton("未知失败", "red");
            };
            cell.setGraphic(node);
            cell.setText(null);
        });
    }

    /**
     * 构建状态列
     *
     * @return 工厂
     */
    private XxbTableCellFactory<InstallRecordVO, String> buildActionCellFactory() {
        return XxbTableCellFactory.cellFactoryWithRowHover(cell -> {
            InstallRecordVO installRecordVO = cell.getData();
            if (!cell.getTableRow().isHover()) {
                cell.setText(installRecordVO.getFileSize());
                cell.setGraphic(null);
                return;
            }
            // 获取当前行数据
            InstallStatusEnum downloadStatus = installRecordVO.getInstallStatus();
            //开始下载按钮，暂停按钮，文件位置按钮，删除按钮
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER_LEFT);
            JFXButton filePathButton = buildFilePathButton(installRecordVO);
            JFXButton deleteButton = buildDeleteButton(cell.getCurrentIndex(), installRecordVO);
            int index = cell.getCurrentIndex();
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
                case INSTALLING, INSTALLED -> hBox.getChildren().addAll(filePathButton, deleteButton);
            }
            cell.setGraphic(hBox);
            cell.setText(null);
        });
    }


    /**
     * 构建文件位置按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildFilePathButton(InstallRecordVO installRecordVO) {
        SvgButton filePathButton = new SvgButton("svg/folder-open-solid.svg", "打开文件");
        filePathButton.setOnAction(event -> DesktopUtil.asyncOpenFileDirectory(installRecordVO.getJdkPackageUrl()));
        return filePathButton;
    }

    /**
     * 构建删除按钮
     *
     * @param installRecordVO 数据
     * @return JFXButton
     */
    private JFXButton buildDeleteButton(int index, InstallRecordVO installRecordVO) {
        SvgButton deleteButton = new SvgButton("svg/trash-solid.svg", "删除");
        deleteButton.getStyleClass().add("warn-hover");
        deleteButton.setOnAction(event -> {
            Boolean deleteFlag = JFXMsgAlertUtil.showSelectInfo(GUIState.getStage(), "提示", "确定删除吗？");
            if (deleteFlag) {
                //删除文件
                installViewModel.deleteRecord(index, installRecordVO);
            }
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
        SvgButton pauseButton = new SvgButton("svg/pause-solid.svg", "暂停");
        pauseButton.setOnAction(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOAD_PAUSE);
            installViewModel.changeStatus(index, installRecordVO);
            XxbDownloadBar xxDownloadBar = installRecordVO.getXxbDownloadBar();
            if (Objects.nonNull(xxDownloadBar)) {
                xxDownloadBar.cancel();
            }
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
        SvgButton startButton = new SvgButton("svg/play-solid.svg", "开始");
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
        SvgButton retryButton = new SvgButton("svg/repeat-solid.svg", "重新下载");
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
        SvgButton installButton = new SvgButton("svg/install-solid.svg", "安装");
        installButton.setOnAction(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLING);
            installViewModel.install(installRecordVO);
        });
        return installButton;
    }
}