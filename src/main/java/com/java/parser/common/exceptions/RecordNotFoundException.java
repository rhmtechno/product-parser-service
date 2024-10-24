package com.java.parser.common.exceptions;

import com.java.parser.domain.enums.ResponseMessage;

public class RecordNotFoundException extends CustomRootException {
    public RecordNotFoundException(ResponseMessage responseMessage) {
        super(responseMessage);
    }

    public RecordNotFoundException(String messageCode, String messageKey) {
        super(messageCode, messageKey);
    }
}
