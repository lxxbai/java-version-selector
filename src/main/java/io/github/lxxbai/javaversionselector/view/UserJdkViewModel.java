
package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.common.enums.ApplyStatusEnum;
import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import io.github.lxxbai.javaversionselector.service.UserJdkVersionService;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    /**
     * 刷新用户已安装的jdk列表
     *
     * @return 用户已安装的jdk列表
     */
    public ObservableList<UserJdkVersionVO> getJdkList() {
        List<UserJdkVersionVO> myList = userJdkVersionService.queryAll();
        jdkList.clear();
        jdkList.addAll(myList);
        return jdkList;
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