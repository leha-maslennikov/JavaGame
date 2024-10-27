package com.gameprocessor.entities.creatures;

public class Human extends Creature {
    public static final String info = "Human info";

    public Human() {
        super();
    }

    @Override
    public Creature init() {
        return new Human();
    }

    @Override
    public String getInfo() {
        return info;
    }
}
