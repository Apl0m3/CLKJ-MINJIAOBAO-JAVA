package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.entity.UserBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-10-14 17:24:27
 */
@Mapper
public interface UserBankMapper extends BaseMapper<UserBank> {


    UserBank queryUseBank(@Param("userId") Long userId,@Param("applicationId") Long applicationId,@Param("type") Integer type);

    /**
     * 查询银行账户
     * @param userId
     * @return
     */
    UserBank selectByUserId(Long userId);
}
