package com.gameprocessor.dispatcher.handlers;

import com.gameprocessor.dispatcher.Callback;
import com.gameprocessor.dispatcher.filters.Filter;
import com.gameprocessor.user.Request;

public abstract class BaseHandler {
    protected final Filter filter;
    protected final Callback callback;
    public BaseHandler(Filter filter, Callback callback){
        this.filter = filter;
        this.callback = callback;
    }
    public abstract boolean call(Request event);
}
