package com.example.gamestorespring.exception;

public class UserAlreadyExistException extends Exception{
    private int status;
    public UserAlreadyExistException(int status, String message) {
        super(message);
        this.status = status;
    }

    public UserAlreadyExistException(String message) {
        super(message);
        this.status = 402;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
