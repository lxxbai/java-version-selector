package io.github.lxxbai.javaversionselector.common.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
     * 获取资源路径
     *
     * @param path 路径
     * @return 资源路径
     */
    public static String toExternalForm(String path) {
        return getUrl(path).toExternalForm();
    }
}
