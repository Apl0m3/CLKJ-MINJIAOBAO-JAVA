package com.lingkj.project.integral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.integral.entity.IntegralUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


/**
 * 用户积分
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Mapper
public interface IntegralUserMapper extends BaseMapper<IntegralUser> {

    /**
     * 查询用户积分
     *
     * @param userId
     * @return
     */
    IntegralUser selectByUserId(Long userId);

    /**
     * 修改用户记录
     *  @param id
     * @param integral
     * @param current
     */
    void updateIntegral(@Param("id") Long id, @Param("integral") Integer integral, @Param("current")Date current);
    /**
     * 查询用户积分 加锁
     *
     * @param userId
     * @return
     */
    IntegralUser selectByUserIdForUpdate(Long userId);
}
