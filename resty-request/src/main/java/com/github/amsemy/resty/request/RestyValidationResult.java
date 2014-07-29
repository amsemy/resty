package com.github.amsemy.resty.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Результат валидации запроса. Сохраняет ошибки в формате, пригодном для
 * отправки клиенту.
 *
 *  errors: {
 *      __self__: [
 *          "errA",
 *          "errB"
 *      ],
 *      foo: {
 *          __self__: [
 *              "errC"
 *          ],
 *          bar: {
 *              __self__: [
 *                  "errD"
 *              ]
 *          }
 *      }
 *  }
 */
public class RestyValidationResult {

    private final Map<String, Object> errors;
    private boolean isValid;

    {
        errors = new HashMap<>();
        isValid = true;
    }

    /**
     * Делает результат невалидным и добавляет ошибку в список.
     *
     * @param  fieldPath
     *         Путь до поля, к которому относится сообщение.
     * @param  errorDesc
     *         Описание ошибки.
     */
    @SuppressWarnings("unchecked")
    public void addError(String[] fieldPath, String errorDesc) {
        isValid = false;
        Map<String, Object> err = errors;
        if (fieldPath != null) {
            for (String key : fieldPath) {
                if (!err.containsKey(key)) {
                    Map<String, Object> map = new HashMap<>();
                    err.put(key, map);
                    err = map;
                } else {
                    err = (Map<String, Object>) err.get(key);
                }
            }
        }
        List<String> arr;
        if (!err.containsKey("__self__")) {
            arr = new ArrayList<>();
            err.put("__self__", arr);
        } else {
            arr = (List<String>) err.get("__self__");
        }
        arr.add(errorDesc);
    }

    /**
     * Возвращает ошибки валидации.
     *
     * @return  Ошибки валидации.
     */
    public Map<String, Object> getErrors() {
        return errors;
    }

    /**
     * Является ли валидным результат.
     *
     * @return  {@code true}, если запрос валидный.
     */
    public boolean isValid() {
        return isValid;
    }

}
