package com.github.amsemy.resty.json;

import com.github.amsemy.resty.json.annotation.RstField;
import com.github.amsemy.resty.json.annotation.RstGetter;
import com.github.amsemy.resty.json.annotation.RstPojo;
import com.github.amsemy.resty.json.annotation.RstType;
import com.github.amsemy.resty.json.annotation.RstTypeValue;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import javax.json.JsonValue;

/**
 * API for converting Java objects to JSON models.
 *
 * @see  javax.json
 */
public class RestyJson {

    private static final String MSG_ANNOTATION_ABSENT =
            "Cann't find '%2$s' annotation in '%1$s' class";
    private static final String MSG_ANNOTATION_DUPLICATION =
            "Duplication of '%2$s' annotation in '%1$s' class";
    private static final String MSG_ANNOTATION_INVALID_USING =
            "Invalid annotation '%2$s' using in '%1$s' class";
    private static final String MSG_POJO_FIELD_DUPLICATION =
            "Duplication of pojo json field '%2$s' mapping in '%1$s' class";
    private static final String MSG_POJO_GET_FAIL =
            "Cann't get pojo field/method '%2$s' value in '%1$s' class";
    private static final String MSG_TYPEVALUE_GET_FAIL =
            "Cann't get type field/method '%2$s' value in '%1$s' class";

    private static void addArrayValue(Object value,
            JsonArrayBuilder builder) throws RestyMappingException {
        if (value == null) {
            builder.addNull();
        } else {
            Class<?> type = value.getClass();
            if (type.isArray()) {
                builder.add(getArray(value));
            } else if (value instanceof BigDecimal) {
                builder.add((BigDecimal) value);
            } else if (value instanceof BigInteger) {
                builder.add((BigInteger) value);
            } else if (value instanceof Boolean) {
                builder.add((Boolean) value);
            } else if (value instanceof Byte) {
                builder.add((Byte) value);
            } else if (value instanceof Character) {
                builder.add((Character) value);
            } else if (value instanceof Collection) {
                builder.add(getCollection((Collection) value));
            } else if (value instanceof Double) {
                builder.add((Double) value);
            } else if (value instanceof Float) {
                builder.add((Float) value);
            } else if (value instanceof Integer) {
                builder.add((Integer) value);
            } else if (value instanceof JsonArrayBuilder) {
                builder.add((JsonArrayBuilder) value);
            } else if (value instanceof JsonObjectBuilder) {
                builder.add((JsonObjectBuilder) value);
            } else if (value instanceof JsonValue) {
                builder.add((JsonValue) value);
            } else if (value instanceof Long) {
                builder.add((Long) value);
            } else if (value instanceof Map) {
                builder.add(getMap((Map) value));
            } else if (value instanceof Short) {
                builder.add((Short) value);
            } else if (value instanceof String) {
                builder.add((String) value);
            } else {
                RstPojo rstPojo = type.getAnnotation(RstPojo.class);
                RstType rstType = type.getAnnotation(RstType.class);
                if (rstPojo != null && rstType != null) {
                    throw new RestyMappingException(
                            MSG_ANNOTATION_INVALID_USING,
                            type.getName(), RstPojo.class.getSimpleName() + ", "
                                    + RstType.class.getSimpleName());
                }
                if (rstPojo != null) {
                    builder.add(getObject(value));
                } else if (rstType != null) {
                    addArrayValue(getTypeValue(value), builder);
                } else {
                    builder.add(value.toString());
                }
            }
        }
    }

    private static void addObjectValue(String name, Object value,
            JsonObjectBuilder builder) throws RestyMappingException {
        if (value == null) {
            builder.addNull(name);
        } else {
            Class<?> type = value.getClass();
            if (type.isArray()) {
                builder.add(name, getArray(value));
            } else if (value instanceof BigDecimal) {
                builder.add(name, (BigDecimal) value);
            } else if (value instanceof BigInteger) {
                builder.add(name, (BigInteger) value);
            } else if (value instanceof Boolean) {
                builder.add(name, (Boolean) value);
            } else if (value instanceof Byte) {
                builder.add(name, (Byte) value);
            } else if (value instanceof Character) {
                builder.add(name, (Character) value);
            } else if (value instanceof Collection) {
                builder.add(name, getCollection((Collection) value));
            } else if (value instanceof Double) {
                builder.add(name, (Double) value);
            } else if (value instanceof Float) {
                builder.add(name, (Float) value);
            } else if (value instanceof Integer) {
                builder.add(name, (Integer) value);
            } else if (value instanceof JsonArrayBuilder) {
                builder.add(name, (JsonArrayBuilder) value);
            } else if (value instanceof JsonObjectBuilder) {
                builder.add(name, (JsonObjectBuilder) value);
            } else if (value instanceof JsonValue) {
                builder.add(name, (JsonValue) value);
            } else if (value instanceof Long) {
                builder.add(name, (Long) value);
            } else if (value instanceof Map) {
                builder.add(name, getMap((Map) value));
            } else if (value instanceof Short) {
                builder.add(name, (Short) value);
            } else if (value instanceof String) {
                builder.add(name, (String) value);
            } else {
                RstPojo rstPojo = type.getAnnotation(RstPojo.class);
                RstType rstType = type.getAnnotation(RstType.class);
                if (rstPojo != null && rstType != null) {
                    throw new RestyMappingException(
                            MSG_ANNOTATION_INVALID_USING,
                            type.getName(), RstPojo.class.getSimpleName() + ", "
                                    + RstType.class.getSimpleName());
                }
                if (rstPojo != null) {
                    builder.add(name, getObject(value));
                } else if (rstType != null) {
                    addObjectValue(name, getTypeValue(value), builder);
                } else {
                    builder.add(name, value.toString());
                }
            }
        }
    }

    private static void addObjectMembers(Object object, Class<?> type,
            Set<String> findedSet, JsonObjectBuilder builder)
            throws RestyMappingException {
        for (Method m : type.getDeclaredMethods()) {
            RstGetter rstGetter = m.getAnnotation(RstGetter.class);
            if (rstGetter != null) {
                String name = rstGetter.value();
                if (name.isEmpty()
                        || m.getParameterTypes().length > 0) {
                    throw new RestyMappingException(
                            MSG_ANNOTATION_INVALID_USING,
                            type.getName(), RstGetter.class.getSimpleName());
                }
                if (findedSet.add(name)) {
                    m.setAccessible(true);
                    Object value;
                    try {
                        value = m.invoke(object);
                    } catch (IllegalAccessException
                            | IllegalArgumentException
                            | InvocationTargetException ex) {
                        throw new RestyMappingException(MSG_POJO_GET_FAIL,
                                type.getName(), m.getName());
                    }
                    m.setAccessible(false);
                    addObjectValue(name, value, builder);
                } else {
                    throw new RestyMappingException(MSG_POJO_FIELD_DUPLICATION,
                            type.getName(), name);
                }
            }
        }
        for (Field f : type.getDeclaredFields()) {
            RstField rstField = f.getAnnotation(RstField.class);
            if (rstField != null) {
                String name = rstField.value();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                if (findedSet.add(name)) {
                    f.setAccessible(true);
                    Object value;
                    try {
                        value = f.get(object);
                    } catch (IllegalArgumentException
                            | IllegalAccessException ex) {
                        throw new RestyMappingException(MSG_POJO_GET_FAIL,
                                type.getName(), f.getName());
                    }
                    f.setAccessible(false);
                    addObjectValue(name, value, builder);
                } else {
                    throw new RestyMappingException(MSG_POJO_FIELD_DUPLICATION,
                            type.getName(), name);
                }
            }
        }
    }

    /**
     * Creates a JSON model of the object. Object can be an array, a collection,
     * a map, a json array builder, a json object builder or a POJO.
     *
     * @param  object
     *         the object.
     * @return  a json model.
     * @throws  RestyMappingException
     *          if there are errors of annotation using.
     */
    public static JsonStructure build(Object object)
            throws RestyMappingException {
        Class<?> type = object.getClass();
        if (type.isArray()) {
            return getArray(object).build();
        } else if (object instanceof Collection) {
            return getCollection((Collection) object).build();
        } else if (object instanceof Map) {
            return getMap((Map) object).build();
        } else if (object instanceof JsonArrayBuilder) {
            return ((JsonArrayBuilder) object).build();
        } else if (object instanceof JsonObjectBuilder) {
            return ((JsonObjectBuilder) object).build();
        } else {
            return getObject(object).build();
        }
    }

    /**
     * Converts an array to JSON builder.
     *
     * @param  array
     *         the array.
     * @return  a builder for creating JsonArray models.
     * @throws  RestyMappingException
     *          if there are errors of annotation using.
     */
    public static JsonArrayBuilder getArray(Object array)
            throws RestyMappingException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i ++) {
            addArrayValue(Array.get(array, i), builder);
        }
        return builder;
    }

    /**
     * Converts a collection to JSON builder.
     *
     * @param  collection
     *         the collection.
     * @return  a builder for creating JsonArray models.
     * @throws  RestyMappingException
     *          if there are errors of annotation using.
     */
    public static JsonArrayBuilder getCollection(Collection collection)
            throws RestyMappingException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Object value : collection) {
            addArrayValue(value, builder);
        }
        return builder;
    }

    /**
     * Converts a map to JSON builder.
     *
     * @param  map
     *         the map.
     * @return  a builder for creating JsonObject models.
     * @throws  RestyMappingException
     *          if there are errors of annotation using.
     */
    public static JsonObjectBuilder getMap(Map map)
            throws RestyMappingException {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for (Object key : map.keySet()) {
            addObjectValue(key.toString(), map.get(key), builder);
        }
        return builder;
    }

    /**
     * Converts an object to JSON builder.
     *
     * @param  object
     *         the object.
     * @return  a builder for creating JsonObject models.
     * @throws  RestyMappingException
     *          if there are errors of annotation using.
     */
    public static JsonObjectBuilder getObject(Object object)
            throws RestyMappingException {
        Class<?> type = object.getClass();
        Set<String> findedSet = new HashSet<>();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        addObjectMembers(object, type, findedSet, builder);
        while (true) {
            type = type.getSuperclass();
            if (type != null) {
                RstPojo rstPojo = type.getAnnotation(RstPojo.class);
                if (rstPojo != null) {
                    addObjectMembers(object, type, findedSet, builder);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return builder;
    }

    /**
     * Gets a value of the user defined data type.
     *
     * @param  typeObject
     *         an object of the user defined data type.
     * @return  an object.
     * @throws  RestyMappingException
     *          if there are errors of annotation using.
     */
    public static Object getTypeValue(Object typeObject)
            throws RestyMappingException {
        Class<?> type = typeObject.getClass();
        boolean isFinded = false;
        Object value = null;
        for (Method m : type.getDeclaredMethods()) {
            RstTypeValue rstTypeValue = m.getAnnotation(RstTypeValue.class);
            if (rstTypeValue != null) {
                if (m.getParameterTypes().length > 0) {
                    throw new RestyMappingException(
                            MSG_ANNOTATION_INVALID_USING,
                            type.getName(), RstTypeValue.class.getSimpleName());
                }
                if (isFinded) {
                    throw new RestyMappingException(MSG_ANNOTATION_DUPLICATION,
                            type.getName(), RstTypeValue.class.getSimpleName());
                } else {
                    isFinded = true;
                    m.setAccessible(true);
                    try {
                        value = m.invoke(typeObject);
                    } catch (IllegalAccessException
                            | IllegalArgumentException
                            | InvocationTargetException ex) {
                        throw new RestyMappingException(MSG_TYPEVALUE_GET_FAIL,
                                type.getName(), m.getName());
                    }
                    m.setAccessible(false);
                }
            }
        }
        for (Field f : type.getDeclaredFields()) {
            RstTypeValue rstTypeValue = f.getAnnotation(RstTypeValue.class);
            if (rstTypeValue != null) {
                if (isFinded) {
                    throw new RestyMappingException(MSG_ANNOTATION_DUPLICATION,
                            type.getName(), RstTypeValue.class.getSimpleName());
                } else {
                    isFinded = true;
                    f.setAccessible(true);
                    try {
                        value = f.get(typeObject);
                    } catch (IllegalArgumentException
                            | IllegalAccessException ex) {
                        throw new RestyMappingException(MSG_TYPEVALUE_GET_FAIL,
                                type.getName(), f.getName());
                    }
                    f.setAccessible(false);
                }
            }
        }
        if (!isFinded) {
            throw new RestyMappingException(MSG_ANNOTATION_ABSENT,
                    type.getName(), RstTypeValue.class.getSimpleName());
        }
        return value;
    }

}
