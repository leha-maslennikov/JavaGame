package com.gameprocessor.entities.items;

import com.gameprocessor.entities.Actions;
import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.resourcemanager.Resource;

public class Equipment extends Item {
    public Integer hp;
    public Integer ap;
    public boolean equiped;

    public Equipment() {
        super();
        this.ap = 0;
        this.hp = 0;
        this.equiped = false;
    }
    public Equipment(String name, String description, int hp, int ap) {
        super(name, description);
        this.hp = hp;
        this.ap = ap;
        this.equiped = false;
    }

    public boolean equip(Creature creature, Resource resource) {
        if(creature.getEquipment().contains(resource)) return false;
        creature.getEquipment().add(resource);
        creature.setMaxHp(creature.getMaxHp() + hp);
        creature.setHp(creature.getHp() + hp);
        creature.setAp(creature.getAp() + ap);
        equiped = true;
        return true;
    }

    public boolean unequip(Creature creature, Resource resource) {
        if(!creature.getEquipment().contains(resource)) return false;
        creature.getEquipment().remove(resource);
        creature.setMaxHp(creature.getMaxHp() - hp);
        creature.setHp(creature.getHp() - hp);
        creature.setAp(creature.getAp() - ap);
        equiped = false;
        return true;
    }

    @Override
    public Actions getActions() {
        var actions = Actions.builder();
        if(equiped) actions.addAction("unequip");
        else actions.addAction("equip");
        return  actions.build();
    }
}
