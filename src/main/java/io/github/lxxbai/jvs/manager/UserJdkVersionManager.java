package io.github.lxxbai.jvs.manager;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.lxxbai.jvs.common.Constants;
import io.github.lxxbai.jvs.common.enums.ApplyStatusEnum;
import io.github.lxxbai.jvs.common.enums.SourceEnum;
import io.github.lxxbai.jvs.common.enums.VmVendorEnum;
import io.github.lxxbai.jvs.common.util.JdkPropertiesUtil;
import io.github.lxxbai.jvs.datasource.entity.UserJdkVersionDO;
import io.github.lxxbai.jvs.datasource.mapper.UserJdkVersionMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;


/**
 * 用户版本管理器
 *
 * @author lxxbai
 */
@Component
public class UserJdkVersionManager extends ServiceImpl<UserJdkVersionMapper, UserJdkVersionDO> {


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
        buildUserJdk(javaHome);
    }

    /**
     * 构建用户版本
     *
     * @param javaHome javaHome
     */
    public void buildUserJdk(String javaHome) {
        if (StrUtil.isBlank(javaHome)) {
            return;
        }
        boolean current = false;
        String currentJavaHome = SystemUtil.get(Constants.STR_JAVA_HOME);
        if (StrUtil.equals(currentJavaHome, javaHome)) {
            current = true;
        }
        // 获取JVM属性
        Map<String, String> jvmProperties = JdkPropertiesUtil.getJvmProperties(javaHome);
        //获取版本信息
        String javaVersion = jvmProperties.get("java.version");
        String vendor = jvmProperties.get("java.vm.vendor");
        Integer mainVersionInt = MapUtil.getInt(jvmProperties, "java.class.version");
        String mainVersion = Objects.isNull(mainVersionInt) ? "未知" : "JDK" + (mainVersionInt - 44);
        UserJdkVersionDO userJavaVersionDO = new UserJdkVersionDO();
        userJavaVersionDO.setLocalHomePath(javaHome);
        userJavaVersionDO.setStatus(current ? ApplyStatusEnum.CURRENT.getStatus() : ApplyStatusEnum.NOT_APPLY.getStatus());
        userJavaVersionDO.setCurrent(true);
        userJavaVersionDO.setSource(SourceEnum.LOCAL.getCode());
        userJavaVersionDO.setVmVendor(VmVendorEnum.getByVmVendor(vendor).getCode());
        userJavaVersionDO.setMainVersion(mainVersion);
        userJavaVersionDO.setJavaVersion(javaVersion);
        if (current) {
            //修改其他的为未应用
            lambdaUpdate()
                    .eq(UserJdkVersionDO::getStatus, ApplyStatusEnum.CURRENT.getStatus())
                    .set(UserJdkVersionDO::getStatus, ApplyStatusEnum.NOT_APPLY.getStatus())
                    .update();
        }
        //查询是否存在
        UserJdkVersionDO existJdk = lambdaQuery()
                .eq(UserJdkVersionDO::getUkVersion, userJavaVersionDO.getUkVersion())
                .eq(UserJdkVersionDO::getLocalHomePath, userJavaVersionDO.getLocalHomePath())
                .one();
        if (Objects.nonNull(existJdk)) {
            //替换成当前
            userJavaVersionDO.setId(existJdk.getId());
        }
        this.saveOrUpdate(userJavaVersionDO);
    }
}
