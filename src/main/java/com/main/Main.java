package com.main;

import com.bot.TelegramView;
import com.cli.CLIView;
import com.gameprocessor.*;

public class Main {
    public static void main(String[] args) {
        //ResourceManager.storage = new FilesStorage();
        GameProcessor gameProcessor = new GameProcessor();
        CLIView cliView = new CLIView(gameProcessor);
        cliView.start();
        //TelegramView telegramView = new TelegramView(gameProcessor);
        //telegramView.start();
    }
}