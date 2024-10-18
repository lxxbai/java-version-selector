package io.github.lxxbai.javaversionselector.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.util.HttpUtil;
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
        // 获取版本列表
        List<JavaVersionDO1> versionList = HttpUtil.get(Constants.VERSION_DATA_URL, new TypeReference<>() {
        });
        if (CollUtil.isNotEmpty(versionList)) {
            // 删除所有数据
            this.lambdaUpdate().remove();
            // 添加数据
            this.getBaseMapper().insert(versionList);
        }
    }

    public static void main(String[] args) {
        System.out.println(OshiUtil.getOs().getBitness());
    }
}
