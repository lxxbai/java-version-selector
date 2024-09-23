package io.github.lxxbai.javaversionselector.datasource.repository;


import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.StatusEnum;
import io.github.lxxbai.javaversionselector.datasource.entity.UserJavaVersionDO;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.*;


/**
 * 获取用户已经有的版列表
 *
 * @author lxxbai
 */
@Getter
@Repository
public class UserJavaVersionRepository extends InMemoryRepository<UserJavaVersionDO> {

    /**
     * 获取用户的java版本列表
     * <p>
     * JAVA_HOME8 =>D:\Java\jdk-17.0.11
     *
     * @return java版本列表
     */
    @Override
    List<UserJavaVersionDO> init() {
        List<UserJavaVersionDO> installedVersions = new ArrayList<>();
        //获取所有的环境变量，用于获取已经安装的版本
        Map<String, String> envMap = System.getenv();
        //获取当前的java版本
        String currentVersionHome = envMap.get(Constants.STR_JAVA_HOME);
        Map<String, String> pathVersionMap = new HashMap<>();
        envMap.forEach((k, v) -> {
            if (StrUtil.startWith(k, Constants.STR_JAVA_HOME)) {
                String version = k.replace(Constants.STR_JAVA_HOME, "");
                if (StrUtil.isNotBlank(version)) {
                    UserJavaVersionDO userJavaVersionDO = new UserJavaVersionDO();
                    userJavaVersionDO.setStatus(StatusEnum.INSTALLED);
                    userJavaVersionDO.setLocalPath(v);
                    userJavaVersionDO.setVersion(version);
                    pathVersionMap.put(v, k.replace(Constants.STR_JAVA_HOME, ""));
                    installedVersions.add(userJavaVersionDO);
                }
            }
        });
        //获取当前的java版本
        String currentVersion = pathVersionMap.get(currentVersionHome);
        //设置当前的java版本
        installedVersions.forEach(x -> {
            if (StrUtil.equalsIgnoreCase(currentVersion, x.getVersion())) {
                x.setCurrent(true);
                x.setVersion(currentVersion);
                x.setStatus(StatusEnum.CURRENT);
            } else {
                x.setCurrent(false);
            }
        });
        return installedVersions;
    }


    /**
     * 取消当前版本
     */
    public void cancelCurrentVersion() {
        UserJavaVersionDO first = currentVersion();
        if (Objects.nonNull(first)) {
            first.setCurrent(false);
            first.setStatus(StatusEnum.INSTALLED);
        }
    }

    /**
     * 应用版本
     */
    public void applyVersion(String version) {
        UserJavaVersionDO newVersion = findByVersion(version);
        newVersion.setCurrent(true);
        newVersion.setStatus(StatusEnum.CURRENT);
    }

    /**
     * 当前版本
     *
     * @return 当前版本
     */
    public UserJavaVersionDO currentVersion() {
        return findFirst(UserJavaVersionDO::getCurrent, true);
    }

    /**
     * 当前版本
     *
     * @return 当前版本
     */
    public Boolean current(String version) {
        UserJavaVersionDO userJavaVersionDO = currentVersion();
        if (Objects.nonNull(userJavaVersionDO)) {
            return StrUtil.equals(userJavaVersionDO.getVersion(), version);
        }
        return false;
    }


    /**
     * 根据版本获取数据
     *
     * @return 版本数据
     */
    public UserJavaVersionDO findByVersion(String version) {
        return findFirst(UserJavaVersionDO::getVersion, version);
    }
}
