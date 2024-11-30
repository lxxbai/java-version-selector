
package io.github.lxxbai.javaversionselector.view;

import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.javaversionselector.common.enums.ApplyStatusEnum;
import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import io.github.lxxbai.javaversionselector.service.UserJdkVersionService;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author lxxbai
 */
@Component
public class UserJdkViewModel {

    @Resource
    private UserJdkVersionService userJdkVersionService;

    private final ObservableList<UserJdkVersionVO> jdkList = FXCollections.observableArrayList();

    private UserJdkVersionVO currentJdk;


    /**
     * 刷新用户已安装的jdk列表
     *
     * @return 用户已安装的jdk列表
     */
    public ObservableList<UserJdkVersionVO> getJdkList() {
        jdkList.addListener((ListChangeListener<UserJdkVersionVO>) change -> {
            while (change.next()) {
                if (change.wasUpdated()) {
                    List<? extends UserJdkVersionVO> addedSubList = change.getAddedSubList();
                    System.out.println(addedSubList);
                }
            }
        });


        List<UserJdkVersionVO> myList = userJdkVersionService.queryAll();
        jdkList.clear();
        jdkList.addAll(myList);
        return jdkList;
    }

    public void refresh() {
        jdkList.clear();
        jdkList.addAll(userJdkVersionService.queryAll());
    }

    public void applyJdk(UserJdkVersionVO userJdk) {
        for (int i = 0; i < jdkList.size(); i++) {
            UserJdkVersionVO userJdkVersion = jdkList.get(i);
            if (StrUtil.equals(userJdk.getUkCode(), userJdkVersion.getUkCode())) {
                userJdkVersion.setStatus(ApplyStatusEnum.CURRENT);
                continue;
            }
            if (ApplyStatusEnum.CURRENT.equals(userJdkVersion.getStatus())) {
                userJdkVersion.setStatus(ApplyStatusEnum.NOT_APPLY);
                continue;
            }
        }
    }
}