package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.UserAccountLogaAndTransactionIdDto;
import com.lingkj.project.user.entity.UserAccountLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户钱包记录
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
@Mapper
public interface UserAccountLogMapper extends BaseMapper<UserAccountLog> {
    List<UserAccountLogaAndTransactionIdDto> getUserAccountLog(@Param("userId") Long userId, @Param("start")Integer start, @Param("end") Integer end);
    Integer queryUserAccountLogCount(@Param("userId") Long userId);
}
