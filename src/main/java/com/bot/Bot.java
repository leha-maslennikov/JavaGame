package com.bot;

import com.bot.storage.MemoryStorage;
import com.bot.storage.Storage;
import com.bot.dispatcher.Dispatcher;
import com.bot.dispatcher.Event;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot extends TelegramLongPollingBot {

    private final String botName;
    private final Dispatcher dispatcher;
    private final Storage storage;
    private final ExecutorService threadPool;

    public Bot(DefaultBotOptions options, String botToken, String botName, Dispatcher dispatcher, Storage storage, int threadCount){
        super(options, botToken);
        this.botName = botName;
        this.dispatcher = dispatcher;
        this.storage = storage;
        this.threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public Bot(String botToken, String botName, Dispatcher dispatcher){
        this(new DefaultBotOptions(), botToken, botName, dispatcher, new MemoryStorage(), 1);
    }

    public Bot(DefaultBotOptions options, String botToken, String botName, Dispatcher dispatcher){
        this(options, botToken, botName, dispatcher, new MemoryStorage(), 1);
    }

    public Bot(DefaultBotOptions options, String botToken, String botName, Dispatcher dispatcher, int threadCount){
        this(options, botToken, botName, dispatcher, new MemoryStorage(), threadCount);
    }

    /**
     * @param userId id пользователя
     * @param key    название ключа для данных
     * @param value  данные
     * @return возвращает передаваемые данные
     */
    public CompletableFuture<Object> setUserData(long userId, String key, Object value) {
        return storage.setUserDataAsync(userId, key, value);
    }

    /**
     * @param userId id пользователя
     * @param key    название ключа для данных
     * @return возвращает данные или null
     */
    public CompletableFuture<Object> getUserData(long userId, String key){
        return storage.getUserDataAsync(userId, key);
    }

    /**
     * Сбрасывает все данные пользователя
     * @param userId id пользователя
     */
    public CompletableFuture<Void> clearUserData(long userId){
        return storage.clearUserDataAsync(userId);
    }

    /**
     * This method is called when receiving updates via GetUpdates method
     *
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(Update update) {
        threadPool.execute(
                ()->{
                    dispatcher.call(new Event(this, update));
                }
        );
    }

    /**
     * Return username of this bot
     */
    @Override
    public String getBotUsername() {
        return botName;
    }
}
