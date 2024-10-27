package com.gameprocessor.entities.items;

import com.gameprocessor.entities.Actions;
import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.ResourceManager;

import java.util.LinkedList;
import java.util.List;

public class Box extends Item {
    public List<Resource> items;

    public Box() {
        super();
        items = new LinkedList<>();
    }
    private Box(String name, String description) {
        super(name, description);
        this.items = new LinkedList<>();
    }

    public List<Resource> getItems() {
        return items;
    }

    @Override
    public Actions getActions() {
         var actions = Actions.builder();
         if(!items.isEmpty()) actions.addAction("loot");
         return actions.build();
    }

    public static BoxBuilder builder(String name, String description){
        return new BoxBuilder(name, description);
    }

    public static class BoxBuilder{
        private final Box box;
        private String userId;

        public BoxBuilder(String name, String description){
            this.box = new Box(name,description);
        }

        public BoxBuilder userId(String userId){
            if(this.userId == null) this.userId = userId;
            return this;
        }
        public BoxBuilder addItem(Item item) {
            if(this.userId == null) throw new RuntimeException("No userId");
            this.box.getItems().add(ResourceManager.createResource(this.userId, item));
            return this;
        }

        public Box build(){
            return this.box;
        }
    }
}
