package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public interface Filter {
    boolean call(Event event);
}
