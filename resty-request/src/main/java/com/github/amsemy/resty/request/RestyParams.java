package com.github.amsemy.resty.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * Параметры запроса к ресурсу. Поддерживает вложенные друг в друга структуры,
 * значения полей которых будут хранится с именами параметров, соответствующими
 * полному пути до поля вида {@code foo[bar][baz]}.
 */
public class RestyParams extends HashMap<String, List<String>> {

    private RestyParams(int initialCapacity) {
        super(initialCapacity);
    }

    private RestyParams(Map<String, List<String>> map) {
        super(map);
    }

    /**
     * Добавляет дополнительную пару параметр/значение в список параметров.
     *
     * @param  key
     *         Имя параметра.
     * @param  value
     *         Значение параметра
     * @return  {@code true} (как указано в {@link java.util.Collection#add}).
     */
    public boolean add(String key, String value) {
        if (containsKey(key)) {
            List<String> list = get(key);
            if (list == null) {
                list = new ArrayList<>(2);
                list.add(null);
                list.add(value);
                put(key, list);
            } else {
                try {
                    list.add(value);
                } catch (UnsupportedOperationException ex) {
                    list = new ArrayList<>(list);
                    list.add(value);
                    put(key, list);
                }
            }
        } else {
            List<String> list = new ArrayList<>(1);
            list.add(value);
            put(key, list);
        }
        return true;
    }

    /**
     * Создаёт пустые параметры запроса.
     *
     * @return  Параметры запроса к ресурсу.
     */
    public static RestyParams buildEmptyParams() {
        return new RestyParams(0);
    }

    /**
     * Создаёт параметры запроса на основе JSON-структуры.
     *
     * @param  json
     *         Параметры запроса в виде JSON-структуры.
     * @return  Параметры запроса к ресурсу.
     */
    public static RestyParams buildJsonParams(JsonValue json) {
        RestyParams result = new RestyParams(0);
        switch (json.getValueType()) {
            case OBJECT:
                result.setJsonValue(json, null);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return result;
    }

    /**
     * Создаёт параметры запроса на основе параметров запроса JAX-RS сервиса.
     *
     * @param  params
     *         Параметры запроса к сервису.
     * @return  Параметры запроса к ресурсу.
     */
    public static RestyParams buildServiceParams(
            Map<String, List<String>> params) {
        return new RestyParams(params);
    }

    /**
     * Создаёт параметры запроса на основе параметров запроса сервлета.
     *
     * @param  params
     *         Параметры запроса к сервлету.
     * @return  Параметры запроса к ресурсу.
     */
    public static RestyParams buildServletParams(
            Map<String, String[]> params) {
        RestyParams result = new RestyParams(params.size());
        for (String key : params.keySet()) {
            String[] arr = params.get(key);
            result.put(key, (arr == null ? null : Arrays.asList(arr)));
        }
        return result;
    }

    private void setJsonValue(JsonValue json, String paramPath) {
        switch (json.getValueType()) {
            case ARRAY:
                final JsonArray arr = (JsonArray) json;
                for (JsonValue val : arr) {
                    setJsonValue(val, paramPath);
                }
                break;
            case OBJECT:
                final JsonObject obj = (JsonObject) json;
                for (String key : obj.keySet()) {
                    JsonValue val = obj.get(key);
                    setJsonValue(val, (paramPath == null
                            ? key
                            : paramPath + "[" + key + "]"));
                }
                break;
            case STRING:
                final JsonString str = (JsonString) json;
                add(paramPath, str.getString());
                break;
            case NUMBER:
                final JsonNumber num = (JsonNumber) json;
                add(paramPath, num.toString());
                break;
            case TRUE:
                add(paramPath, "true");
                break;
            case FALSE:
                add(paramPath, "false");
                break;
            case NULL:
                add(paramPath, null);
        }
    }

}
