package com.gameprocessor.dispatcher;

import com.gameprocessor.dispatcher.filters.Filter;
import com.gameprocessor.dispatcher.handlers.BaseHandler;
import com.gameprocessor.user.Request;

import java.util.LinkedList;

public class Dispatcher {
    private Filter filter;
    private final LinkedList<BaseHandler> handlers = new LinkedList<>();
    private final LinkedList<Dispatcher> dispatchers = new LinkedList<>();

    public boolean call(Request event){
        if(filter != null && !filter.call(event)) return false;
        for(BaseHandler handler: handlers){
            if(handler.call(event)){
                return true;
            }
        }
        for(Dispatcher dispatcher: dispatchers){
            if(dispatcher.call(event)){
                return true;
            }
        }
        return false;
    }
    public void addDispatcher(Dispatcher dispatcher){
        dispatchers.addLast(dispatcher);
    }

    public void addHandler(BaseHandler handler){
        handlers.addLast(handler);
    }

    public void addFilter(Filter filter){
        this.filter = filter;
    }
}
