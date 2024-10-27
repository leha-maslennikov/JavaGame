package com.gameprocessor.entities.items;

import com.gameprocessor.entities.Actions;

/**
 * Игровой предмет
 */
public class Item implements Sendable{
    public String name;
    public String description;

    public Item() {
        name = "no name";
        description = "no description";
    }

    public Item(String name, String description){
        this.name = name;
        this.description = description;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return name+"\n"+description;
    }

    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("take")
                .addAction("throw")
                .build();
    }
}
