package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.component.ModelProperty;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.service.SettingsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author lxxbai
 */
@Component
public class SettingsViewModel {

    @Resource
    private SettingsService settingsService;

    private final ModelProperty<DownloadConfig> modelProperty = new ModelProperty<>();

    /**
     * 加载配置
     */
    public void load() {
        DownloadConfig downloadConfig = settingsService.queryOneConfig("downloadConfig", DownloadConfig.class);
        modelProperty.setModel(downloadConfig);
    }

    public void save() {
        DownloadConfig model = modelProperty.getModel();


    }
}