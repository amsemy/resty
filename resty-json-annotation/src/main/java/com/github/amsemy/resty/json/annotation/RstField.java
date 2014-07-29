package com.github.amsemy.resty.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Указывает что, поле класса является полем представления объекта. Применима к
 * полям POJO-классов.
 *
 * @see  RstPojo
 * @see  RstGetter
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RstField {

    /**
     * (Необязательно) Возвращает имя поля представления объекта, которое будет
     * содержать значение, возвращемое полем объекта.
     *
     * @return  Имя поля или {@code ""}.
     */
    String value() default "";

}
