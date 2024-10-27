package com.gameprocessor.user;

import com.gameprocessor.GameProcessor;
import com.gameprocessor.entities.LevelManager;
import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.ResourceManager;
import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.entities.Room;

public class UserData {
    public String state;
    public Resource player;
    public Resource room;
    public boolean combatFlag;
    public Resource levelManager;

    public UserData(){}

    public UserData(String userId, Creature player, Room room, LevelManager levelManager) {
        this.state = GameProcessor.NONE;
        this.player = ResourceManager.createResource(userId, player);
        this.room = ResourceManager.createResource(userId, room);
        this.levelManager =  ResourceManager.createResource(userId, levelManager);
        this.combatFlag = false;
    }

    public Resource getPlayer() {
        return player;
    }

    public Resource getRoom() {
        return room;
    }

    public Resource getLevelManager() {
        return levelManager;
    }

    public boolean getCombatFlag() {
        return combatFlag;
    }
}
