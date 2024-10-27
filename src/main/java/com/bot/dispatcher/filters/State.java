package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public class State implements Filter{
    
    private final String state;

    public State(){
        this.state = "";
    }

    public State(String state){
        this.state = state;
    }

    @Override
    public boolean call(Event event){
        if(event.state == null) return false;
        try{
            Object obj = event.state.get();
            if(obj == null) obj = "";
            if(obj instanceof String state){
               return this.state.equals(state);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
