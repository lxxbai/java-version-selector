package io.github.lxxbai.jvs.control;

import io.github.lxxbai.jvs.common.util.ResourceUtil;
import io.github.lxxbai.jvs.spring.FXMLController;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class AdsControl implements Initializable {

    public WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String externalForm = ResourceUtil.toExternalForm("html/local.html");
        webView.getEngine().load(externalForm);
    }
}
