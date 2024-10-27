package com.gameprocessor.resourcemanager;

public class Resource {
    public String id;

    public Resource(){}
    public Resource(String id){
        this.id = id;
    }

    public Resource(String id, String objectClass){
        this.id = id+":"+objectClass;
    }

    public String getUserId() {
        String[] args = id.split(":");
        return args[0];
    }

    public String getId() {
        return id;
    }

    public String getObjectClass() {
        String[] args = id.split(":");
        return args[2];
    }

    public Object get(){
        return ResourceManager.getObject(this);
    }

    public void update(Object object){
        ResourceManager.update(this, object);
    }

    public void delete(){
        ResourceManager.delete(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj instanceof Resource resource) {
            return id.equals(resource.id);
        }
       return false;
    }
}
