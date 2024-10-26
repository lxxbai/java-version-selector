package io.github.lxxbai.javaversionselector.common.annotations.valid;

import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    String message() default "{javafx.validation.constraints.NotBlank.message}";

    FontAwesomeSolid icon() default FontAwesomeSolid.EXCLAMATION_TRIANGLE;

    String style() default "error";

    Class<? extends ValidatorBase> validator() default RequiredFieldValidator.class;

    Class<?>[] groups() default {};
}