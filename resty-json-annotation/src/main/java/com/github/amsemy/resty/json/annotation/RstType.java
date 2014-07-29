package com.github.amsemy.resty.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Указывает, что данный класс является пользовательским типом данных. При
 * преобразовании объектов данного класса будет использовано значение, которое
 * будет получено из поля/метода, отмеченного аннотацией {@link RstTypeValue}.
 * Несовместима с аннотацией {@link RstPojo}.
 *
 * @see  RstPojo
 * @see  RstTypeValue
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RstType {
}
