package io.github.lxxbai.javaversionselector.manager;

import cn.hutool.core.io.FileUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.StatusEnum;
import io.github.lxxbai.javaversionselector.common.util.UserEnvUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.JavaVersionDO;
import io.github.lxxbai.javaversionselector.datasource.entity.UserJavaVersionDO;
import io.github.lxxbai.javaversionselector.datasource.repository.JavaVersionRepository;
import io.github.lxxbai.javaversionselector.datasource.repository.UserJavaVersionRepository;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 版本管理器处理
 *
 * @author lxxbai
 */
@Component
public class JavaVersionManager {

    @Resource
    private JavaVersionRepository javaVersionRepository;

    @Resource
    private UserJavaVersionRepository userJavaVersionRepository;


    /**
     * 查询所有用户版本
     *
     * @return 用户版本列表
     */
    public List<UserJavaVersion> queryAllUserJavaVersion(boolean refresh) {
        List<JavaVersionDO> javaVersions = javaVersionRepository.findAll();
        //需要刷新
        if (refresh) {
            //todo 获取用户的配置
            javaVersions = javaVersionRepository.refresh();
        }
        //获取用户已经有的版本
        List<UserJavaVersionDO> userJavaVersionList = userJavaVersionRepository.findAll();
        //数据转成Map
        Map<String, UserJavaVersionDO> dataMap = userJavaVersionList.stream().collect(Collectors.toMap(UserJavaVersionDO::getVersion,
                Function.identity(), (old, n) -> n));
        return javaVersions.stream().map(one -> {
            UserJavaVersion userJavaVersion = new UserJavaVersion();
            userJavaVersion.setVersion(one.getVersion());
            userJavaVersion.setStatus(StatusEnum.NOT_INSTALLED);
            userJavaVersion.setCurrent(false);
            userJavaVersion.setReleaseDate(one.getReleaseDate());
            userJavaVersion.setX64WindowsDownloadUrl(one.getX64WindowsDownloadUrl());
            userJavaVersion.setX32WindowsDownloadUrl(one.getX32WindowsDownloadUrl());
            userJavaVersion.setMacDownloadUrl(one.getMacDownloadUrl());
            if (dataMap.containsKey(one.getVersion())) {
                UserJavaVersionDO oneUserVersion = dataMap.get(one.getVersion());
                userJavaVersion.setLocalPath(oneUserVersion.getLocalPath());
                userJavaVersion.setCurrent(oneUserVersion.getCurrent());
                userJavaVersion.setStatus(oneUserVersion.getStatus());
            }
            return userJavaVersion;
        }).toList();
    }


    /**
     * 下载状态修改
     *
     * @param userJavaVersion 用户版本
     */
    public void downloadStatusChange(UserJavaVersion userJavaVersion) {
        UserJavaVersionDO downloadVersion = userJavaVersionRepository.findByVersion(userJavaVersion.getVersion());
        if (Objects.nonNull(downloadVersion)) {
            userJavaVersionRepository.delete(UserJavaVersionDO::getVersion, userJavaVersion.getVersion());
        }
        UserJavaVersionDO newDownloadVersion = new UserJavaVersionDO();
        newDownloadVersion.setVersion(userJavaVersion.getVersion());
        newDownloadVersion.setLocalPath(userJavaVersion.getLocalPath());
        newDownloadVersion.setStatus(userJavaVersion.getStatus());
        newDownloadVersion.setCurrent(false);
        userJavaVersionRepository.add(newDownloadVersion);
    }


    /**
     * 应用
     *
     * @param userJavaVersion 用户版本
     */
    public void apply(UserJavaVersion userJavaVersion) {
        //取消当前版本
        userJavaVersionRepository.cancelCurrentVersion();
        //应用当前版本
        userJavaVersionRepository.applyVersion(userJavaVersion.getVersion());
        //更新地址
        UserEnvUtil.updateUserEnv(Constants.STR_JAVA_HOME, userJavaVersion.getLocalPath());
    }

    /**
     * 卸载
     *
     * @param userJavaVersion 用户版本
     */
    public void unInstall(UserJavaVersion userJavaVersion) {
        //取消当前版本
        userJavaVersionRepository.cancelCurrentVersion();
        //环境变量删除
        UserEnvUtil.deleteUserEnv(Constants.STR_JAVA_HOME + userJavaVersion.getVersion());
        //文件删除
        FileUtil.del(userJavaVersion.getLocalPath());
        //删除java版本
        Boolean current = userJavaVersionRepository.current(userJavaVersion.getVersion());
        if (current) {
            UserEnvUtil.deleteUserEnv(Constants.STR_JAVA_HOME);
        }
    }
}
