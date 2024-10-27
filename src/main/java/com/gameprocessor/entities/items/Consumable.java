package com.gameprocessor.entities.items;

import com.gameprocessor.entities.Actions;
import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.entities.items.Item;

public class Consumable extends Item {
    public Integer hp;

    public Consumable(){}
    public Consumable(String name, String description, int hp) {
        super(name, description);
        this.hp = hp;
    }
    public Integer getHp(){return hp;}
    public void useConsumable(Creature creature)
    {
        creature.setHp(this.getHp() + creature.getHp());
    }

    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("use")
                .build();
    }
}
