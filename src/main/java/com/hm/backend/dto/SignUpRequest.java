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
public class SignUpRequest {
    private String name;
    private String email;
    private String password;

    public void isValid(){
        if(StringUtils.isEmpty(name)
                || StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)
        ){
            throw new InvalidRequestException(
                    ErrorMessages.INVALID_SIGN_UP_REQUEST.getErrorMessage(),
                    ErrorMessages.INVALID_SIGN_UP_REQUEST.getErrorCode()
            );
        }
    }
}
