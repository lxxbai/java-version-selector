package io.github.lxxbai.jvs.view.model;

import io.github.lxxbai.jvs.common.model.JdkInfo;
import io.github.lxxbai.jvs.model.JdkInfoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lxxbai
 */
@Getter
@Component
public class JdkScannerViewModel {

    private final ObservableList<JdkInfoVO> javaVersionList = FXCollections.observableArrayList();

    /**
     * 刷新数据
     *
     * @param jdkInfoList jdkInfoList
     */
    public void refresh(List<JdkInfo> jdkInfoList) {
        javaVersionList.clear();
        for (JdkInfo jdkInfo : jdkInfoList) {
            javaVersionList.add(new JdkInfoVO(jdkInfo));
        }
    }

    /**
     * 获取选中的列表
     */
    public List<JdkInfoVO> getSelectedList() {
        return javaVersionList.stream().filter(JdkInfoVO::isSelectedFlag)
                .toList();
    }
}
