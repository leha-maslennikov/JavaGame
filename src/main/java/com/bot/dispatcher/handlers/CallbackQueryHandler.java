package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;

public class CallbackQueryHandler extends BaseHandler{
    public CallbackQueryHandler(Filter filter, Callback callback){
        super(filter, callback);
    }
    @Override
    public boolean call(Event event){
        if(!event.event.hasCallbackQuery()) {
            return false;
        }
        if(event.userId==-1){
            event.userId = event.event.getCallbackQuery().getFrom().getId();
            event.state = event.getData("state");
        }
        if(filter.call(event)){
            callback.call(event);
            return true;
        }
        return false;
    }
}
