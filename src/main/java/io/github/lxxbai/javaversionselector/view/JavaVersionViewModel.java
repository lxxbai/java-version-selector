package io.github.lxxbai.javaversionselector.view;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.cola.statemachine.StateMachine;
import io.github.lxxbai.javaversionselector.common.enums.StatusEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.manager.JavaVersionManager;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxxbai
 */
@Component
public class JavaVersionViewModel {

    @Resource
    private JavaVersionManager javaVersionManager;

    @Resource
    private StateMachine<VersionStatusEnum, VersionActionEnum, VersionContext> jvsStateMachine;

    private final StringProperty filterText = new SimpleStringProperty();

    @Getter
    private final ObservableList<UserJavaVersion> userVersionList = FXCollections.observableArrayList();

    @PostConstruct
    public void init() {
        //初始化数据
        this.userVersionList.addAll(javaVersionManager.queryAllUserJavaVersion(false));
    }

    /**
     * 刷新
     */
    public void refresh() {
        //过滤的值设置为空
        filterText.setValue("");
        userVersionList.clear();
        userVersionList.addAll(javaVersionManager.queryAllUserJavaVersion(true));
    }

    /**
     * 过滤
     */
    public void filter() {
        //过滤的值
        String value = filterText.getValue();
        List<UserJavaVersion> userVersionList = javaVersionManager.queryAllUserJavaVersion(false);
        if (StrUtil.isBlank(value)) {
            this.userVersionList.clear();
            this.userVersionList.addAll(userVersionList);
            return;
        }
        this.userVersionList.clear();
        List<UserJavaVersion> filteredVersions = new ArrayList<>();
        for (UserJavaVersion version : userVersionList) {
            if (version.getVersion().contains(value)) {
                filteredVersions.add(version);
            }
        }
        this.userVersionList.addAll(filteredVersions);
    }

    /**
     * 更新表格数据
     *
     * @param index           下标
     * @param userJavaVersion 版本信息
     */
    private void refreshColumn(int index, UserJavaVersion userJavaVersion) {
        this.userVersionList.set(index, userJavaVersion);
    }


    /**
     * 状态改变
     * @param versionContext 上下文
     */
    public void doStatusChange(VersionContext versionContext) {
        VersionStatusEnum versionStatusEnum = jvsStateMachine.fireEvent(versionContext.getVersionStatus(),
                versionContext.getVersionAction(), versionContext);
        UserJavaVersion userJavaVersion = versionContext.getUserJavaVersion();
        versionContext.setVersionStatus(versionStatusEnum);
        refreshColumn(versionContext.getIndex(), userJavaVersion);
    }


    /**
     * 下载完成
     */
    public void downloadStatusChange(int index, StatusEnum statusEnum) {
        UserJavaVersion userJavaVersion = userVersionList.get(index);
        userJavaVersion.setStatus(statusEnum);
        javaVersionManager.downloadStatusChange(userJavaVersion);
        refreshColumn(index, userJavaVersion);
        //下载成功后，解压文件，配置对应的环境变量
        if (statusEnum == StatusEnum.INSTALLED) {
            ZipUtil.unzip(userJavaVersion.getLocalPath(), "D:\\Java");
            javaVersionManager.apply(userJavaVersion);
        }
    }

    /**
     * 应用
     *
     * @param index 下标
     */
    public void apply(int index) {
        UserJavaVersion userJavaVersion = userVersionList.get(index);
        userJavaVersion.setStatus(StatusEnum.CURRENT);
        userJavaVersion.setCurrent(true);
        //调用应用
        javaVersionManager.apply(userJavaVersion);
        refreshColumn(index, userJavaVersion);
    }

    /**
     * 卸载
     *
     * @param index 下标
     */
    public void unInstall(int index) {
        UserJavaVersion userJavaVersion = userVersionList.get(index);
        userJavaVersion.setStatus(StatusEnum.NOT_INSTALLED);
        userJavaVersion.setCurrent(false);
        //调用应用
        javaVersionManager.apply(userJavaVersion);
        refreshColumn(index, userJavaVersion);
    }


    public StringProperty filterTextProperty() {
        return filterText;
    }
}
