package com.gameprocessor.entities;

import java.util.LinkedList;
import java.util.List;

public class Actions {
    public List<String> actions;

    public Actions(){
        this.actions = new LinkedList<>();
    }

    public void addAction(String action){
        this.actions.add(action);
    }

    public List<String> getActions(){
        return actions;
    }

    public static ActionsBuilder builder(){
        return new ActionsBuilder();
    }
    public static class ActionsBuilder {
        private final Actions actions;

        public ActionsBuilder(){
            this.actions = new Actions();
        }

        public ActionsBuilder addAction(String action){
            this.actions.addAction(action);
            return this;
        }

        public Actions build(){
            return actions;
        }
    }
}
