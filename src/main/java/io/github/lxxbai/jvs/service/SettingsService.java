package io.github.lxxbai.jvs.service;

import io.github.lxxbai.jvs.datasource.entity.UserConfigInfoDO;
import io.github.lxxbai.jvs.manager.UserConfigInfoManager;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lxxbai
 */
@Service
public class SettingsService {

    @Resource
    private UserConfigInfoManager userConfigInfoManager;

    /**
     * 查询配置信息
     *
     * @param dictKey 配置键
     * @param clazz   类
     * @return 配置信息
     */
    public <T> T queryOneConfig(String dictKey, Class<T> clazz) {
        return userConfigInfoManager.queryOneConfig(dictKey, clazz);
    }

    /**
     * 查询配置信息
     *
     * @param dictKey 配置键
     * @return 配置信息
     */
    public String queryStrConfig(String dictKey) {
        return userConfigInfoManager.queryStrConfig(dictKey);
    }


    /**
     * 保存配置信息
     *
     * @param key   配置键
     * @param value 配置值
     */
    public void saveConfig(String key, String value) {
        //查询是否存在
        boolean exists = userConfigInfoManager
                .lambdaQuery()
                .eq(UserConfigInfoDO::getDicKey, key)
                .exists();
        if (exists) {
            userConfigInfoManager.lambdaUpdate()
                    .set(UserConfigInfoDO::getDicValue, value)
                    .eq(UserConfigInfoDO::getDicKey, key)
                    .update();
        } else {
            UserConfigInfoDO userConfigInfoDO = new UserConfigInfoDO();
            userConfigInfoDO.setDicKey(key);
            userConfigInfoDO.setDicValue(value);
            userConfigInfoManager.save(userConfigInfoDO);
        }
    }
}
