package com.gameprocessor.entities.items;

import com.gameprocessor.entities.Actions;

/**
 * Интерфейс представляет объект,
 * который можно отправить пользователю.
 * Отправленный объект пользователь
 * может выбрать.
 */
public interface Sendable{
    /**
     * @return текст, который будет отправлен пользователю вместе с объектом
     */
    String getName();

    /**
     * @return текст, который будет отправлен пользователю, после того, как он выберет данный объект
     */
    String getDescription();

    /**
     * @return действия, которые можно проделать с объектом, после его выбора, должны быть реализованы в GameProcessor
     */
    Actions getActions();
}