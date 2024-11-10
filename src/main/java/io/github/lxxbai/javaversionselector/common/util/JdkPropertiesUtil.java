package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.util.RuntimeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * jdk 信息解析
 *
 * @author lxxbai
 */
public class JdkPropertiesUtil {

    /**
     * 使用 RuntimeUtil 执行 java -XshowSettings:properties -version 命令
     * 并将结果解析为 Map<String, String>
     *
     * @param homePath java home 路径
     */
    public static Map<String, String> getJvmProperties(String homePath) {
        // 执行命令并捕获输出
        String result = RuntimeUtil.execForStr(homePath + "\\bin\\java", "-XshowSettings:properties", "-version");
        // 创建一个 Map 来存储键值对
        Map<String, String> propertiesMap = new HashMap<>();
        // 分行处理输出，并解析为 key = value 格式
        String[] lines = result.split("\n");
        for (String line : lines) {
            // 判断是否包含 "="，即 key = value 格式
            if (line.contains("=")) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    propertiesMap.put(key, value);
                }
            }
        }
        return propertiesMap;
    }
}
