package com.java.parser.common.components;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.java.parser.common.logger.ParserServiceLogger;
import com.java.parser.domain.common.LocaleMessageDto;
import com.java.parser.domain.common.Message;
import com.java.parser.domain.enums.SpecialCharsEnum;
import com.java.parser.service.ProductLocaleMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("messageSource")
@RequiredArgsConstructor
public class MessageSourceComponent extends AbstractMessageSource {

    private final ProductLocaleMessageService coreLocaleMessageService;
    private final ParserServiceLogger logger;

    @Override
    protected MessageFormat resolveCode(String key, Locale locale) {
        String id = key.concat(SpecialCharsEnum.COLON.getSign()).concat(locale.getLanguage());

        //Optional<Message> messageOpt = coreLocaleMessageService.findById(id);

        //if (messageOpt.isPresent()) return new MessageFormat(messageOpt.get().getContent(), locale);

        List<Message> messageList = getLocaleMessage(locale.getLanguage());
        logger.trace("Locale message list:->" + new Gson().toJson(messageList) + "\n");
        Optional<Message> targetMessageOpt = getTargetMessage(id, messageList);

        if (targetMessageOpt.isEmpty()) {
            logger.trace("\n\t-------Message Not found--------" + id + "\n");
            return new MessageFormat("Something went wrong", locale);
        }

        Message targetMessage = targetMessageOpt.get();

        return new MessageFormat(targetMessage.getContent(), locale);
    }

    private Optional<Message> getTargetMessage(String id, List<Message> messageList) {
        return messageList.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    private List<Message> getLocaleMessage(String locale) {
        LocaleMessageDto localeMessageDto = coreLocaleMessageService.getLocaleData(locale);
        String jsonContent = localeMessageDto.getContent();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, String> map = objectMapper.readValue(jsonContent, new TypeReference<>() {
            });

            List<Message> messages = map.entrySet().stream().map(this::prepareEntity).collect(Collectors.toList());

            return messages.stream().map(item -> {
                item.setLocale(locale);
                item.setId(item.getKey().concat(SpecialCharsEnum.COLON.getSign()).concat(locale));
                return item;
            }).collect(Collectors.toList());

        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return Collections.emptyList();
    }

    private Message prepareEntity(Map.Entry<String, String> mapEntry) {
        Message message = new Message();
        message.setKey(mapEntry.getKey());
        message.setContent(mapEntry.getValue());
        return message;
    }
}
