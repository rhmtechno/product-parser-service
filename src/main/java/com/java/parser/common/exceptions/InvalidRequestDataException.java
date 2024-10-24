package com.java.parser.common.exceptions;

import com.java.parser.domain.enums.ResponseMessage;

public class InvalidRequestDataException extends PreValidationException {

    public InvalidRequestDataException(ResponseMessage responseMessage) {
        super(responseMessage);
    }

    public InvalidRequestDataException(String messageCode, String messageKey) {
        super(messageCode, messageKey);
    }
}
