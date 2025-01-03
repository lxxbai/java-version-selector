package io.github.lxxbai.jvs.common.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.lxxbai.jvs.common.annotations.base.FXView;
import io.github.lxxbai.jvs.common.exception.ClientException;
import io.github.lxxbai.jvs.model.ViewResult;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * @author lxxbai
 */
@Slf4j
public class FXMLLoaderUtil {

    /**
     * 加载fxml
     *
     * @param fxmlPath 路径
     */
    public static <T extends Node> T loadWithSpring(String fxmlPath) {
        try {
            FXMLLoader loader = loadLoader(fxmlPath);
            loader.setControllerFactory(SpringUtil::getBean);
            return loader.load();
        } catch (IOException e) {
            log.error("异常:", e);
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


    /**
     * 加载fxml
     *
     * @param fxmlPath 路径
     */
    public static <T extends Node> T load(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = loadLoader(fxmlPath);
            return fxmlLoader.load();
        } catch (IOException e) {
            log.error("异常:", e);
            throw new ClientException("Path_error", "配置错误");
        }
    }

    /**
     * 加载fxml
     *
     * @param fxmlPath 路径
     */
    public static FXMLLoader loadLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtil.getUrl(fxmlPath));
        return loader;
    }


    /**
     * 加载fxml
     */
    public static <T, N extends Node> ViewResult<T, N> loadFxView(Class<T> clazz) {
        ViewResult<T, N> result = new ViewResult<>();
        result.setController(SpringUtil.getBean(clazz));
        FXMLLoader loader = new FXMLLoader();
        FXView fxView = AnnotationUtil.getAnnotation(clazz, FXView.class);
        if (Objects.nonNull(fxView) && StrUtil.isNotBlank(fxView.url())) {
            loader.setLocation(ResourceUtil.getUrl(fxView.url()));
        }
        loader.setControllerFactory(SpringUtil::getBean);
        try {
            result.setViewNode(loader.load());
        } catch (IOException e) {
            log.error("异常:", e);
            throw new ClientException("Path_error", "配置错误");
        }
        return result;
    }
}
