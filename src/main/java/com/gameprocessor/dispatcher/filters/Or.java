package com.gameprocessor.dispatcher.filters;

import com.gameprocessor.user.Request;

public class Or implements Filter {

    private final Filter[] filters;

    public Or(Filter... filters){
        this.filters = filters;
    }

    public boolean call(Request event){
        for(Filter filter: filters){
            if(filter.call(event)){
                return true;
            }
        }
        return false;
    }
}
