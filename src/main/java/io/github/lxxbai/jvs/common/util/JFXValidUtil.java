package io.github.lxxbai.jvs.common.util;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @author lxxbai
 */
public class JFXValidUtil {


    /**
     * 默认的必填校验器
     *
     * @param message 提示信息
     * @return 校验器
     */
    public static RequiredFieldValidator defaultValidator(String message) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage(message);
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        requiredFieldValidator.setIcon(warnIcon);
        return requiredFieldValidator;
    }

    /**
     * 默认的必填校验器
     *
     * @param message 提示信息
     * @param field   字段
     */
    public static void defaultValidator(JFXTextField field, String message) {
        RequiredFieldValidator requiredFieldValidator = defaultValidator(message);
        field.getValidators().add(requiredFieldValidator);
        field.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                field.validate();
            }
        });
    }
}
