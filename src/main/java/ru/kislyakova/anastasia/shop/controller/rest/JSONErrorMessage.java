package ru.kislyakova.anastasia.shop.controller.rest;

public class JSONErrorMessage {
    private final ErrorDescription error;

    private JSONErrorMessage(ErrorDescription error) {
        this.error = error;
    }

    public static JSONErrorMessage create(String type, String msg) {
        return new JSONErrorMessage(new ErrorDescription(type, msg));
    }

    public ErrorDescription getError() {
        return error;
    }

    public static class ErrorDescription {
        private final String type;
        private final String message;

        private ErrorDescription(String type, String msg) {
            this.type = type;
            this.message = msg;
        }

        public String getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }
}
