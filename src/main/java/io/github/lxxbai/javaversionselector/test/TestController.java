package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    @FXML
    private JFXTextField usernameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化 Validator
        RequiredFieldValidator validator = new RequiredFieldValidator();
        // NOTE adding error class to text area is causing the cursor to disapper
        validator.setMessage("Please type something!");
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setIcon(warnIcon);
        usernameField.getValidators().add(validator);
        usernameField.focusedProperty().addListener((o, oldVal, newVal) ->
        {
            if (!newVal)
            {
                usernameField.validate();
            }
        });
    }

    @FXML
    private void onSubmit() {
        if (usernameField.validate()) {
            System.out.println("Validation passed: " + usernameField.getText());
        } else {
            System.out.println("Validation failed");
        }
    }
}