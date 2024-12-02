package io.github.lxxbai.javaversionselector.common.annotations.base;

import java.lang.annotation.*;

/**
 *
 * @author lxxbai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface FXView {

    String url();

    String title() default "";

    double preWidth() default 0.0;

    double preHeight() default 0.0;
}
