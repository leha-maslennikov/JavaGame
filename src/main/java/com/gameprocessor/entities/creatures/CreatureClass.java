package com.gameprocessor.entities.creatures;

public class CreatureClass {
    public static final String info = "info";
    public final Creature race;

    public CreatureClass(){
        this.race = new Creature();
    }

    public CreatureClass(Creature race) {
        this.race = race;
    }

}
