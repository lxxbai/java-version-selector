package io.github.lxxbai.jvs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lxxbai
 */
@Getter
@AllArgsConstructor
public enum OsEnum {

    WINDOWS("windows"), MAC("mac"), LINUX("linux");

    private  final String code;
}
