package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.model.JavaVersionVO;
import io.github.lxxbai.javaversionselector.service.JavaVersionService;
import jakarta.annotation.Resource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

/**
 * @author lxxbai
 */
@Component
public class NewJavaVersionViewModel {

    @Resource
    private JavaVersionService javaVersionService;

    private final StringProperty filterText = new SimpleStringProperty();

    private final ObservableList<JavaVersionVO> javaVersionList = FXCollections.observableArrayList();

    public void refresh() {
        javaVersionList.clear();
        javaVersionList.addAll(javaVersionService.queryAll(true));
    }

    public ObservableList<JavaVersionVO> getJavaVersionList() {
        javaVersionList.addAll(javaVersionService.queryAll(false));
        return javaVersionList;
    }

    public StringProperty filterTextProperty() {
        return filterText;
    }
}
