package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.component.ModelProperty;
import io.github.lxxbai.javaversionselector.datasource.entity.UserConfigInfoDO;
import io.github.lxxbai.javaversionselector.service.SettingsService;
import jakarta.annotation.Resource;
import javafx.beans.property.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SettingsViewModel {

    @Resource
    private SettingsService settingsService;

    private final IntegerProperty parallelDownloadsProperty = new SimpleIntegerProperty();
    private final StringProperty downloadPathProperty = new SimpleStringProperty();
    private final StringProperty jdkPathProperty = new SimpleStringProperty();

    private final ModelProperty<UserConfigInfoDO> modelProperty = new ModelProperty(UserConfigInfoDO.class);

    private SimpleObjectProperty<UserConfigInfoDO> personProperty = new SimpleObjectProperty<>();

    public void save() {
        List<UserConfigInfoDO> configList = new ArrayList<>();
        UserConfigInfoDO userConfigInfoDO = new UserConfigInfoDO();
        configList.add(userConfigInfoDO);
        settingsService.saveConfigList(configList);
    }


    public IntegerProperty parallelDownloadsProperty() {
        return parallelDownloadsProperty;
    }

    public StringProperty downloadPathProperty() {
        return downloadPathProperty;
    }

    public StringProperty jdkPathProperty() {
        return jdkPathProperty;
    }

}