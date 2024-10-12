package io.github.lxxbai.javaversionselector.manager;

import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
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
            userJavaVersion.setStatus(VersionStatusEnum.NOT_INSTALLED);
            userJavaVersion.setCurrent(false);
            userJavaVersion.setReleaseDate(one.getReleaseDate());
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
     * 状态修改
     *
     * @param userJavaVersion 用户版本
     */
    public void doStatusChange(UserJavaVersion userJavaVersion) {
        UserJavaVersionDO userJavaVersionDO = userJavaVersionRepository.findByVersion(userJavaVersion.getVersion());
        if (Objects.isNull(userJavaVersionDO)) {
            return;
        }
        userJavaVersionDO.setLocalPath(userJavaVersion.getLocalPath());
        userJavaVersionDO.setStatus(userJavaVersion.getStatus());
        userJavaVersionDO.setCurrent(userJavaVersion.getCurrent());
        //应用
        if (userJavaVersion.getCurrent() && !userJavaVersionDO.getCurrent()) {
            userJavaVersionRepository.cancelCurrentVersion();
        }
    }
}
