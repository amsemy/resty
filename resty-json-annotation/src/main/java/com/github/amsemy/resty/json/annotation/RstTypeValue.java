package com.github.amsemy.resty.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Указывает, что данное поле/метод содержат значение пользовательского типа
 * данных, которое будет использоваться при преобразовании. Аннотация применима
 * только к какому-то одному полю, либо методу.
 *
 * @see  RstPojo
 * @see  RstType
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RstTypeValue {
}
