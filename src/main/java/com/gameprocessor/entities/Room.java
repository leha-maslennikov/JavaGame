package com.gameprocessor.entities;

import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.entities.items.Item;
import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.ResourceManager;

import java.util.LinkedList;
import java.util.List;

public class Room {

    public String name;
    public List<Resource> items;
    public List<Resource> enemies;

    public Room(){}

    public Room(String name){
        this.name = name;
        this.items = new LinkedList<>();
        this.enemies = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public List<Resource> getItems() {
        return items;
    }
    public List<Resource> getEnemies(){return enemies;}

    public static RoomBuilder builder(String name){
        return new RoomBuilder(name);
    }

    public static class RoomBuilder{
        private final Room room;
        private String userId;

        public RoomBuilder(String name){
            this.room = new Room(name);
        }

        public RoomBuilder userId(String userId){
            if(this.userId == null) this.userId = userId;
            return this;
        }

        public RoomBuilder addItem(Item item){
            if(this.userId != null) {
                Resource resource = ResourceManager.createResource(this.userId, item);
                this.room.getItems().add(resource);
            }
            return this;
        }

        public RoomBuilder addEnemy(Creature enemy)
        {
            if(this.userId != null) {
                this.room.getEnemies().add(ResourceManager.createResource(this.userId, enemy));
            }
            return this;
        }
        public RoomBuilder addEnemies(List<Creature> enemies)
        {
            if(this.userId != null) {
                for (Creature enemy : enemies) {
                    this.room.getEnemies().add(ResourceManager.createResource(this.userId, enemy));
                }
            }
            return this;
        }

        public RoomBuilder addItems(List<Item> items)
        {
            if(this.userId != null) {
                for (Item item : items) {
                    Resource resource = ResourceManager.createResource(this.userId, item);
                    this.room.getItems().add(resource);
                }
            }
            return this;
        }

        public Room build(){
            return this.room;
        }
    }
}
