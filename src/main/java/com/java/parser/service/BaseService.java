package com.java.parser.service;

import com.java.parser.common.logger.ParserServiceLogger;
import com.java.parser.domain.enums.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Date;
import java.util.Optional;

public class BaseService {

    protected ParserServiceLogger logger;
    private LocaleMessageService messageService;
    private HttpServletRequest request;

    @Autowired
    public void setLogger(ParserServiceLogger logger) {
        this.logger = logger;
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Lazy
    @Autowired
    public void setMessageService(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    public String getMessage(String key) {
        return messageService.getLocalMessage(key);
    }

    public String getMessage(ResponseMessage key, Object... objects) {
        return messageService.getLocalMessage(key, objects);
    }

    public String getMessage(String key, Object... objects) {
        return messageService.getLocalMessage(key, objects);
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public Optional<String> getHeaderValue(String headerName) {

        try {
            return Optional.ofNullable(request.getHeader(headerName));
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }

        return Optional.empty();
    }

}
