package io.github.lxxbai.javaversionselector.datasource.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.util.HttpUtil;
import io.github.lxxbai.javaversionselector.common.util.OsUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.JavaVersionDO;
import io.github.lxxbai.javaversionselector.datasource.entity.JavaVersionDO1;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 所有的java版本列表
 *
 * @author lxxbai
 */
@Repository
public class JavaVersionRepository1 extends InMemoryRepository<JavaVersionDO1> {

    @Override
    List<JavaVersionDO1> init() {
        // 获取当前操作系统
        String osName = OsUtil.getOsName();
        // 获取对应操作系统的版本列表
        String jsonUrl = Constants.OS_VERSIONS_URL.formatted(osName);
        return refresh(jsonUrl);
    }

    /**
     * 刷新所有的java版本列表
     *
     * @return java版本列表
     */
    public List<JavaVersionDO1> refresh() {
        return init();
    }

    /**
     * 刷新所有的java版本列表
     *
     * @return java版本列表
     */
    public List<JavaVersionDO1> refresh(String url) {
        return HttpUtil.get(url, new TypeReference<>() {
        });
    }
}
