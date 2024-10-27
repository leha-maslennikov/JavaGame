package com.gameprocessor.resourcemanager;

public interface Storage {
    Resource createResource(String Id, Object object);
    Object getObject(Resource resource);
    Resource update(Resource resource, Object object);
    void delete(Resource resource);
    void deleteUser(String userId);
    boolean hasUser(String userId);
}
