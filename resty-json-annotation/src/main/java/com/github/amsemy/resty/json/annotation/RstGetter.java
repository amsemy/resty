package com.github.amsemy.resty.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Указывает что, метод класса является полем представления объекта. Применима к
 * методам POJO-классов без параметров.
 *
 * @see  RstPojo
 * @see  RstField
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RstGetter {

    /**
     * Возвращает имя поля представления объекта, которое будет содержать
     * значение, возвращемое методом объекта.
     *
     * @return  Имя поля.
     */
    String value();

}
