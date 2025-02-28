package io.github.lxxbai.jvs.control;

import io.github.lxxbai.jvs.common.util.ResourceUtil;
import io.github.lxxbai.jvs.spring.FXMLController;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class RedPacketControl implements Initializable {

    public ImageView meiTuan;
    public ImageView elma;
    public ImageView msmds;
    public ImageView msmds2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        meiTuan.setImage(ResourceUtil.toImage("pic/test.png"));
        elma.setImage(ResourceUtil.toImage("pic/test.png"));
        msmds.setImage(ResourceUtil.toImage("pic/test.png"));
        msmds2.setImage(ResourceUtil.toImage("pic/test.png"));
    }
}
