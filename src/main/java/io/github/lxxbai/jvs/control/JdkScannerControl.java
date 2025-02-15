package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSpinner;
import io.github.lxxbai.jvs.common.model.JdkInfo;
import io.github.lxxbai.jvs.common.util.JFXMsgAlertUtil;
import io.github.lxxbai.jvs.common.util.ThreadPoolUtil;
import io.github.lxxbai.jvs.component.XxbAlert;
import io.github.lxxbai.jvs.component.builder.XxbAlertBuilder;
import io.github.lxxbai.jvs.component.cell.XxbTableCellFactory;
import io.github.lxxbai.jvs.component.task.JdkScannerTask;
import io.github.lxxbai.jvs.event.JdkScannerEvent;
import io.github.lxxbai.jvs.model.JdkInfoVO;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.JdkScannerView;
import io.github.lxxbai.jvs.view.model.JdkScannerViewModel;
import io.github.lxxbai.jvs.view.model.UserJdkViewModel;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class JdkScannerControl implements Initializable {

    @Resource
    private JdkScannerViewModel jdkScannerViewModel;

    @Resource
    private UserJdkViewModel userJdkViewModel;

    @Resource
    private JdkScannerView jdkScannerView;
    @FXML
    public TableView<JdkInfoVO> tableView;
    @FXML
    public TableColumn<JdkInfoVO, Boolean> selectedFlag;
    @FXML
    public TableColumn<JdkInfoVO, String> vmVendor;
    @FXML
    public TableColumn<JdkInfoVO, String> mainVersion;
    @FXML
    public TableColumn<JdkInfoVO, String> javaVersion;
    @FXML
    public TableColumn<JdkInfoVO, String> javaHome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        javaHome.setCellValueFactory(new PropertyValueFactory<>("localHomePath"));
        selectedFlag.setCellValueFactory(new PropertyValueFactory<>("selectedFlag"));
        selectedFlag.setCellFactory(XxbTableCellFactory.cellFactory(cell -> {
            JFXCheckBox checkBox = new JFXCheckBox();
            checkBox.setSelected(cell.getData().isSelectedFlag());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                    cell.getData().setSelectedFlag(newValue));
            cell.setGraphic(checkBox);
        }));
        tableView.setItems(jdkScannerViewModel.getJavaVersionList());
    }


    /**
     * 扫描本地Jdk
     */
    @EventListener(value = JdkScannerEvent.class)
    public void scanLocalJdk() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("请选择要扫描的文件夹");
        File selectedDirectory = directoryChooser.showDialog(GUIState.getStage().getScene().getWindow());
        if (Objects.isNull(selectedDirectory)) {
            return;
        }
        //显示进度条
        HBox hbox = new HBox();
        JFXSpinner progressIndicator = new JFXSpinner();
        progressIndicator.setRadius(5);
        hbox.getChildren().addAll(new Label("扫描中..."), progressIndicator);
        hbox.setSpacing(10);
        //扫描任务
        JdkScannerTask jdkScannerTask = new JdkScannerTask(selectedDirectory);
        //弹框内容
        XxbAlert xxbAlert = XxbAlertBuilder.builder().window(GUIState.getStage())
                .title("扫描中", "svg/info.svg")
                .content(hbox)
                .build();
        JFXButton button = xxbAlert.addCancelButton(true);
        button.setOnAction(event -> {
            jdkScannerTask.cancel();
            xxbAlert.close();
        });
        //扫描完成
        jdkScannerTask.setOnSucceeded(event -> {
            xxbAlert.close();
            List<JdkInfo> jdkInfoList = jdkScannerTask.getJdkInfoList();
            Platform.runLater(() -> {
                if (jdkInfoList.isEmpty()) {
                    JFXMsgAlertUtil.showWarning(GUIState.getStage(), "提示", "未找到任何Jdk!");
                    return;
                }
                //展示选择jdk弹框
                showSelectJdk(jdkInfoList);
            });
        });
        //扫描取消
        jdkScannerTask.setOnCancelled(event -> xxbAlert.close());
        //扫描失败
        jdkScannerTask.setOnFailed(event -> {
            xxbAlert.close();
            Platform.runLater(() -> JFXMsgAlertUtil.showError(GUIState.getStage(), "错误", "发生异常!"));
        });
        //关闭窗口取消任务
        xxbAlert.setOnCloseRequest(dialogEvent -> {
            if (jdkScannerTask.isRunning()) {
                jdkScannerTask.cancel();
            }
        });
        ThreadPoolUtil.execute(jdkScannerTask);
        xxbAlert.showAndWait();
    }


    /**
     * 展示选择jdk弹框,并选择导入的数据
     *
     * @param jdkInfoList jdk信息
     */
    private void showSelectJdk(List<JdkInfo> jdkInfoList) {
        //数据设置
        jdkScannerViewModel.refresh(jdkInfoList);
        //展示
        XxbAlert selectAlert = XxbAlertBuilder.builder().title("请选择导入的Jdk")
                .window(GUIState.getStage())
                .content(jdkScannerView.getView(), 600)
                .build();
        JFXButton cancelButton = selectAlert.addCancelButton(false);
        JFXButton okButton = selectAlert.addOkButton(true);
        cancelButton.setOnAction(event -> selectAlert.close());
        okButton.setOnAction(event -> {
            //检查是否选中
            if (tableView.getItems().stream().noneMatch(JdkInfoVO::isSelectedFlag)) {
                JFXMsgAlertUtil.showWarning(GUIState.getStage(), "提示", "请选择要导入的Jdk!");
                return;
            }
            //获取选中的Jdk,保存数据
            userJdkViewModel.save(jdkScannerViewModel.getSelectedList());
            selectAlert.close();
        });
        selectAlert.showAndWait();
    }
}
