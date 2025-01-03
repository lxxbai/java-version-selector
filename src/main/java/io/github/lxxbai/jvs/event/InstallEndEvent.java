package io.github.lxxbai.jvs.event;

import io.github.lxxbai.jvs.model.InstallRecordVO;
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
