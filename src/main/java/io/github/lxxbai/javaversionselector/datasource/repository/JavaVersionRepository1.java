package io.github.lxxbai.javaversionselector.datasource.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.util.HttpUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.JdkVersionDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 所有的java版本列表
 *
 * @author lxxbai
 */
@Repository
public class JavaVersionRepository1 extends InMemoryRepository<JdkVersionDO> {

    @Override
    List<JdkVersionDO> init() {
        // 获取对应操作系统的版本列表
        return refresh(Constants.VERSIONS_URL);
    }

    /**
     * 刷新所有的java版本列表
     *
     * @return java版本列表
     */
    public List<JdkVersionDO> refresh() {
        return init();
    }

    /**
     * 刷新所有的java版本列表
     *
     * @return java版本列表
     */
    public List<JdkVersionDO> refresh(String url) {
        return HttpUtil.get(url, new TypeReference<>() {
        });
    }
}
