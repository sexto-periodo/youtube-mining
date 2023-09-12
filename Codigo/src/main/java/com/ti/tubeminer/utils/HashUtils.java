package com.ti.tubeminer.utils;

import java.util.UUID;

public class HashUtils {

    public static String generateHash(){

        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
