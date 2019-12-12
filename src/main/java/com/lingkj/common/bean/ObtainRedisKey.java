package com.lingkj.common.bean;

import com.lingkj.common.utils.GenerateCode;
import lombok.Data;

/**
 * ObtainRedisKey
 *
 * @author chen yongsong
 * @className ObtainRedisKey
 * @date 2019/6/27 14:23
 */
@Data
public class ObtainRedisKey {

    private String mobile;
    private Integer type;
    private String redisKey;
    private String verifyCode;

    public ObtainRedisKey(String mobile, Integer type) {
        this.mobile = mobile;
        this.type = type;
    }


    public ObtainRedisKey invoke() {
        redisKey = "send_code_" + mobile;
        verifyCode = GenerateCode.generateVerifyCode();
        switch (type) {
            case 1:
                redisKey += "_register";
                break;
            case 2:
                redisKey += "_login";
                break;
            case 3:
                redisKey += "_forget";
                break;
            case 4:
                redisKey += "_updatePwd";
                break;

            default:

        }
        return this;
    }
}
