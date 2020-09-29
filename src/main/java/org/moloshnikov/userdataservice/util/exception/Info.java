package org.moloshnikov.userdataservice.util.exception;

public class Info {

    private boolean success;
    private String[] errors;

    public Info() {

    }

    public Info(boolean success, String... errors) {
        this.success = success;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}