package io.github.lxxbai.jvs.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.lxxbai.jvs.common.Constants;
import io.github.lxxbai.jvs.common.util.HttpUtil;
import io.github.lxxbai.jvs.datasource.entity.JdkVersionDO;
import io.github.lxxbai.jvs.datasource.entity.UserConfigInfoDO;
import io.github.lxxbai.jvs.datasource.mapper.JdkVersionMapper;
import io.github.lxxbai.jvs.datasource.mapper.UserConfigInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


/**
 * 版本管理器处理
 *
 * @author lxxbai
 */
@Component
public class JdkVersionManager extends ServiceImpl<JdkVersionMapper, JdkVersionDO> {

    @Resource
    private UserConfigInfoMapper userConfigInfoMapper;

    /**
     * 刷新版本列表
     */
    public void refresh() {
        UserConfigInfoDO lastLoadDate = userConfigInfoMapper.selectOne(Wrappers.<UserConfigInfoDO>lambdaQuery()
                .eq(UserConfigInfoDO::getDicKey, "LAST_LOAD_DATE"));
        if (Objects.isNull(lastLoadDate)) {
            lastLoadDate = new UserConfigInfoDO();
            lastLoadDate.setDicKey("LAST_LOAD_DATE");
            lastLoadDate.setDicValue(null);
            userConfigInfoMapper.insert(lastLoadDate);
        }
        //存在并且刷新过的数据
        if (StrUtil.equals(lastLoadDate.getDicValue(), DateUtil.formatDate(DateUtil.date()))) {
            return;
        }
        // 获取版本列表
        List<JdkVersionDO> versionList = HttpUtil.get(Constants.VERSION_DATA_URL, new TypeReference<>() {
        });
        if (CollUtil.isNotEmpty(versionList)) {
            // 删除所有数据
            this.lambdaUpdate().remove();
            // 添加数据
            this.getBaseMapper().insert(versionList);
        }
        lastLoadDate.setDicValue(DateUtil.formatDate(DateUtil.date()));
        userConfigInfoMapper.updateById(lastLoadDate);
    }
}
