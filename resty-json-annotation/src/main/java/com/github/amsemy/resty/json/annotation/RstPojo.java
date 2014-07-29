package com.github.amsemy.resty.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Указывает, что данный класс является преобразуемым в представление, которое
 * будет отправлено в ответе на запрос. В представление попадают только те
 * поля и методы класса, которые отмечены аннотациями {@link RstField} и
 * {@link RstGetter}. Унаследованые поля попадают в представление, если предок
 * класса отмечен этой аннотацией. Аннотация применима к POJO-классам.
 * Несовместима с аннотацией {@link RstType}.
 *
 * @see  RstField
 * @see  RstGetter
 * @see  RstType
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RstPojo {
}
