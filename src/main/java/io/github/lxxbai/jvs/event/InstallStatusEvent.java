package io.github.lxxbai.jvs.event;

import io.github.lxxbai.jvs.common.enums.InstallStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lxxbai
 */
@AllArgsConstructor
@Data
public class InstallStatusEvent {

    private String ukVersion;

    private InstallStatusEnum installStatus;

    private String jdkHomePath;
}
