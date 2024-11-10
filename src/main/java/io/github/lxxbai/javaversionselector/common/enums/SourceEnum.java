package io.github.lxxbai.javaversionselector.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum SourceEnum {

    //local or platform

    LOCAL("LOCAL", "本地"),
    PLATFORM("PLATFORM", "当前平台"),
    ;

    private final String code;

    private final String desc;

}
