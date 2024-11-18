package com.hm.backend.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    USER_ALREADY_EXISTS("E409","User already exists!"),
    USER_NOT_FOUND("E404","User Not Found!"),
    DOCTOR_NOT_FOUND("D404","Doctor Not Found!"),
    PASSWORD_NOT_MATCHED("E401","Password Not Matched!"),
    INVALID_ACCESS_TOKEN("T401","Invalid Access Token!"),

    INVALID_SIGN_UP_REQUEST("A400","Invalid Sign Up Request!"),
    INVALID_LOGIN_REQUEST("A400","Invalid Login  Request!"),
    INVALID_CHANGE_PASSWORD_REQUEST("A400","Invalid Change Password Request!");

    String errorCode;
    String errorMessage;

}
