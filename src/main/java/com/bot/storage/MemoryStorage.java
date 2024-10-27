package com.bot.storage;

import java.util.HashMap;
import java.util.Map;

public class MemoryStorage extends Storage{
    private final Map<Long, Map<String, Object>> storage = new HashMap<>();

    /**
     * @param userId id пользователя
     * @param key    название ключа для данных
     * @param value  данные
     * @return возвращает передаваемые данные
     */
    @Override
    public Object setUserData(long userId, String key, Object value) throws Exception {
        if(key == null){
            throw new Exception("null key");
        }
        if(value == null){
            throw new Exception("null value");
        }
        Map<String, Object> data = storage.getOrDefault(userId, new HashMap<>());
        data.put(key, value);
        storage.put(userId, data);
        return  value;
    }

    /**
     * @param userId id пользователя
     * @param key    название ключа для данных
     * @return возвращает данные или null
     */
    @Override
    public Object getUserData(long userId, String key) throws Exception {
        if(key == null){
            throw new Exception("null key");
        }
        Map<String, Object> data = storage.getOrDefault(userId, new HashMap<>());
        return data.get(key);
    }

    /**
     * Сбрасывает все данные пользователя
     * @param userId id пользователя
     */
    @Override
    public void clearUserData(long userId) {
        storage.remove(userId);
    }
}
