package com.lingkj.project.integral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.project.integral.dto.IntegralUserLogRespDto;
import com.lingkj.project.integral.entity.IntegralUserLog;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户积分记录
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Mapper
public interface IntegralUserLogMapper extends BaseMapper<IntegralUserLog> {
    /**
     * 查询积分记录
     *
     * @param userId
     * @return
     */
    Integer queryIntegralLogCount(Long userId);

    /**
     * 积分记录分页查询
     *
     * @param limit
     * @param pageSize
     * @param userId
     * @return
     */
    List<IntegralUserLogRespDto> queryIntegralLogPage(@Param("pageSize") Integer pageSize, @Param("limitStart") Integer limit, @Param("userId") Long userId);
}
