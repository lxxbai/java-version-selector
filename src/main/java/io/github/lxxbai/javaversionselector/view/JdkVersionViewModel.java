package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.common.enums.VmVendorEnum;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import io.github.lxxbai.javaversionselector.service.JavaVersionService;
import jakarta.annotation.Resource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * java版本列表
 *
 * @author lxxbai
 */
@Component
public class JdkVersionViewModel {

    @Resource
    private JavaVersionService javaVersionService;

    @Getter
    private final StringProperty filterJavaVersion = new SimpleStringProperty();
    @Getter
    private final StringProperty filterMainVersion = new SimpleStringProperty();
    @Getter
    private final StringProperty filterVmVendor = new SimpleStringProperty();


    private final ObservableList<JdkVersionVO> javaVersionList = FXCollections.observableArrayList();

    private final ObservableList<String> vmVendorList = FXCollections.observableArrayList();

    private final ObservableList<String> mainVersionList = FXCollections.observableArrayList();


    /**
     * 刷新数据
     */
    public void refresh() {
        resetFilter();
        javaVersionList.clear();
        javaVersionList.addAll(javaVersionService.queryAll(true));
    }

    /**
     * 过滤
     */
    public void filter() {
        //过滤的值
        String javaVersionValue = filterJavaVersion.getValue();
        String mainVersionValue = filterMainVersion.getValue();
        String vmVendorValue = filterVmVendor.getValue();
        List<JdkVersionVO> filterVersions = javaVersionService.filter(vmVendorValue, mainVersionValue, javaVersionValue);
        javaVersionList.clear();
        javaVersionList.addAll(filterVersions);
    }

    /**
     * 获取所有版本信息
     *
     * @return 所有版本信息
     */
    public ObservableList<JdkVersionVO> getJavaVersionList() {
        javaVersionList.addAll(javaVersionService.queryAll(false));
        return javaVersionList;
    }

    /**
     * 获取所有主版本
     *
     * @return 所有主版本
     */
    public ObservableList<String> getMainVersionList() {
        mainVersionList.clear();
        List<String> list = javaVersionList.stream().map(JdkVersionVO::getMainVersion)
                .distinct().sorted(Comparator.comparing((String x) ->
                        Integer.parseInt(x.replace("JDK", ""))).reversed())
                .toList();
        mainVersionList.addAll(list);
        return mainVersionList;
    }

    /**
     * 获取所有供应商
     *
     * @return 所有供应商
     */
    public ObservableList<String> getVmVendorList() {
        vmVendorList.clear();
        vmVendorList.addAll(Arrays.stream(VmVendorEnum.values()).map(VmVendorEnum::getCode).toList());
        return vmVendorList;
    }

    public void resetFilter() {
        //过滤的值
        filterJavaVersion.set(null);
        filterMainVersion.set(null);
        filterVmVendor.setValue(null);
    }
}
