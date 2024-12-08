package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.JFXButtonUtil;
import io.github.lxxbai.javaversionselector.component.cell.GraphicTableCellFactory;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import jakarta.annotation.Resource;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;


/**
 * @author lxxbai
 */
@FXView(url = "view/java_version1.fxml")
@Component
public class JdkVersionView1 {

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
    private MFXTableView<JdkVersionVO> tableView;
    @FXML
    private JFXButton resetButton;
    @FXML
    private JFXButton refreshButton;

    @FXML
    public void initialize() {

        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(true))
                .setDeploy(true)
                .setResolveAssets(true)
                .build()
                .setGlobal();
        //禁用焦点
        ReadOnlyDoubleProperty width = tableView.widthProperty();
        //设置百分比宽度
        MFXTableColumn<JdkVersionVO> vmVendor = new MFXTableColumn<>("vmVendor", false);
        MFXTableColumn<JdkVersionVO> mainVersion= new MFXTableColumn<>("vmVendor", false);
        MFXTableColumn<JdkVersionVO> javaVersion= new MFXTableColumn<>("vmVendor", false);
        MFXTableColumn<JdkVersionVO> releaseDate= new MFXTableColumn<>("vmVendor", false);
        MFXTableColumn<JdkVersionVO> fileName= new MFXTableColumn<>("vmVendor", false);
        MFXTableColumn<JdkVersionVO> fileSize= new MFXTableColumn<>("vmVendor", false);
        MFXTableColumn<JdkVersionVO> action= new MFXTableColumn<>("vmVendor", false);
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
        vmVendor.setRowCellFactory(x->new MFXTableRowCell<>(JdkVersionVO::getVmVendor));
        mainVersion.setRowCellFactory(x->new MFXTableRowCell<>(JdkVersionVO::getMainVersion));
        javaVersion.setRowCellFactory(x->new MFXTableRowCell<>(JdkVersionVO::getJavaVersion));
        releaseDate.setRowCellFactory(x->new MFXTableRowCell<>(JdkVersionVO::getReleaseDate));
        fileName.setRowCellFactory(x->new MFXTableRowCell<>(JdkVersionVO::getFileName));
        fileSize.setRowCellFactory(x->new MFXTableRowCell<>(JdkVersionVO::getFileSize));
        tableView.getTableColumns().addAll(vmVendor, mainVersion, javaVersion,releaseDate,fileName,fileSize);
//        action.setRowCellFactory(buildActionCellFactory());
        tableView.setItems(jdkVersionViewModel.getJavaVersionList());
        filterVmVendor.setItems(jdkVersionViewModel.getVmVendorList());
        filterMainVersion.setItems(jdkVersionViewModel.getMainVersionList());
        JFXButtonUtil.fullSvg(resetButton, "svg/rotate-solid.svg", "重置");
        JFXButtonUtil.fullSvg(refreshButton, "svg/rotate-solid.svg", "刷新");
    }


    /**
     * 获取下载状态列的工厂
     *
     * @return 表格列工厂
     */
    private GraphicTableCellFactory<JdkVersionVO, String> buildActionCellFactory() {
        //设置单元格工厂,只有不是安装中的时候才显示下载按钮
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            JdkVersionVO vo = cellData.getData();
            InstallStatusEnum downloadStatus = vo.getInstallStatus();
            JFXButton downloadButton;
            if (downloadStatus == InstallStatusEnum.DOWNLOADING) {
                downloadButton = JFXButtonUtil.buildDynamicButton("pic/downloading.gif");
                downloadButton.setDisable(true);
            } else {
                downloadButton = JFXButtonUtil.buildSvgButton("svg/circle-down-regular.svg");
                //下载
                downloadButton.setOnAction(event -> installViewModel.download(vo));
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