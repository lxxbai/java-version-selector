package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.service.SettingsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class SettingsViewModel {

    @Resource
    private SettingsService settingsService;

    public void save() {
        //settingsService.save();
    }

    public boolean configured() {
        return settingsService.configured();
    }
}