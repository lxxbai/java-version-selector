package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.enums.VmVendorEnum;
import io.github.lxxbai.javaversionselector.common.util.StringUtil;
import io.github.lxxbai.javaversionselector.event.InstallStatusEvent;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import io.github.lxxbai.javaversionselector.service.JavaVersionService;
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

    // 创建 FilteredList
    private final FilteredList<JdkVersionVO> filteredData = new FilteredList<>(javaVersionList, p -> true);

    @Resource
    private UserJdkVersionService userJdkVersionService;


    /**
     * 刷新数据
     */
    public void refresh() {
        resetFilter();
        javaVersionList.clear();
        javaVersionList.addAll(javaVersionService.queryAll(true));
    }

    /**
     * 根据虚拟机供应商、主要版本和Java版本过滤Java版本信息
     */
    public void filter() {
        //过滤的值
        String javaVersionValue = filterJavaVersion.getValue();
        String mainVersionValue = filterMainVersion.getValue();
        String vmVendorValue = filterVmVendor.getValue();
        filteredData.setPredicate(v -> StringUtil.isBlankOrEqual(vmVendorValue, v.getVmVendor())
                && StringUtil.isBlankOrEqual(mainVersionValue, v.getMainVersion())
                && StringUtil.isBlankOrLike(javaVersionValue, v.getJavaVersion()));
    }

    /**
     * 获取所有版本信息
     *
     * @return 所有版本信息
     */
    public ObservableList<JdkVersionVO> getJavaVersionList() {
        javaVersionList.addAll(javaVersionService.queryAll(false));
        return filteredData;
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

    /**
     * 更新表格数据
     *
     * @param index        下标
     * @param jdkVersionVO 版本信息
     */
    private void refreshColumn(int index, JdkVersionVO jdkVersionVO) {
        this.javaVersionList.set(index, jdkVersionVO);
    }


    /**
     * 重置过滤
     */
    public void resetFilter() {
        //过滤的值
        filterJavaVersion.set(null);
        filterMainVersion.set(null);
        filterVmVendor.setValue(null);
    }


    /**
     * 更新状态
     *
     * @param index      下标
     * @param statusEnum 状态
     */
    public void changeStatus(int index, InstallStatusEnum statusEnum) {
        JdkVersionVO vo = javaVersionList.get(index);
        vo.setInstallStatus(statusEnum);
        refreshColumn(index, vo);
    }


    @EventListener(value = InstallStatusEvent.class)
    public void changeStatus(InstallStatusEvent installStatusEvent) {
        String ukVersion = installStatusEvent.getUkVersion();
        for (int i = 0; i < javaVersionList.size(); i++) {
            if (javaVersionList.get(i).getUkVersion().equals(ukVersion)) {
                changeStatus(i, installStatusEvent.getInstallStatus());
            }
        }
    }

    /**
     * 判断版本是否存在
     *
     * @param ukVersion 版本号
     * @return 是否存在
     */
    public boolean versionExists(String ukVersion) {
        return userJdkVersionService.versionExists(ukVersion);
    }
}
