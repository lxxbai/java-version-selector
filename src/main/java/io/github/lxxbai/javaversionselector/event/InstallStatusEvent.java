package io.github.lxxbai.javaversionselector.event;

import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lxxbai
 */
@AllArgsConstructor
@Data
public class InstallStatusEvent {

    private String ukVersion;

    private InstallStatusEnum currentStatus;
}
