package com.bot;

import com.bot.dispatcher.Dispatcher;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Command;
import com.bot.dispatcher.filters.Or;
import com.bot.dispatcher.filters.State;
import com.bot.dispatcher.handlers.CallbackQueryHandler;
import com.bot.dispatcher.handlers.MessageHandler;
import com.gameprocessor.GameProcessor;
import com.gameprocessor.user.Request;
import com.gameprocessor.user.Response;
import com.main.FilterWithDelete;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.util.LinkedList;

public class TelegramView {
    public final GameProcessor gameProcessor;

    public TelegramView() {
        gameProcessor = new GameProcessor();
    }

    public TelegramView(GameProcessor gameProcessor) {
        this.gameProcessor = gameProcessor;
    }

    public final Bot bot = new Bot(
            options(),
            "5787246678:AAFXIoyuSRUw95D4a56XVZaY8TSXZl-NHDM",
            "RPG_Bot",
            createDispatcher()
    );

    public static DefaultBotOptions options(){
        DefaultBotOptions options = new DefaultBotOptions();
        options.setMaxThreads(2);
        return options;
    }

    public static void send(Event event, Response response) {
        long chatId = 1;
        if(event.event.hasMessage()) chatId = event.event.getMessage().getChatId();
        else if(event.event.hasCallbackQuery()) chatId = event.event.getCallbackQuery().getMessage().getChatId();
        var msg = SendMessage.builder().chatId(chatId).text(response.text);
        if(response.objects != null && !response.objects.isEmpty()) {
            event.setData("state", "send");
            var builder = InlineKeyboardMarkup.builder();
            for(Response.ResponseObject responseObject: response.objects) {
                LinkedList<InlineKeyboardButton> row = new LinkedList<>();
                row.add(
                        InlineKeyboardButton.builder()
                                .text(responseObject.text)
                                .callbackData(responseObject.callbackData)
                                .build()
                );
                builder.keyboardRow(row);
            }
            msg.replyMarkup(builder.build());
        }
        try {
            event.bot.executeAsync(
                    msg.build()
            ).thenAccept(
                    newMsg -> event.setData("msg", newMsg)
            );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //ResourceManager.storage = new FilesStorage();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Dispatcher createDispatcher(){
        Dispatcher dp = new Dispatcher();

        dp.addHandler(
                new MessageHandler(
                        new FilterWithDelete(
                                new Or(
                                        new Command("start"),
                                        new Command("inspect"),
                                        new Command("data"),
                                        new Command("retry"),
                                        new Command("bag")
                                )
                        ),
                        (event)->{
                            Response response = this.gameProcessor.handleRequest(
                                    Request.builder()
                                            .userId(event.userId)
                                            .callbackData(event.event.getMessage().getText())
                                            .build()
                            );
                            send(event, response);
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Or(
                                new Command("help"),
                                new Command("attack"),
                                new Command("await")
                        ),
                        (event)->{
                            Response response = this.gameProcessor.handleRequest(
                                    Request.builder()
                                            .userId(event.userId)
                                            .callbackData(event.event.getMessage().getText())
                                            .build()
                            );
                            send(event, response);
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        event -> true,
                        (event)->{
                            Response response = this.gameProcessor.handleRequest(
                                    Request.builder()
                                            .userId(event.userId)
                                            .callbackData(event.event.getMessage().getText())
                                            .build()
                            );
                            send(event, response);
                        }
                )
        );

        dp.addHandler(
                new CallbackQueryHandler(
                        new FilterWithDelete(new State("send")),
                        event -> {
                            event.setData("state", "");
                            try {
                                String callbackData = event.event.getCallbackQuery().getData();
                                Response response = this.gameProcessor.handleRequest(Request.builder()
                                        .userId(event.userId)
                                        .callbackData(callbackData)
                                        .build());
                                send(event, response);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                )
        );

        return dp;
    }
}