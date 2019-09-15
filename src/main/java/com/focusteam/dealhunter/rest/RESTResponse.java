package com.focusteam.dealhunter.rest;

import java.util.ArrayList;
import java.util.HashMap;

public class RESTResponse {
    private HashMap<String, Object> response;
    private RESTResponse(){this.response = new HashMap<>();}

    public HashMap<String, Object> getResponse(){return response;}
    public void setResponse(HashMap<String, Object> response){
        this.response = response;
    }

    public void addResponse(String key, Object value){
        this.response.put(key, value);
    }

    public static class Error{
        private HashMap<String, String> errors;
        private int status;
        private String message;
        private Object data;

        public Error() {
            this.errors = new HashMap<>();
            this.status = 0;
            this.message = "";
            this.data = null;
        }

        public Error setStatus(int status) {
            this.status = status;
            return this;
        }

        public Error setMessage(String message) {
            this.message = message;
            return this;
        }

        public Error setData(Object data) {
            this.data = data;
            return this;
        }

        public Error addError(String key, String value) {
            this.errors.put(key, value);
            return this;
        }

        public Error addErrors(HashMap<String, String> errors) {
            this.errors.putAll(errors);
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("data", this.data);
            String errorKey = "error";
            if (this.errors.size() > 1) {
                errorKey = "errors";
            }
            restResponse.addResponse(errorKey, this.errors);
            return restResponse.getResponse();
        }
    }

    public static class SimpleError {

        private int status;
        private String message;
        private Object data;

        public SimpleError() {
            this.status = 0;
            this.message = "";
        }

        public SimpleError setCode(int status) {
            this.status = status;
            return this;
        }

        public SimpleError setMessage(String message) {
            this.message = message;
            return this;
        }

        public SimpleError setData(Object data) {
            this.data = data;
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("data", this.data);
            return restResponse.getResponse();
        }
    }

    public static class Success{
        private int status;
        private String message;
        private Object data;
        private RESTPagination pagination;

        public Success() {
            this.status = 200;
            this.message = "Success";
        }

        public Success setStatus(int status) {
            this.status = status;
            return this;
        }

        public Success setMessage(String message) {
            this.message = message;
            return this;
        }

        public Success setPagination(RESTPagination pagination) {
            this.pagination = pagination;
            return this;
        }

        public Success setData(Object obj) {
            this.data = obj;
            return this;
        }

        public Success setData(Iterable listObj) {
            this.data = new ArrayList<>();
            this.data = listObj;
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("data", this.data);
            if (this.pagination != null) {
                restResponse.addResponse("pagination", this.pagination);
            }
            return restResponse.getResponse();
        }
    }
}
