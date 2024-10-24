package com.java.parser.common.exceptions;

import com.java.parser.domain.enums.ResponseMessage;

public abstract class PreValidationException extends CustomRootException {
    public PreValidationException(ResponseMessage responseMessage) {
        super(responseMessage);
    }

    public PreValidationException(String messageCode, String messageKey) {
        super(messageCode, messageKey);
    }
}
