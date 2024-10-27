package com.gameprocessor.entities.spells;

import com.gameprocessor.entities.creatures.Creature;

public class Attack extends Spell {
    public Attack(int ap) {
        super(ap);
    }

    public Attack() {}

    public Spell get(Creature creature) {
        return new Spell(creature.getAp());
    }
}
