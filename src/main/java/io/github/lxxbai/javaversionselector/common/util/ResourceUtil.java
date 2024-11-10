package io.github.lxxbai.javaversionselector.common.util;

import javafx.scene.image.Image;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.net.URL;

/**
 * 获取资源
 *
 * @author wdc
 */
public class ResourceUtil {

    /**
     * 获取资源
     *
     * @param path 路径
     * @return URL
     */
    public static URL getUrl(String path) {
        try {
            Resource resource = new ClassPathResource(path);
            if (!resource.exists()) {
                throw new RuntimeException("文件不存在");
            }
            return resource.getURL();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取资源
     *
     * @param path 路径
     * @return URL
     */
    public static InputStream getInputStream(String path) {
        try {
            Resource resource = new ClassPathResource(path);
            if (!resource.exists()) {
                throw new RuntimeException("文件不存在");
            }
            return resource.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取资源路径
     *
     * @param path 路径
     * @return 资源路径
     */
    public static String toExternalForm(String path) {
        return getUrl(path).toExternalForm();
    }


    /**
     * 将指定路径的图像加载为Image对象
     *
     * @param path 图像文件的路径
     * @return 返回一个新创建的Image对象
     */
    public static Image toImage(String path) {
        // 使用ResourceUtil类将路径转换为外部形式，然后创建并返回一个新的Image对象
        return new Image(toExternalForm(path));
    }
}
