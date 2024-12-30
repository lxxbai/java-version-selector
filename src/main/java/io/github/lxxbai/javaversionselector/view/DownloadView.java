package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * @author lxxbai
 */
public class DownloadView {

    @FXML
    private JFXDialogLayout downloadDialog;

    @FXML
    private JFXCheckBox defaultPathCheckBox;

    @FXML
    private JFXTextField downloadPathField;

    @FXML
    private JFXTextField jdkPathField;

    @FXML
    public void initialize() {
        //检查配置
    }

    @FXML
    private void chooseDownloadDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件存储路径");
        File selectedDirectory = directoryChooser.showDialog(downloadPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            downloadPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void chooseJdkDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件安装路径");
        File selectedDirectory = directoryChooser.showDialog(jdkPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            jdkPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }


    public void download(ActionEvent actionEvent) {
    }
}