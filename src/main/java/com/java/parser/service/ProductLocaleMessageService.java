package com.java.parser.service;

import com.java.parser.common.exceptions.RecordNotFoundException;
import com.java.parser.domain.common.LocaleMessageDto;
import com.java.parser.domain.entity.LocaleMessage;
import com.java.parser.domain.enums.ResponseMessage;
import com.java.parser.repository.LocaleMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductLocaleMessageService extends BaseService {

    private final LocaleMessageRepository localeMessageRepository;

    public LocaleMessageDto getLocaleData(String locale) {

        LocaleMessage localeMessage = localeMessageRepository.findLocaleMessageByLocale(locale);

        if (Objects.isNull(localeMessage))
            throw new RecordNotFoundException(ResponseMessage.LOCALE_RECORD_NOT_FOUND);

        return new LocaleMessageDto(localeMessage.getLocale(), localeMessage.getContent());
    }

}
