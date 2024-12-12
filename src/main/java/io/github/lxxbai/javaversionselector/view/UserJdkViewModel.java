
package io.github.lxxbai.javaversionselector.view;

import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.javaversionselector.common.enums.ApplyStatusEnum;
import io.github.lxxbai.javaversionselector.event.InstallStatusEvent;
import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import io.github.lxxbai.javaversionselector.service.UserJdkVersionService;
import jakarta.annotation.Resource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


/**
 * @author lxxbai
 */
@Component
public class UserJdkViewModel {

    @Resource
    private UserJdkVersionService userJdkVersionService;

    private final ObservableList<UserJdkVersionVO> jdkList = FXCollections.observableArrayList();

    // 创建 FilteredList
    private final FilteredList<UserJdkVersionVO> filteredData = new FilteredList<>(jdkList, p -> true);

    @Getter
    private final StringProperty filterJavaVersion = new SimpleStringProperty();


    /**
     * 刷新用户已安装的jdk列表
     *
     * @return 用户已安装的jdk列表
     */
    public ObservableList<UserJdkVersionVO> getJdkList() {
        List<UserJdkVersionVO> myList = userJdkVersionService.queryAll();
        jdkList.clear();
        jdkList.addAll(myList);
        return filteredData;
    }

    /**
     * 过滤
     */
    public void filter() {
        //过滤的值
        String javaVersionValue = filterJavaVersion.getValue();
        if (StrUtil.isBlank(javaVersionValue)) {
            filteredData.setPredicate(record -> true);
            return;
        }
        filteredData.setPredicate(record -> StrUtil.containsIgnoreCase(record.getJavaVersion(), javaVersionValue));
    }

    /**
     * 刷新
     */
    public void refresh() {
        jdkList.clear();
        jdkList.addAll(userJdkVersionService.queryAll());
    }

    /**
     * 更新表格数据
     *
     * @param index           下标
     * @param installRecordVO 版本信息
     */
    private void refreshColumn(int index, UserJdkVersionVO installRecordVO) {
        this.jdkList.set(index, installRecordVO);
    }

    @EventListener(value = InstallStatusEvent.class)
    public void changeStatus(InstallStatusEvent installStatusEvent) {
        String jdkHomePath = installStatusEvent.getJdkHomePath();
        userJdkVersionService.saveUserJdk(jdkHomePath);
        // 刷新
        refresh();
    }


    /**
     * 应用
     *
     * @param userJdk jdk
     */
    public void applyJdk(UserJdkVersionVO userJdk) {
        Integer currentIndex = null;
        Integer newIndex = null;
        for (int i = 0; i < jdkList.size(); i++) {
            UserJdkVersionVO jdk = jdkList.get(i);
            if (ApplyStatusEnum.CURRENT.equals(jdk.getStatus())) {
                currentIndex = i;
            }
            if (Objects.equals(userJdk.getId(), jdk.getId())) {
                newIndex = i;
            }
            if (Objects.nonNull(currentIndex) && Objects.nonNull(newIndex)) {
                break;
            }
        }
        if (Objects.equals(currentIndex, newIndex)) {
            return;
        }
        if (Objects.nonNull(currentIndex)) {
            UserJdkVersionVO userJdkVersionVO = jdkList.get(currentIndex);
            userJdkVersionVO.setStatus(ApplyStatusEnum.NOT_APPLY);
            userJdkVersionService.updateStatus(userJdkVersionVO);
            refreshColumn(currentIndex, userJdkVersionVO);
        }
        if (Objects.nonNull(newIndex)) {
            UserJdkVersionVO userJdkVersionVO = jdkList.get(newIndex);
            userJdkVersionVO.setStatus(ApplyStatusEnum.CURRENT);
            userJdkVersionService.updateStatus(userJdkVersionVO);
            refreshColumn(newIndex, userJdkVersionVO);
        }
    }
}