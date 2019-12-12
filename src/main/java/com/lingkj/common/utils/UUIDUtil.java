package com.lingkj.common.utils;

import java.util.UUID;

/**
 * Created by Jojo on 2018/7/30 18:49.
 */
public class UUIDUtil {
    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}
