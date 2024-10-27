package com.gameprocessor.entities;

import com.gameprocessor.entities.items.Item;

import java.util.LinkedList;

public class Inventory {
    private final LinkedList<Item> items;

    public Inventory(){
        items = new LinkedList<>();
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void putItem(Item item){
        items.add(item);
    }

    public void deleteItem(Item item){
        items.remove(item);
    }
}
