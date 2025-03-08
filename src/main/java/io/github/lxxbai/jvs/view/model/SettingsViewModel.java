package io.github.lxxbai.jvs.view.model;

import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.jvs.common.Constants;
import io.github.lxxbai.jvs.common.util.FileUtil;
import io.github.lxxbai.jvs.common.util.ObjectMapperUtil;
import io.github.lxxbai.jvs.component.ModelProperty;
import io.github.lxxbai.jvs.model.DownloadConfig;
import io.github.lxxbai.jvs.service.SettingsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        DownloadConfig downloadConfig = settingsService.queryOneConfig(Constants.DOWNLOAD_CONFIG_KEY, DownloadConfig.class);
        if (Objects.isNull(downloadConfig)) {
            downloadConfig = new DownloadConfig();
            downloadConfig.setParallelDownloads(2);
            downloadConfig.setDefaultConfigured(false);
            downloadConfig.setJdkSavePath(FileUtil.getDefaultPath().resolve("Downloads").toString());
            downloadConfig.setJdkInstallPath(FileUtil.getDefaultPath().resolve("Jdks").toString());
        }
        modelProperty.setModel(downloadConfig);
    }

    public ModelProperty<DownloadConfig> getModelProperty() {
        if (Objects.isNull(modelProperty.getModel())) {
            load();
        }
        return modelProperty;
    }

    public void saveTheme(String theme) {
        if (StrUtil.isBlank(theme)) {
            return;
        }
        settingsService.saveConfig("THEME", theme);
    }

    public String getTheme() {
        String theme = settingsService.queryStrConfig("THEME");
        return StrUtil.isBlank(theme) ? "blue" : theme;
    }

    /**
     * 保存配置
     */
    public void save() {
        DownloadConfig model = modelProperty.getModel();
        settingsService.saveConfig(Constants.DOWNLOAD_CONFIG_KEY, ObjectMapperUtil.toJsonString(model));
    }
}