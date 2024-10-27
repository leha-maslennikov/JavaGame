package com.gameprocessor.dispatcher.filters;

import com.gameprocessor.user.Request;

public class And implements Filter {

    private final Filter[] filters;

    public And(Filter... filters){
        this.filters = filters;
    }

    @Override
    public boolean call(Request event){
        for(Filter filter: filters){
            if(!filter.call(event)){
                return false;
            }
        }
        return true;
    }
}
