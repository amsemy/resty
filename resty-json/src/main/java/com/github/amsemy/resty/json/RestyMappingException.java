package com.github.amsemy.resty.json;

/**
 * Общее исключение для всех ошибок, которые возникают при работе с Resty.
 */
public class RestyMappingException extends Exception {

    /**
     * Создаёт исключение без детализирующего сообщения.
     */
    public RestyMappingException() {
        super();
    }

    /**
     * Создаёт исключение с детализирующим сообщением.
     *
     * @param  message
     *         Детализирующее сообщение.
     */
    public RestyMappingException(String message) {
        super(message);
    }

    /**
     * Создаёт исключение с детализирующим сообщением.
     *
     * @param  message
     *         Детализирующее сообщение.
     * @param  params
     *         Параметры для форматирования сообщения.
     *
     * @see  java.util.Formatter
     */
    public RestyMappingException(String message, Object... params) {
        super(String.format(message, params));
    }

}
