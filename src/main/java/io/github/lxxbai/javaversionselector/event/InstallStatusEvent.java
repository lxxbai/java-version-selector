package io.github.lxxbai.javaversionselector.event;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lxxbai
 */
@AllArgsConstructor
@Data
public class InstallStatusEvent {

    private String version;

    private VersionActionEnum versionAction;
}
