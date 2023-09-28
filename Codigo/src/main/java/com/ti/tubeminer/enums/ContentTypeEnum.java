package com.ti.tubeminer.enums;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum ContentTypeEnum {

    VIDEO,
    COMMENT;

    public static final String CONTENT_TYPE_NULL_CHECK_CONSTRAINT = "VARCHAR (255) CHECK (kind IN ('VIDEO', 'COMMENT'))";

}
