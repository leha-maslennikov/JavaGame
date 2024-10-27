package com.gameprocessor;

import com.gameprocessor.dispatcher.Dispatcher;
import com.gameprocessor.dispatcher.filters.State;
import com.gameprocessor.dispatcher.handlers.Handler;
import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.resourcemanager.ResourceManager;
import com.gameprocessor.user.Response;
import com.gameprocessor.user.UserData;

public class GameDispatcher {
    public static Dispatcher get() {
        Dispatcher dp = new Dispatcher();
        dp.addHandler(
                new Handler(
                        new State(GameProcessor.CREATE),
                        request -> {
                            for(var i: GameProcessor.RACES) {
                                if(request.getCallbackData().equals(i.getClass().getSimpleName())){
                                    UserData userData = (UserData) request.getUserData().get();
                                    userData.player = ResourceManager.createResource(request.getUserId(), i.init());
                                    request.getUserData().update(userData);
                                    request.response = Response.builder()
                                            .userId(request.getUserId())
                                            .text("Введите имя")
                                            .build();
                                    return;
                                }
                            }
                            UserData userData = (UserData) request.getUserData().get();
                            Creature player = (Creature) userData.getPlayer().get();
                            player.setName(request.getCallbackData());
                            userData.getPlayer().update(player);
                            request.response = Response.builder()
                                    .userId(request.getUserId())
                                    .text("""
                                            Персонаж создан.
                                            Ваша задача: выбраться из подземелья.
                                            /inspect - осмотреть окружение
                                            /data - посмотреть информацию о персонаже
                                            /bag - открыть инвентарь
                                            /retry - начать заново
                                            /help - помощь"""
                                    )
                                    .build();
                            userData.state = GameProcessor.NONE;
                            request.getUserData().update(userData);
                        }
                )
        );

        return dp;
    }
}
