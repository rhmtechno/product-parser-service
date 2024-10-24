package com.java.parser.domain.enums;

public enum ApplicationSettingsCode {

    EXAMPLE_APP_SETTINGS(1001, "Example app settings."),
    ;

    private int code;
    private String name;

    ApplicationSettingsCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
