package com.ti.tubeminer.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ProgrammingLanguageEnum {

    PYTHON, JAVA, C, CPP, CSHARP, JAVASCRIPT, OTHER;

    public static final String PROGRAMMING_LANGUAGE_NULL_CHECK_CONSTRAINT = "VARCHAR (255) CHECK (kind IN ('PYTHON', 'JAVA', 'C', 'CPP', 'CSHARP', 'JAVASCRIPT'))";
}
