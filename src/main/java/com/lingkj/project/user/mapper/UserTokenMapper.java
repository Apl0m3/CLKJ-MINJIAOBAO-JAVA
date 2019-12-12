package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.entity.UserToken;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Token
 *
 * @author chenyongsong
 *
 * @date 2019-07-05 10:57:20
 */
@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {
    UserToken queryByToken(String token);
}
