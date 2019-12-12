package com.lingkj.project.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * UserDto
 *
 * @author chen yongsong
 * @className UserDto
 * @date 2019/7/11 18:07
 */
@ApiModel("用户")
@Data
public class UserRespDto {
    /**
     *
     */
    private String userName;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String nickName;
    /**
     *
     */
    private String avatar;
    /**
     *
     */
    private String birthday;
    /**
     *
     */
    private Integer gender;
    private Integer memberType;
    /**
     * 邮政编码
     */
    private String postalCode;
    /**
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;
    /**
     * 国家
     */
    private String country;

    /**
     * 所在地区详细地址
     */
    private String address;
    private String addressName;
    /**
     * 省
     */
    private String provinceStr;
    /**
     * 市
     */
    private String cityStr;
    private Long userRoleId;
}
