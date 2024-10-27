package com.gameprocessor.dispatcher;

import com.gameprocessor.user.Request;

public interface Callback{
    void call(Request event);
}
