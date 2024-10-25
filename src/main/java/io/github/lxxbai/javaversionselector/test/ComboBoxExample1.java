package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;

public class ComboBoxExample1 extends Application {

    @Override
    public void start(Stage stage) {
        VBox main = new VBox();
        main.setSpacing(50);
        TextArea javafxTextArea = new TextArea();
        javafxTextArea.setPromptText("JavaFX Text Area");
        main.getChildren().add(javafxTextArea);
        JFXTextField jfxTextArea = new JFXTextField();
        jfxTextArea.setPromptText("JFoenix Text Area :D");
        jfxTextArea.setLabelFloat(true);
        RequiredFieldValidator validator = new RequiredFieldValidator();
        // NOTE adding error class to text area is causing the cursor to disapper
        validator.setMessage("Please type something!");
        FontIcon warnIcon = new FontIcon(MaterialDesign.MDI_BARCODE);
        warnIcon.getStyleClass().add("error");
        validator.setIcon(warnIcon);
        jfxTextArea.getValidators().add(validator);
        jfxTextArea.focusedProperty().addListener((o, oldVal, newVal) ->
        {
            if (!newVal)
            {
                jfxTextArea.validate();
            }
        });

        main.getChildren().add(jfxTextArea);
        StackPane pane = new StackPane();
        pane.getChildren().add(main);
        StackPane.setMargin(main, new Insets(100));
        pane.setStyle("-fx-background-color:WHITE");
        final Scene scene = new Scene(pane, 800, 600);
        scene.getStylesheets().add(ResourceUtil.toExternalForm("css/jfoenix-components.css"));
        stage.setTitle("JFX Button Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
