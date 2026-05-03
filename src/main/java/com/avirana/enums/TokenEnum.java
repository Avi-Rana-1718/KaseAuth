package com.avirana.enums;

public enum TokenEnum {
    ACCESS("access"), REFRESH("refresh");

    String value;

    TokenEnum(String value) {
        this.value = value;
    }

    public String getValue() {return this.value;}
}
