package io.github.lxxbai.jvs.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.lxxbai.jvs.common.util.ObjectMapperUtil;
import io.github.lxxbai.jvs.datasource.entity.UserConfigInfoDO;
import io.github.lxxbai.jvs.datasource.mapper.UserConfigInfoMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * 配置
 *
 * @author lxxbai
 */
@Component
public class UserConfigInfoManager extends ServiceImpl<UserConfigInfoMapper, UserConfigInfoDO> {


    /**
     * 查询配置信息
     *
     * @param dictKey 配置键
     * @param clazz   类
     * @return 配置信息
     */
    public <T> T queryOneConfig(String dictKey, Class<T> clazz) {
        UserConfigInfoDO oneConfig = lambdaQuery()
                .eq(UserConfigInfoDO::getDicKey, dictKey)
                .one();
        if (Objects.isNull(oneConfig)) {
            return null;
        }
        return ObjectMapperUtil.toObj(oneConfig.getDicValue(), clazz);
    }

    /**
     * 查询配置信息
     *
     * @param dictKey 配置键
     * @return 配置信息
     */
    public String queryStrConfig(String dictKey) {
        UserConfigInfoDO oneConfig = lambdaQuery()
                .eq(UserConfigInfoDO::getDicKey, dictKey)
                .one();
        return oneConfig.getDicValue();
    }
}
