package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;

public class EventHandler extends BaseHandler{
    public EventHandler(Filter filter, Callback callback){
        super(filter, callback);
    }
    @Override
    public boolean call(Event event){
        if(filter.call(event)){
            callback.call(event);
            return true;
        }
        return false;
    }
}
