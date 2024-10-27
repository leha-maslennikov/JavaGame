package com.gameprocessor.dispatcher.filters;

import com.gameprocessor.resourcemanager.ResourceManager;
import com.gameprocessor.user.Request;
import com.gameprocessor.user.UserData;

public class State implements Filter {
    
    private final String state;

    public State(){
        this.state = "";
    }

    public State(String state){
        this.state = state;
    }

    @Override
    public boolean call(Request event) {
        if(!ResourceManager.hasUser(event.getUserId())) return false;
        UserData userData = (UserData) event.getUserData().get();
        return userData.state.equals(state);
    }
}
