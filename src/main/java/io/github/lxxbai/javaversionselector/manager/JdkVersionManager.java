package io.github.lxxbai.javaversionselector.manager;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.util.HttpUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.JdkVersionDO;
import io.github.lxxbai.javaversionselector.datasource.mapper.JdkVersionMapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 版本管理器处理
 *
 * @author lxxbai
 */
@Component
public class JdkVersionManager extends ServiceImpl<JdkVersionMapper, JdkVersionDO> {

    /**
     * 刷新版本列表
     */
    public void refresh() {
        // 获取版本列表
        List<JdkVersionDO> versionList = HttpUtil.get(Constants.VERSION_DATA_URL, new TypeReference<>() {
        });
        if (CollUtil.isNotEmpty(versionList)) {
            // 删除所有数据
            this.lambdaUpdate().remove();
            // 添加数据
            this.getBaseMapper().insert(versionList);
        }
    }
}
