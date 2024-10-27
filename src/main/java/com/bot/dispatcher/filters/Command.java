package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

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
    public boolean call(Event event){
        if (!event.event.getMessage().hasText()) return false;
        return event.event.getMessage().getText().equals(command);
    }
}
