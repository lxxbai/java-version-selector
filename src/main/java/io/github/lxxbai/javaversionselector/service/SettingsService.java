package io.github.lxxbai.javaversionselector.service;

import io.github.lxxbai.javaversionselector.common.util.ObjectMapperUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.UserConfigInfoDO;
import io.github.lxxbai.javaversionselector.manager.UserConfigInfoManager;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
        return userConfigInfoManager
                .lambdaQuery()
                .eq(UserConfigInfoDO::getDicKey, "DOWNLOAD_CONFIG")
                .exists();
    }

    /**
     * 查询配置信息
     *
     * @param dictKey 配置键
     * @param clazz   类
     * @return 配置信息
     */
    public <T> T queryOneConfig(String dictKey, Class<T> clazz) {
        UserConfigInfoDO oneConfig = userConfigInfoManager
                .lambdaQuery()
                .eq(UserConfigInfoDO::getDicKey, dictKey)
                .one();
        if (Objects.isNull(oneConfig)) {
            return null;
        }
        return ObjectMapperUtil.toObj(oneConfig.getDicValue(), clazz);
    }


    /**
     * 保存配置信息
     *
     * @param key   配置键
     * @param value 配置值
     * @return 成功
     */
    public boolean saveConfig(String key, String value) {
        UserConfigInfoDO userConfigInfoDO = new UserConfigInfoDO();
        userConfigInfoDO.setDicKey(key);
        userConfigInfoDO.setDicValue(value);
        return true;
    }
}
