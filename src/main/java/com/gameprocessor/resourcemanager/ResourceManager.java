package com.gameprocessor.resourcemanager;

public class ResourceManager {
    public static Storage storage = new DefaultStorage();
    private static Integer count = 1;

    public static Resource createResource(String userId, String id, Object object){
        return storage.createResource(userId+":"+id, object);
    }

    public static Resource createResource(String userId, Object object) {
        synchronized (count) {
            return storage.createResource(userId+":"+count++, object);
        }
    }

    public static Object getObject(Resource resource){
        return storage.getObject(resource);
    }

    public static Resource update(Resource resource, Object object){
        return storage.update(resource, object);
    }

    public static void delete(Resource resource){
        storage.delete(resource);
    }

    public static void deleteUser(String userId) {
        storage.deleteUser(userId);
    }

    public static boolean hasUser(String userId) {
        return storage.hasUser(userId);
    }
}
