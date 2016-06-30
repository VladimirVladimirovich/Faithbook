package com.dtsey.inbeliefbackend.exception;

/**
 * Created by dtsey on 3/22/15.
 */
public class UserAlreadyRegisteredException extends Exception{
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
