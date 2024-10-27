package com.main;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameprocessor.entities.items.Consumable;
import com.gameprocessor.entities.LevelManager;
import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.entities.creatures.Dwarf;
import com.gameprocessor.entities.creatures.Human;
import com.gameprocessor.entities.items.Box;
import com.gameprocessor.entities.items.Door;
import com.gameprocessor.entities.items.Equipment;
import com.gameprocessor.entities.items.Item;
import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.Storage;
import com.gameprocessor.entities.*;
import com.gameprocessor.user.UserData;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.LinkedList;

public class FilesStorage implements Storage {
    public LinkedList<Class> classes = new LinkedList<>();
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public FilesStorage() {
        classes.add(Box.class);
        classes.add(Creature.class);
        classes.add(Door.class);
        classes.add(Equipment.class);
        classes.add(Item.class);
        classes.add(Room.class);
        classes.add(UserData.class);
        classes.add(LevelManager.class);
        classes.add(Consumable.class);
        classes.add(Dwarf.class);
        classes.add(Human.class);
    }
    @Override
    public Resource createResource(String id, Object object) {
        Resource resource = new Resource(id, object.getClass().getName());
        String str = resource.getId().replace(":", "_")+".json";
        File file = new File("user_data", resource.getUserId());
        if(!file.exists()) file.mkdir();
        try {
            mapper.writeValue(
                    new File(file.getPath() + "\\" + str),
                    object
            );
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resource;
    }

    @Override
    public Object getObject(Resource resource) {
        String str = resource.getId().replace(":", "_")+".json";
        File file = new File("user_data", resource.getUserId());
        if(!file.exists()) {
            throw new RuntimeException(file.getPath()+" not exist");
        }
        file = new File(file.getPath()+"\\"+str);
        if(!file.exists()) {
            throw new RuntimeException(file.getPath()+" not exist");
        }
        try {
            for(Class i: classes) {
                if(resource.getObjectClass().equals(i.getName())) {
                    return mapper.readValue(file, i);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("getObject faild: " + resource.id);
    }

    @Override
    public Resource update(Resource resource, Object object) {
        if(!object.getClass().getName().equals(resource.getObjectClass())) {
            throw new RuntimeException(object.getClass().getName()+"!="+resource.getObjectClass());
        }
        String str = resource.getId().replace(":", "_")+".json";
        File file = new File("user_data", resource.getUserId());
        if(!file.exists()) {
            throw new RuntimeException(file.getPath()+" not exist");
        }
        file = new File(file.getPath()+"\\"+str);
        if(!file.exists()) {
            throw new RuntimeException(file.getPath()+" not exist");
        }
        try {
            mapper.writeValue(file, object);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resource;
    }

    @Override
    public void delete(Resource resource) {
        String str = resource.id.replace(":", "_")+".json";
        File file = new File("user_data", resource.getUserId());
        if(!file.exists()) {
            throw new RuntimeException(file.getPath()+" not exist");
        }
        file = new File(file.getPath()+"\\"+str);
        if(!file.exists()) {
            throw new RuntimeException(file.getPath()+" not exist");
        }
        file.delete();
    }

    @Override
    public void deleteUser(String userId) {
        File file = new File("user_data", userId);
        if(!file.exists()) {
            throw new RuntimeException(file.getPath()+" not exist");
        }
        try {
            FileUtils.deleteDirectory(file);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasUser(String userId) {
        File file = new File("user_data", userId);
        return file.exists();
    }
}
