package com.gameprocessor;

import com.gameprocessor.user.Request;

public class Start extends GameCommand {
    public Start(GameCommand gameCommand) {
        super(gameCommand);
        if(!gameCommand.command.equals("/start")) {
            throw new RuntimeException("Wrong command: " + gameCommand.command);
        }
    }
    public Start(String command) throws GameException {
        super(command);
    }

    @Override
    public void run(Request request) {

    }
}
