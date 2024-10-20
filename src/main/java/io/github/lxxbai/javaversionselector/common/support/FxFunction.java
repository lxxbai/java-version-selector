package io.github.lxxbai.javaversionselector.common.support;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface FxFunction<T, R> extends Function<T, R>, Serializable {
}