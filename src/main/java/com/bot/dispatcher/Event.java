package com.bot.dispatcher;

import com.bot.Bot;
import com.fasterxml.jackson.databind.deser.std.MapEntryDeserializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Event{

    public final Update event;
    public final Bot bot;
    public long userId;
    public CompletableFuture<Object> state;

    public Event(Bot bot, Update event, long userId){
        this.bot = bot;
        this.event = event;
        this.userId = userId;
        this.state = null;
    }
    public Event(Bot bot, Update event){
        this(bot, event, -1);
    }

    /**
     * @param key    название ключа для данных
     * @param value  данные
     * @return возвращает передаваемые данные
     */
    public CompletableFuture<Object> setData(String key, Object value){
        return bot.setUserData(this.userId, key, value);
    }

    /**
     * @param key название ключа для данных
     * @return возвращает данные или null
     */
    public CompletableFuture<Object> getData(String key){
        return bot.getUserData(this.userId, key);
    }

    /**
     * Сбрасывает все данные пользователя
     * @return CompletableFuture<Void>
     */
    public CompletableFuture<Void> clear(){
        return bot.clearUserData(this.userId);
    }
}
