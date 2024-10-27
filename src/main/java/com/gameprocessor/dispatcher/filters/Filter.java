package com.gameprocessor.dispatcher.filters;

import com.gameprocessor.user.Request;

public interface Filter {
    boolean call(Request event);
}
