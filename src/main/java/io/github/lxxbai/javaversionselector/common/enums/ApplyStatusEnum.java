package io.github.lxxbai.javaversionselector.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyStatusEnum {

    CURRENT("CURRENT", "当前版本"),
    NOT_APPLY("NOT_APPLY", "未应用"),
    ;

    private final String status;
    private final String desc;
}
