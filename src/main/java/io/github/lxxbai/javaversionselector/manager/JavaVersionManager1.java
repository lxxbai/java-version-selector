package io.github.lxxbai.javaversionselector.manager;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.util.HttpUtil;
import io.github.lxxbai.javaversionselector.common.util.OsUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.JavaVersionDO1;
import io.github.lxxbai.javaversionselector.datasource.mapper.JavaVersionMapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 版本管理器处理
 *
 * @author lxxbai
 */
@Component
public class JavaVersionManager1 extends ServiceImpl<JavaVersionMapper, JavaVersionDO1> {

    /**
     * 刷新版本列表
     */
    public void refresh() {
        // 获取当前操作系统
        String osName = OsUtil.getOsName();
        // 获取对应操作系统的版本列表
        String jsonUrl = Constants.OS_VERSIONS_URL.formatted(osName);
        // 获取版本列表
        List<JavaVersionDO1> versionList = HttpUtil.get(jsonUrl, new TypeReference<>() {
        });
        if (CollUtil.isNotEmpty(versionList)) {
            // 删除所有数据
            this.lambdaUpdate().remove();
            // 添加数据
            this.getBaseMapper().insert(versionList);
        }
    }
}
