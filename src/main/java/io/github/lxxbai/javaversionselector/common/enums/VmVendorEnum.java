package io.github.lxxbai.javaversionselector.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum VmVendorEnum {

    ORACLE("Oracle jdk"),
    OPEN_JDK("Open jdk"),
    OTHER_JDK("Other jdk");

    private final String code;

    /**
     * 根据虚拟机供应商名称获取对应的枚举值
     * 此方法旨在通过供应商名称，如"Oracle"或"Adoptium"，来标准化识别虚拟机供应商
     * 如果供应商名称为空或未识别，则默认返回"OTHER_JDK"
     *
     * @param vmVendor 虚拟机供应商名称
     * @return 对应的VmVendorEnum枚举值
     */
    public static VmVendorEnum getByVmVendor(String vmVendor) {
        if (StrUtil.isBlank(vmVendor)) {
            return OTHER_JDK;
        }
        if (StrUtil.containsIgnoreCase(vmVendor, "Oracle")) {
            return ORACLE;
        }
        if (StrUtil.containsIgnoreCase(vmVendor, "Adoptium")) {
            return OPEN_JDK;
        }
        return OTHER_JDK;
    }
}
