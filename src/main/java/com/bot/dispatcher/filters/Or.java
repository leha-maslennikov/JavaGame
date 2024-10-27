package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public class Or implements Filter {

    private final Filter[] filters;

    public Or(Filter... filters){
        this.filters = filters;
    }

    public boolean call(Event event){
        for(Filter filter: filters){
            if(filter.call(event)){
                return true;
            }
        }
        return false;
    }
}
