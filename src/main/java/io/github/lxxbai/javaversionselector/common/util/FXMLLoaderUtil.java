package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.extra.spring.SpringUtil;
import io.github.lxxbai.javaversionselector.common.exception.ClientException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

public class FXMLLoaderUtil {

    /**
     * 加载fxml
     *
     * @param fxmlPath 路径
     */
    public static <T extends Node> T load(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ResourceUtil.getUrl(fxmlPath));
            loader.setControllerFactory(SpringUtil::getBean);
            return loader.load();
        } catch (IOException e) {
            throw new ClientException("Path_error", "配置错误");
        }
    }

    /**
     * 加载fxml
     *
     * @param url 路径
     */
    public static <T extends Node> T load(URL url) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        return loader.load();
    }
}
