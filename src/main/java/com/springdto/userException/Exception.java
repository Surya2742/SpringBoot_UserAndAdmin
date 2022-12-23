package com.springdto.userException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exception extends RuntimeException {
    String message;

    public Exception(String fieldValue) {
        super(String.format("User not found with Email : %s", fieldValue));
        this.message = fieldValue;
    }

}
