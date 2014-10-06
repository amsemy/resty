package com.github.amsemy.resty.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Запрос к ресурсу.
 */
public class RestyRequest {

    /**
     * Сообщение об ошибке, не связнное с каким-либо полем.
     */
    public static class CommonMessage extends Message {

        /**
         * Создаёт сообщение.
         *
         * @param  errorDesc
         *         Описание ошибки.
         */
        public CommonMessage(String errorDesc) {
            super(null, errorDesc);
        }

    }

    /**
     * Сообщение об ошибке.
     */
    public static class Message {

        public final String errorDesc;
        public String fieldName;


        /**
         * Создаёт сообщение.
         *
         * @param  fieldName
         *         Имя поля, к которому относится сообщение. Не может быть равно
         *         пустой строке.
         * @param  errorDesc
         *         Описание ошибки.
         */
        public Message(String fieldName, String errorDesc) {
            this.errorDesc = errorDesc;
            if (fieldName != null && fieldName.isEmpty()) {
                throw new IllegalArgumentException("Empty field name");
            }
            this.fieldName = fieldName;
        }

    }

    /**
     * Параметр запроса.
     */
    public static class Param {

        public final String paramName;
        public final int index;

        /**
         * Создаёт параметр.
         *
         * @param  paramName
         *         Имя параметра запроса. Не может быть равно пустой строке.
         * @param  index
         *         Номер элемента массива значений параметра.
         */
        public Param(String paramName, int index) {
            if (paramName.isEmpty()) {
                throw new IllegalArgumentException("Empty param name");
            }
            this.paramName = paramName;
            this.index = index;
        }

    }

    /**
     * Ограничения на размер значения.
     */
    public static class Size {

        public final int min;
        public final int max;

        /**
         * Создаёт ограничения.
         *
         * @param  min
         *         Минимальный размер.
         * @param  max
         *         Максимальный размер.
         */
        public Size(int min, int max) {
            this.min = min;
            this.max = max;
        }

    }

    /**
     * Валидатор запроса.
     */
    public class RestyValidator {

        private final Locale locale;

        private final String defaultBasename = RestyRequest.class.getName();

        /**
         * Создаёт валидатор запроса.
         *
         * @param  locale
         *         Локаль, используемая для формирования сообщений об ошибках.
         */
        protected RestyValidator(Locale locale) {
            this.locale = locale;
        }

        /**
         * Убеждается, что значение параметра является Boolean.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertBoolean(String paramName, Message message) {
            return assertBoolean(param(paramName), message);
        }

        /**
         * Убеждается, что значение параметра является Boolean.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertBoolean(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertBoolean");
            return makeValidation(param, checkBoolean(param), message);
        }

        /**
         * Убеждается, что значение параметра является Date.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertDate(String paramName, Message message) {
            return assertDate(param(paramName), message);
        }

        /**
         * Убеждается, что значение параметра является Date.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertDate(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertDate");
            return makeValidation(param, checkDate(param), message);
        }

        /**
         * Убеждается, что параметр запроса существует.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertExists(String paramName, Message message) {
            return assertExists(param(paramName), message);
        }

        /**
         * Убеждается, что параметр запроса существует.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertExists(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertExists");
            return makeValidation(param, checkExists(param), message);
        }

        /**
         * Убеждается, что значение параметра является Float.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertFloat(String paramName, Message message) {
            return assertFloat(param(paramName), message);
        }

        /**
         * Убеждается, что значение параметра является Float.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertFloat(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertFloat");
            return makeValidation(param, checkFloat(param), message);
        }

        /**
         * Убеждается, что значение параметра является Integer.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertInteger(String paramName, Message message) {
            return assertInteger(param(paramName), message);
        }

        /**
         * Убеждается, что значение параметра является Integer.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertInteger(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertInteger");
            return makeValidation(param, checkInteger(param), message);
        }

        /**
         * Убеждается, что значение параметра является Long.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertLong(String paramName, Message message) {
            return assertLong(param(paramName), message);
        }

        /**
         * Убеждается, что значение параметра является Long.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertLong(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertLong");
            return makeValidation(param, checkLong(param), message);
        }

        /**
         * Убеждается, что значение параметра не равно {@code null} и не равно
         * пустой строке.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertNotEmpty(String paramName, Message message) {
            return assertNotEmpty(param(paramName), message);
        }

        /**
         * Убеждается, что значение параметра не равно {@code null} и не равно
         * пустой строке.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertNotEmpty(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertNotEmpty");
            return makeValidation(param, checkNotEmpty(param), message);
        }

        /**
         * Убеждается, что значение параметра является Short.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertShort(String paramName, Message message) {
            return assertShort(param(paramName), message);
        }

        /**
         * Убеждается, что значение параметра является Short.
         *
         * @param  param
         *         Параметр запроса.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertShort(Param param, Message message) {
            message = defaultI18nMessage(param, message, "assertShort");
            return makeValidation(param, checkShort(param), message);
        }

        /**
         * Убеждается, что значение параметра соответствует ограничениям на
         * размер.
         *
         * @param  paramName
         *         Имя параметра запроса.
         * @param  size
         *         Ограничения на размер значения.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertSize(String paramName, Size size,
                Message message) {
            return assertSize(param(paramName), size, message);
        }

        /**
         * Убеждается, что значение параметра соответствует ограничениям на
         * размер.
         *
         * @param  param
         *         Параметр запроса.
         * @param  size
         *         Ограничения на размер значения.
         * @param  message
         *         Сообщение об ошибке.
         * @return  {@code true}, если проверка успешна.
         *
         * @see  RestyValidator#makeValidation
         */
        protected boolean assertSize(Param param, Size size, Message message) {
            if (size.min == 0) {
                message = defaultI18nMessage(param, message,
                        "assertSize_max", size.max);
            } else {
                message = defaultI18nMessage(param, message,
                        "assertSize", size.min, size.max);
            }
            return makeValidation(param, checkSize(param, size), message);
        }

        private Message defaultI18nMessage(Param param, Message message,
                String descKey, Object... args) {
            if (message == null) {
                if (param == null) {
                    return commonMessage(i18n(descKey, defaultBasename, args));
                } else {
                    return message(param.paramName,
                            i18n(descKey, defaultBasename, args));
                }
            } else {
                return message;
            }
        }

        /**
         * Получает интернационализированную строку по ключу сообщения.
         *
         * @param  key
         *         Ключ сообщения.
         * @param  basename
         *         Имя ресурсного комплекта.
         * @param  args
         *         Параметры для форматирования сообщения.
         * @return  Интернационализированная строка.
         */
        protected String i18n(String key, String basename, Object... args) {
            ResourceBundle rb = ResourceBundle.getBundle(basename, locale);
            return (args.length == 0
                    ? rb.getString(key)
                    :String.format(rb.getString(key), args));
        }

        /**
         * Убеждается, что условие истинно. Если условие не выполняется, то
         * записывается сообщение об ошибке.
         *
         * @param  condition
         *         Проверяемое условие.
         * @param  message
         *         Сообщение об ошибке.
         * @return  Значение параметра {@code condition}.
         * @throws  IllegalArgumentException
         *          Если невозможно определить к какому полю относится ошибка.
         */
        protected boolean makeValidation(boolean condition, Message message) {
            if (message == null) {
                throw new IllegalArgumentException(
                        "Can't determine field name");
            }
            return makeValidation(null, condition, message);
        }

        /**
         * Убеждается, что условие истинно. Если условие не выполняется, то
         * записывается сообщение об ошибке. Если сообщение об обшибке относится
         * к полю и имя поля, к которому относится ошибка равно {@code null}, то
         * за имя поля будет взято имя параметра запроса.
         *
         * @param  param
         *         Параметр запроса.
         * @param  condition
         *         Проверяемое условие.
         * @param  message
         *         Сообщение об ошибке.
         * @return  Значение параметра {@code condition}.
         * @throws  IllegalArgumentException
         *          Если невозможно определить к какому полю относится ошибка.
         */
        protected boolean makeValidation(Param param, boolean condition,
                Message message) {
            if (!condition) {
                if (message == null) {
                    message = defaultI18nMessage(param, null, "makeValidation");
                } else {
                    if (!(message instanceof CommonMessage)
                            && message.fieldName == null) {
                        if (param == null) {
                            throw new IllegalArgumentException(
                                    "Can't determine field name");
                        }
                        message.fieldName = param.paramName;
                    }
                }
                valResult.addError(fieldPath(message.fieldName),
                        message.errorDesc);
            }
            return condition;
        }

    }

    private final String[] fieldPath;
    private final String paramPath;
    private final RestyParams params;
    private final RestyValidationResult valResult;

    /**
     * Создаёт запрос.
     *
     * @param  params
     *         Параметры запроса к ресурсу.
     */
    public RestyRequest(RestyParams params) {
        this.params = params;
        fieldPath = null;
        paramPath = null;
        valResult = new RestyValidationResult();
    }

    /**
     * Создаёт подзапрос на основе родительского запроса. Используется для
     * получения вложенных сущностей.
     *
     * @param  parent
     *         Родительский запрос.
     * @param  paramName
     *         Название параметра, содержащего вложенную сущность. Используется
     *         для формирования пути к вложенным параметрам. Если равно
     *         {@code null}, то параметры берутся по родительскому пути.
     *         Не может быть равно пустой строке.
     */
    public RestyRequest(RestyRequest parent, String paramName) {
        params = parent.params;
        if (paramName != null && paramName.isEmpty()) {
            throw new IllegalArgumentException("Empty param name");
        }
        fieldPath = parent.fieldPath(paramName);
        paramPath = parent.paramPath(paramName);
        valResult = parent.valResult;
    }

    /**
     * Проверяет, что значение параметра является Boolean.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkBoolean(String paramName) {
        return checkBoolean(param(paramName));
    }

    /**
     * Проверяет, что значение параметра является Boolean.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkBoolean(Param param) {
        if (checkNotEmpty(param)) {
            try {
                String value = getString(param.paramName, param.index);
                strToBoolean(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что значение параметра является Date.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkDate(String paramName) {
        return checkDate(param(paramName));
    }

    /**
     * Проверяет, что значение параметра является Date.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkDate(Param param) {
        if (checkNotEmpty(param)) {
            try {
                String value = getString(param.paramName, param.index);
                strToDate(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что параметр запроса существует.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkExists(String paramName) {
        return checkExists(param(paramName));
    }

    /**
     * Проверяет, что параметр запроса существует.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkExists(Param param) {
        String paramName = paramPath(param.paramName);
        if (params.containsKey(paramName)) {
            if (param.index == 0) {
                return true;
            } else {
                List<String> values = params.get(paramName);
                return (param.index > 0
                        && values != null && param.index < values.size());
            }
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что значение параметра является Float.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkFloat(String paramName) {
        return checkFloat(param(paramName));
    }

    /**
     * Проверяет, что значение параметра является Float.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkFloat(Param param) {
        if (checkNotEmpty(param)) {
            try {
                String value = getString(param.paramName, param.index);
                strToFloat(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что значение параметра является Integer.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkInteger(String paramName) {
        return checkInteger(param(paramName));
    }

    /**
     * Проверяет, что значение параметра является Integer.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkInteger(Param param) {
        if (checkNotEmpty(param)) {
            try {
                String value = getString(param.paramName, param.index);
                strToInteger(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что значение параметра является Long.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkLong(String paramName) {
        return checkLong(param(paramName));
    }

    /**
     * Проверяет, что значение параметра является Long.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkLong(Param param) {
        if (checkNotEmpty(param)) {
            try {
                String value = getString(param.paramName, param.index);
                strToLong(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что значение параметра не равно {@code null} и не равно пустой
     * строке.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkNotEmpty(String paramName) {
        return checkNotEmpty(param(paramName));
    }

    /**
     * Проверяет, что значение параметра не равно {@code null} и не равно пустой
     * строке.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkNotEmpty(Param param) {
        if (checkExists(param)) {
            String value = getString(param.paramName, param.index);
            return (value != null && !value.isEmpty());
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что значение параметра является Short.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkShort(String paramName) {
        return checkShort(param(paramName));
    }

    /**
     * Проверяет, что значение параметра является Short.
     *
     * @param  param
     *         Параметр запроса.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkShort(Param param) {
        if (checkNotEmpty(param)) {
            try {
                String value = getString(param.paramName, param.index);
                strToShort(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что значение параметра соответствует ограничениям на размер.
     *
     * @param  paramName
     *         Имя параметра запроса.
     * @param  size
     *         Ограничения на размер значения.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkSize(String paramName, Size size) {
        return checkSize(param(paramName), size);
    }

    /**
     * Проверяет, что значение параметра соответствует ограничениям на размер.
     *
     * @param  param
     *         Параметр запроса.
     * @param  size
     *         Ограничения на размер значения.
     * @return  {@code true}, если проверка успешна.
     */
    public boolean checkSize(Param param, Size size) {
        if (size.min < 0 || size.min > size.max) {
            throw new IllegalArgumentException();
        }
        if (checkExists(param)) {
            String value = getString(param.paramName, param.index);
            int length = (value == null ? 0 : value.length());
            return (length >= size.min && length <= size.max);
        } else {
            return false;
        }
    }

    /**
     * Возвращает значение параметра как Boolean.
     *
     * @param  paramName
     *         Имя параметра.
     * @return  Значение параметра.
     */
    public Boolean getBoolean(String paramName) {
        return getBoolean(paramName, 0);
    }

    /**
     * Возвращает значение параметра как Boolean.
     *
     * @param  paramName
     *         Имя параметра.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Значение параметра.
     */
    public Boolean getBoolean(String paramName, int index) {
        String value = getString(paramName, index);
        return (value == null || value.isEmpty()
                ? null : strToBoolean(value));
    }

    /**
     * Возвращает значение параметра как Date.
     *
     * @param  paramName
     *         Имя параметра.
     * @return  Значение параметра.
     */
    public Date getDate(String paramName) {
        return getDate(paramName, 0);
    }

    /**
     * Возвращает значение параметра как Date.
     *
     * @param  paramName
     *         Имя параметра.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Значение параметра.
     */
    public Date getDate(String paramName, int index) {
        String value = getString(paramName, index);
        return (value == null || value.isEmpty()
                ? null : strToDate(value));
    }

    /**
     * Возвращает список ошибок валидации.
     *
     * @return  Список ошибок валидации.
     */
    public Map<String, Object> getErrors() {
        return valResult.getErrors();
    }

    /**
     * Возвращает значение параметра как Float.
     *
     * @param  paramName
     *         Имя параметра.
     * @return  Значение параметра.
     */
    public Float getFloat(String paramName) {
        return getFloat(paramName, 0);
    }

    /**
     * Возвращает значение параметра как Float.
     *
     * @param  paramName
     *         Имя параметра.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Значение параметра.
     */
    public Float getFloat(String paramName, int index) {
        String value = getString(paramName, index);
        return (value == null || value.isEmpty()
                ? null : strToFloat(value));
    }

    /**
     * Возвращает значение параметра как Integer.
     *
     * @param  paramName
     *         Имя параметра.
     * @return  Значение параметра.
     */
    public Integer getInteger(String paramName) {
        return getInteger(paramName, 0);
    }

    /**
     * Возвращает значение параметра как Integer.
     *
     * @param  paramName
     *         Имя параметра.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Значение параметра.
     */
    public Integer getInteger(String paramName, int index) {
        String value = getString(paramName, index);
        return (value == null || value.isEmpty()
                ? null : strToInteger(value));
    }

    /**
     * Возвращает значение параметра как Long.
     *
     * @param  paramName
     *         Имя параметра.
     * @return  Значение параметра.
     */
    public Long getLong(String paramName) {
        return getLong(paramName, 0);
    }

    /**
     * Возвращает значение параметра как Long.
     *
     * @param  paramName
     *         Имя параметра.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Значение параметра.
     */
    public Long getLong(String paramName, int index) {
        String value = getString(paramName, index);
        return (value == null || value.isEmpty()
                ? null : strToLong(value));
    }

    /**
     * Возвращает список имён параметров запроса.
     *
     * @return  Список имён параметров запроса.
     */
    public Set<String> getParameterNames() {
        if (paramPath == null) {
            return params.keySet();
        } else {
            Set<String> names = new HashSet<>();
            for (String key : params.keySet()) {
                if (!key.equals(paramPath) && key.startsWith(paramPath)) {
                    String name = key.substring(paramPath.length());
                    int i = name.indexOf("]");
                    names.add(name.substring(1, i) + name.substring(i + 1));
                }
            }
            return names;
        }
    }

    /**
     * Возвращает значение параметра как Short.
     *
     * @param  paramName
     *         Имя параметра.
     * @return  Значение параметра.
     */
    public Short getShort(String paramName) {
        return getShort(paramName, 0);
    }

    /**
     * Возвращает значение параметра как Short.
     *
     * @param  paramName
     *         Имя параметра.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Значение параметра.
     */
    public Short getShort(String paramName, int index) {
        String value = getString(paramName, index);
        return (value == null || value.isEmpty()
                ? null : strToShort(value));
    }

    /**
     * Возвращает значение параметра.
     *
     * @param  paramName
     *         Имя параметра.
     * @return  Значение параметра.
     */
    public String getString(String paramName) {
         return getString(paramName, 0);
    }

    /**
     * Возвращает значение параметра.
     *
     * @param  paramName
     *         Имя параметра.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Значение параметра.
     */
    public String getString(String paramName, int index) {
        List<String> values = params.get(paramPath(paramName));
        if (values != null) {
            if (index >= 0 && index < values.size()) {
                return values.get(index);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Является ли валидным запрос.
     *
     * @return  {@code true}, если запрос валидный.
     */
    public boolean isValid() {
        return valResult.isValid();
    }

    private String[] fieldPath(String paramName) {
        if (paramName == null) {
            return fieldPath;
        } else {
            String[] result;
            if (fieldPath == null) {
                result = new String[] { paramName };
            } else {
                result = new String[fieldPath.length + 1];
                System.arraycopy(fieldPath, 0, result, 0, fieldPath.length);
                result[fieldPath.length] = paramName;
            }
            return result;
        }
    }

    private String paramPath(String paramName) {
        if (paramName == null) {
            return paramPath;
        } else {
            return (paramPath == null
                    ? paramName
                    : paramPath + "[" + paramName + "]");
        }
    }

    //------------------------------------------------------------------------

    /**
     * Обёртка для создания сообщения об ошибке, не связнного с каким-либо
     * полем.
     *
     * @param  errorDesc
     *         Описание сообщения.
     * @return  Сообщение об ошибке.
     */
    public static CommonMessage commonMessage(String errorDesc) {
        return new CommonMessage(errorDesc);
    }

    /**
     * Обёртка для создания ограничения на максимальный размер значения.
     *
     * @param  max
     *         Максимальный размер.
     * @return  Ограничения на размер.
     */
    public static Size maxSize(int max) {
        return new Size(0, max);
    }

    /**
     * Обёртка для создания сообщения об ошибке, относящегося к полю. При записи
     * ошибки в качестве имени поля будет использовано имя параметра запроса.
     *
     * @param  errorDesc
     *         Описание ошибки.
     * @return  Сообщение об ошибке.
     */
    public static Message message(String errorDesc) {
        return message(null, errorDesc);
    }

    /**
     * Обёртка для создания сообщения об ошибке, относящегося к полю. Ошибка
     * записывается с указанным именем поля.
     *
     * @param  fieldName
     *         Имя поля, к которому относится сообщение.
     * @param  errorDesc
     *         Описание ошибки.
     * @return  Сообщение об ошибке.
     */
    public static Message message(String fieldName, String errorDesc) {
        return new Message(fieldName, errorDesc);
    }

    /**
     * Обёртка для создания параметра запроса.
     *
     * @param  paramName
     *         Имя параметра запроса. Не может быть равно пустой строке.
     * @return  Параметр запроса.
     */
    public static Param param(String paramName) {
        return param(paramName, 0);
    }

    /**
     * Обёртка для создания параметра запроса.
     *
     * @param  paramName
     *         Имя параметра запроса. Не может быть равно пустой строке.
     * @param  index
     *         Номер элемента массива значений параметра.
     * @return  Параметр запроса.
     */
    public static Param param(String paramName, int index) {
        return new Param(paramName, index);
    }

    /**
     * Обёртка для создания ограничения на размер значения.
     *
     * @param  min
     *         Минимальный размер.
     * @param  max
     *         Максимальный размер.
     * @return  Ограничения на размер.
     */
    public static Size size(int min, int max) {
        return new Size(min, max);
    }

    //------------------------------------------------------------------------

    private static Boolean strToBoolean(String value) {
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("Not a boolean value");
        }
    }

    private static Date strToDate(String value) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(value);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private static Float strToFloat(String value) {
        return Float.parseFloat(value);
    }

    private static Integer strToInteger(String value) {
        return Integer.parseInt(value);
    }

    private static Long strToLong(String value) {
        return Long.parseLong(value);
    }

    private static Short strToShort(String value) {
        return Short.parseShort(value);
    }

}
