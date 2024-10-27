package com.gameprocessor.entities.items;

import com.gameprocessor.entities.Actions;
import com.gameprocessor.entities.Room;
import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.ResourceManager;

public class Door extends Item {
    public Resource room;

    public Door() {
        this.room = null;
    }
    public Door(String name, String description, Room room, String userId){
        super(name, description);
        this.room = ResourceManager.createResource(userId, room);
    }

    public Resource getRoom() {
        return room;
    }

    /**
     * @return текст, который будет отправлен пользователю вместе с объектом
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return текст, который будет отправлен пользователю, после того, как он выберет данный объект
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return действия, которые можно проделать с объектом, после его выбора, должны быть реализованы в GameProcessor
     */
    @Override
    public Actions getActions() {
        return Actions.builder()
                .addAction("open")
                .build();
    }
}
