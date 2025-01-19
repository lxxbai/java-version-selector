package io.github.lxxbai.jvs.model;

import io.github.lxxbai.jvs.common.model.JdkInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lxxbai
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JdkInfoVO extends JdkInfo {

    private boolean selectedFlag;

    public JdkInfoVO(JdkInfo jdkInfo) {
        this.setSelectedFlag(true);
        this.setJavaVersion(jdkInfo.getJavaVersion());
        this.setMainVersion(jdkInfo.getMainVersion());
        this.setVmVendor(jdkInfo.getVmVendor());
        this.setLocalHomePath(jdkInfo.getLocalHomePath());
    }
}
