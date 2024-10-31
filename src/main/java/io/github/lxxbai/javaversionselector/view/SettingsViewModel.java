package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.util.ObjectMapperUtil;
import io.github.lxxbai.javaversionselector.component.ModelProperty;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.service.SettingsService;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author lxxbai
 */
@Component
public class SettingsViewModel {

    @Resource
    private SettingsService settingsService;

    @Getter
    private final ModelProperty<DownloadConfig> modelProperty = new ModelProperty<>();

    /**
     * 加载配置
     */
    public void load() {
        DownloadConfig downloadConfig = settingsService.queryOneConfig(Constants.DOWNLOAD_CONFIG_KEY, DownloadConfig.class);
        if (Objects.isNull(downloadConfig)) {
            downloadConfig = new DownloadConfig();
            downloadConfig.setParallelDownloads(2);
        }
        modelProperty.setModel(downloadConfig);
    }

    /**
     * 保存配置
     */
    public void save() {
        DownloadConfig model = modelProperty.getModel();
        settingsService.saveConfig(Constants.DOWNLOAD_CONFIG_KEY, ObjectMapperUtil.toJsonString(model));
    }


    public boolean configured() {
        return settingsService.configured();
    }
}