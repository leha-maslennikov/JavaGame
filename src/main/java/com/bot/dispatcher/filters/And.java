package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public class And implements Filter {

    private final Filter[] filters;

    public And(Filter ... filters){
        this.filters = filters;
    }

    @Override
    public boolean call(Event event){
        for(Filter filter: filters){
            if(!filter.call(event)){
                return false;
            }
        }
        return true;
    }
}
