package io.github.lxxbai.javaversionselector.event;

import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lxxbai
 */
@AllArgsConstructor
@Data
public class InstallEndEvent {

    private String jdkHomePath;

    private InstallRecordVO installRecordVO;
}
