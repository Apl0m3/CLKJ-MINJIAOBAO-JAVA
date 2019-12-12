package com.lingkj.project.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * UserDto
 *
 * @author chen yongsong
 * @className UserDto
 * @date 2019/7/11 18:07
 */
@ApiModel("用户请求")
@Data
public class UserReqDto {
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

    /**
     * 所在地区
     */
    private String address;
}
