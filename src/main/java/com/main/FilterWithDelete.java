package com.main;

import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class FilterWithDelete implements Filter {
    private final Filter filter;
    public FilterWithDelete(Filter filter){
        this.filter = filter;
    }
    @Override
    public boolean call(Event event) {
        if(!this.filter.call(event)) return false;
        event.getData("msg").thenAccept(
                object -> {
                    if (object instanceof Message message) {
                        try {
                            event.bot.executeAsync(
                                    EditMessageReplyMarkup.builder()
                                            .chatId(message.getChatId())
                                            .messageId(message.getMessageId())
                                            .replyMarkup(InlineKeyboardMarkup.builder().build())
                                            .build()
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        event.setData("msg", "");
        return true;
    }
}
