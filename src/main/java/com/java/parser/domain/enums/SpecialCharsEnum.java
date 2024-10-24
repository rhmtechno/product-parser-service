package com.java.parser.domain.enums;

public enum SpecialCharsEnum {

    COLON(":"),
    SPACE(" "),
    HYPHEN("-"),
    UNDERSCORE("_"),
    HASH("#"),
    AT_SIGN("@"),;

    private String sign;

    SpecialCharsEnum(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

}
