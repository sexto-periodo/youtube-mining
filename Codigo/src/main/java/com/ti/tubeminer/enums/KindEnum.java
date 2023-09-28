package com.ti.tubeminer.enums;

public enum KindEnum {

    YOUTUBE_SEARCH_LIST("youtube#searchResult"),
    YOUTUBE_COMMENT_LIST("youtube#commentThreadListResponse");

    String value;

    KindEnum(String value){
        this.value = value;
    }

//    public static final String KIND_NULL_CHECK_CONSTRAINT = "VARCHAR (255) CHECK (kind IN ('YOUTUBE_SEARCH_LIST', 'YOUTUBE_COMMENT_LIST'))";

}
