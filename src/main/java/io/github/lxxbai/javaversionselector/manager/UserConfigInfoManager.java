package io.github.lxxbai.javaversionselector.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.lxxbai.javaversionselector.datasource.entity.UserConfigInfoDO;
import io.github.lxxbai.javaversionselector.datasource.mapper.UserConfigInfoMapper;
import org.springframework.stereotype.Component;


/**
 * 配置
 *
 * @author lxxbai
 */
@Component
public class UserConfigInfoManager extends ServiceImpl<UserConfigInfoMapper, UserConfigInfoDO> {

}
