package io.github.lxxbai.javaversionselector.view;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.statemachine.StateMachine;
import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.event.StatusChangeEvent;
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
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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

    @Getter
    private final Map<String, Integer> userVersionMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        //初始化数据
       // addAll(javaVersionManager.queryAllUserJavaVersion(false));
    }


    private void addAll(List<UserJavaVersion> dataList) {
        userVersionList.addAll(dataList);
        for (int i = 0; i < dataList.size(); i++) {
            userVersionMap.put(dataList.get(i).getVersion(), i);
        }
    }

    private void clear() {
        userVersionMap.clear();
        userVersionList.clear();
    }

    /**
     * 刷新
     *
     * @param refreshData 是否刷新版本基础数据
     * @param clearText   是否清空过滤的值
     */
    public void refresh(boolean refreshData, boolean clearText) {
        //过滤的值设置为空
        if (clearText) {
            filterText.setValue("");
        }
        clear();
        addAll(javaVersionManager.queryAllUserJavaVersion(refreshData));
    }

    /**
     * 过滤
     */
    public void filter() {
        //过滤的值
        String value = filterText.getValue();
        List<UserJavaVersion> userVersionList = javaVersionManager.queryAllUserJavaVersion(false);
        if (StrUtil.isBlank(value)) {
            clear();
            addAll(userVersionList);
            return;
        }
        clear();
        List<UserJavaVersion> filteredVersions = new ArrayList<>();
        for (UserJavaVersion version : userVersionList) {
            if (version.getVersion().contains(value)) {
                filteredVersions.add(version);
            }
        }
        addAll(filteredVersions);
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
     * 更新表格数据
     *
     * @param userJavaVersion 版本信息
     */
    private void refreshColumn(UserJavaVersion userJavaVersion) {
        Integer index = userVersionMap.get(userJavaVersion.getVersion());
        if (Objects.isNull(index)) {
            return;
        }
        this.userVersionList.set(index, userJavaVersion);
    }

    /**
     * 异步状态改变
     *
     * @param statusChangeEvent 事件
     */
    @Async("threadPoolExecutor")
    @EventListener(StatusChangeEvent.class)
    public void doStatusChange(StatusChangeEvent statusChangeEvent) {
        VersionContext versionContext = new VersionContext();
        Integer index = userVersionMap.get(statusChangeEvent.getVersion());
        if (Objects.isNull(index)) {
            return;
        }
        versionContext.setVersion(statusChangeEvent.getVersion());
        versionContext.setIndex(index);
        versionContext.setVersionStatus(userVersionList.get(index).getStatus());
        versionContext.setUserJavaVersion(this.userVersionList.get(index));
        versionContext.setVersionAction(statusChangeEvent.getVersionAction());
        doStatusChange(versionContext);
    }

    /**
     * 状态改变
     *
     * @param versionContext 上下文
     */
    private void doStatusChange(VersionContext versionContext) {
        //执行状态机
        VersionStatusEnum versionStatusEnum = jvsStateMachine.fireEvent(versionContext.getVersionStatus(),
                versionContext.getVersionAction(), versionContext);
        UserJavaVersion userJavaVersion = versionContext.getUserJavaVersion();
        versionContext.setVersionStatus(versionStatusEnum);
        userJavaVersion.setStatus(versionStatusEnum);
        //更新数据库
        javaVersionManager.doStatusChange(userJavaVersion);
        //更新表格数据,有一种情况,应用后,之前应用的版本未刷新
        if (versionContext.getVersionAction().equals(VersionActionEnum.APPLY)) {
            refresh(false, false);
        } else {
            refreshColumn(versionContext.getIndex(), userJavaVersion);
        }
        //判断是否需要自动执行下一个状态
        VersionActionEnum nextAction = versionContext.getNextAction();
        if (Objects.nonNull(nextAction)) {
            doStatusChange(new StatusChangeEvent(versionContext.getVersion(), nextAction));
        }
    }

    public StringProperty filterTextProperty() {
        return filterText;
    }
}
