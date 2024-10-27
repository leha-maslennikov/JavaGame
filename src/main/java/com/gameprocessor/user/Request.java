package com.gameprocessor.user;

import com.gameprocessor.resourcemanager.Resource;

public class Request {
    public String userId;
    public String callbackData;
    public Response response;

    public String getUserId() {
        return userId;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public Resource getUserData(){
        return new Resource(
                this.userId+":UserData",
                UserData.class.getName()
        );
    }

    public Response getResponse() {
        return response;
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static class RequestBuilder {
        private final Request request;

        public RequestBuilder() {
            this.request = new Request();
        }

        public RequestBuilder userId(long userId) {
            request.userId = Long.toString(userId);
            return this;
        }

        public RequestBuilder userId(String userId) {
            request.userId = userId;
            return  this;
        }

        public RequestBuilder callbackData(String callbackData) {
            request.callbackData = callbackData;
            return  this;
        }

        public Request build() {
            return request;
        }
    }
}
