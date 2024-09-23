package io.github.lxxbai.javaversionselector.datasource.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.util.HttpUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.JavaVersionDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 所有的java版本列表
 *
 * @author lxxbai
 */
@Repository
public class JavaVersionRepository extends InMemoryRepository<JavaVersionDO> {

    @Override
    List<JavaVersionDO> init() {
        return refresh(Constants.VERSIONS_URL);
    }

    /**
     * 刷新所有的java版本列表
     *
     * @return java版本列表
     */
    public List<JavaVersionDO> refresh() {
        return init();
    }

    /**
     * 刷新所有的java版本列表
     *
     * @return java版本列表
     */
    public List<JavaVersionDO> refresh(String url) {
        return HttpUtil.get(url, new TypeReference<>() {
        });
    }
}
