package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;

public abstract class BaseHandler{
    protected final Filter filter;
    protected final Callback callback;
    public BaseHandler(Filter filter, Callback callback){
        this.filter = filter;
        this.callback = callback;
    }
    public abstract boolean call(Event event);
}
