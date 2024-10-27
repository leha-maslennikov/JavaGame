package com.gameprocessor.dispatcher.handlers;

import com.gameprocessor.dispatcher.Callback;
import com.gameprocessor.dispatcher.filters.Filter;
import com.gameprocessor.user.Request;

public class Handler extends BaseHandler {
    public Handler(Filter filter, Callback callback) {
        super(filter, callback);
    }

    @Override
    public boolean call(Request event) {
        if(filter.call(event)) {
            callback.call(event);
            return true;
        }
        return false;
    }
}
