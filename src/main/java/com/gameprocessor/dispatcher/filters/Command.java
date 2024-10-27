package com.gameprocessor.dispatcher.filters;

import com.gameprocessor.user.Request;

/**
 * Применять только в MessageHandler
 */
public class Command implements Filter {
    private final String command;
    
    public Command(String command){
        this("/", command);
    }

    public Command(String prefix, String text){
        this.command = prefix+text;
    }

    @Override
    public boolean call(Request event){
        if (event.getCallbackData() == null) {
            System.out.println(Command.class + " null");
            return false;
        }
        return event.getCallbackData().equals(command);
    }
}
