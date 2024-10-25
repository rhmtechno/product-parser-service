package com.java.parser.domain.common;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class Message implements Serializable {
    private String id;
    private String locale;
    private String key;
    private String content;
}
