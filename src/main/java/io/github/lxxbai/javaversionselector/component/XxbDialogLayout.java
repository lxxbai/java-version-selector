package io.github.lxxbai.javaversionselector.component;

import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author lxxbai
 */
public class XxbDialogLayout extends JFXDialogLayout {

    private final SimpleBooleanProperty closeSuper = new SimpleBooleanProperty(false);

    public SimpleBooleanProperty closeSuperProperty() {
        return closeSuper;
    }

    public void closeSuper() {
        closeSuper.setValue(true);
    }
}
