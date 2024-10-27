package com.gameprocessor.entities.spells;

import com.gameprocessor.entities.Actions;
import com.gameprocessor.entities.items.Sendable;

import java.util.LinkedList;

public class SpellBook implements Sendable {

    public LinkedList<Spell> spells;

    /**
     * @return текст, который будет отправлен пользователю вместе с объектом
     */
    @Override
    public String getName() {
        return "Книга заклинаний";
    }

    /**
     * @return текст, который будет отправлен пользователю, после того, как он выберет данный объект
     */
    @Override
    public String getDescription() {
        return "Вы можете применить:";
    }

    /**
     * @return действия, которые можно проделать с объектом, после его выбора, должны быть реализованы в GameProcessor
     */
    @Override
    public Actions getActions() {
        return null;
    }
}
