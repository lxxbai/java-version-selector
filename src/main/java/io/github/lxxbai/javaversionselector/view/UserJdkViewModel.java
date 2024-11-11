
package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import io.github.lxxbai.javaversionselector.service.UserJdkVersionService;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
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

    public void refresh() {
        jdkList.clear();
        jdkList.addAll(userJdkVersionService.queryAll());
    }
}