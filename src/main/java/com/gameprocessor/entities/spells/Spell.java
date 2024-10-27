package com.gameprocessor.entities.spells;

import com.gameprocessor.entities.Actions;
import com.gameprocessor.entities.items.Sendable;

public class Spell implements Sendable {
    public int ap;

    public Spell(){}

    public Spell(int ap) {
        this.ap = ap;
    }

    /**
     * @return текст, который будет отправлен пользователю вместе с объектом
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * @return текст, который будет отправлен пользователю, после того, как он выберет данный объект
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * @return действия, которые можно проделать с объектом, после его выбора, должны быть реализованы в GameProcessor
     */
    @Override
    public Actions getActions() {
        return null;
    }
}
