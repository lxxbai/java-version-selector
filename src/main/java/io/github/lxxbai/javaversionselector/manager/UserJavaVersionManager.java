package io.github.lxxbai.javaversionselector.manager;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.SourceEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.enums.VmVendorEnum;
import io.github.lxxbai.javaversionselector.common.util.JdkPropertiesUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.UserJavaVersionDO1;
import io.github.lxxbai.javaversionselector.datasource.mapper.UserJavaVersionMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;


/**
 * 用户版本管理器
 *
 * @author lxxbai
 */
@Component
public class UserJavaVersionManager extends ServiceImpl<UserJavaVersionMapper, UserJavaVersionDO1> {


    /**
     * 初始化已安装的Java版本
     */
    public void initInstalledUserJdk() {
        // 获取已安装的Java版本
        String javaHome = SystemUtil.get(Constants.STR_JAVA_HOME);
        saveLocalInstalledJdk(javaHome);
    }

    /**
     * 保存本地已安装的Java版本
     *
     * @param javaHome javaHome
     */
    public void saveLocalInstalledJdk(String javaHome) {
        if (StrUtil.isBlank(javaHome)) {
            return;
        }
        // 获取JVM属性
        Map<String, String> jvmProperties = JdkPropertiesUtil.getJvmProperties(javaHome);
        //获取版本信息
        String javaVersion = jvmProperties.get("java.version");
        String vendor = jvmProperties.get("java.vm.vendor");
        Integer mainVersionInt = MapUtil.getInt(jvmProperties, "java.class.version");
        String mainVersion = Objects.isNull(mainVersionInt) ? "未知" : String.valueOf(mainVersionInt - 44);
        UserJavaVersionDO1 userJavaVersionDO = new UserJavaVersionDO1();
        userJavaVersionDO.setLocalHomePath(javaHome);
        userJavaVersionDO.setStatus(VersionStatusEnum.INSTALLED.getStatus());
        userJavaVersionDO.setCurrent(true);
        userJavaVersionDO.setSource(SourceEnum.LOCAL.getCode());
        userJavaVersionDO.setVmVendor(VmVendorEnum.getByVmVendor(vendor).getCode());
        userJavaVersionDO.setMainVersion(mainVersion);
        userJavaVersionDO.setJavaVersion(javaVersion);
        //查询是否存在
        boolean exists = lambdaQuery()
                .eq(UserJavaVersionDO1::getUkVersion, userJavaVersionDO.getUkVersion())
                .exists();
        if (exists) {
            return;
        }
        //不存在,保存数据
        this.save(userJavaVersionDO);
    }
}
