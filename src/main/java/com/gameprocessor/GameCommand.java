package com.gameprocessor;

import com.gameprocessor.user.Request;

public class GameCommand {
    protected final String command;
    protected final String[] args;

    public GameCommand(GameCommand gameCommand) {
        this.command = gameCommand.command;
        this.args = gameCommand.args;
    }
    public GameCommand(String command) throws GameException {
        if(!command.startsWith("/")) throw new GameException("Not a command: "+ command);
        String[] args = command.split(" ");
        this.command = args[0];
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public void run(Request request) {

    }
}
