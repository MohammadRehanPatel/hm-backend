package com.hm.backend.dto;


import com.hm.backend.constants.ErrorMessages;
import com.twilio.exception.InvalidRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;

    public void isValid(){
        if(
                StringUtils.isEmpty(email)
                        || StringUtils.isEmpty(password)
        ){
            throw new InvalidRequestException(
                    ErrorMessages.INVALID_LOGIN_REQUEST.getErrorMessage(),
                    ErrorMessages.INVALID_LOGIN_REQUEST.getErrorCode()
            );
        }
    }
}
