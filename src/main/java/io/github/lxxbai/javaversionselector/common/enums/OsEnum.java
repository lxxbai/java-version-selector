package io.github.lxxbai.javaversionselector.common.enums;

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
