package io.github.lxxbai.javaversionselector.service;

import io.github.lxxbai.javaversionselector.datasource.entity.UserConfigInfoDO;
import io.github.lxxbai.javaversionselector.manager.UserConfigInfoManager;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author lxxbai
 */
@Service
public class SettingsService {

    @Resource
    private UserConfigInfoManager userConfigInfoManager;

    /**
     * 检查系统是否已经配置
     * 该方法用于判断系统是否已经完成了初始配置通过查询数据库中的配置信息来确定系统状态
     *
     * @return boolean 表示系统是否已经配置如果返回true，表示系统已配置；如果返回false，表示系统未配置
     */
    public boolean configured() {
        // 查询数据库中键为"CONFIGURED"的配置信息，以确定系统是否已经配置
        UserConfigInfoDO userConfigInfo = userConfigInfoManager
                .lambdaQuery()
                .eq(UserConfigInfoDO::getDicKey, "CONFIGURED")
                .one();

        // 如果没有找到配置信息，返回false，表示系统未配置
        if (Objects.isNull(userConfigInfo)) {
            return false;
        }

        // 解析找到的配置信息的值为布尔类型并返回，表示系统已配置
        return Boolean.parseBoolean(userConfigInfo.getDicValue());
    }


    /**
     * 保存配置信息
     *
     * @param configList 配置信息列表
     * @return 成功
     */
    public boolean saveConfigList(List<UserConfigInfoDO> configList) {
        userConfigInfoManager.saveBatch(configList);
        return true;
    }
}
