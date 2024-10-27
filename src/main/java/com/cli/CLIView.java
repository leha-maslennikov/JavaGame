package com.cli;

import com.gameprocessor.GameProcessor;
import com.gameprocessor.user.Request;
import com.gameprocessor.user.Response;
import java.util.LinkedList;
import java.util.Scanner;

public class CLIView {
    GameProcessor gameProcessor;
    public CLIView() {
        this.gameProcessor = new GameProcessor();
    }
    public CLIView(GameProcessor gameProcessor) {
        this.gameProcessor = gameProcessor;
    }
    public void start() {
        System.out.println("/start - для начала");
        LinkedList<String> callbackData = new LinkedList<>();
        while(true) {
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            var request = Request.builder()
                    .userId(1)
                    .callbackData(s);
            if(!callbackData.isEmpty()) {
                try {
                    request.callbackData(callbackData.get(Integer.parseInt(s)));
                }
                catch (Exception e) {

                }
                callbackData.clear();
            }
            Response response = this.gameProcessor.handleRequest(request.build());
            System.out.println(response.text);
            int k = 0;
            for(var i: response.objects){
                System.out.println(k++ + " " + i.text);
                callbackData.add(i.callbackData);
            }
        }
    }
}