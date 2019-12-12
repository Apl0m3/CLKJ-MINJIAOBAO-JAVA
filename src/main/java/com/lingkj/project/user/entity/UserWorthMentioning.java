package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_worth_mentioning")
public class UserWorthMentioning {
    @TableId(type = IdType.INPUT)
    public Long userId;
    public String facebookId;
    public String instagramId;
    public String youtubeId;
    public String twitterId;
}
