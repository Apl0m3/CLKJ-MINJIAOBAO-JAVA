package com.lingkj.project.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.user.dto.ApiUserMessageRespDto;
import com.lingkj.project.user.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-11 14:22:51
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {
    /**
     * 查询某订单是否存在新消息
     * @param userId
     * @param transactionRecordId
     * @return
     */
    Integer countNewApi(@Param("userId") Long userId, @Param("transactionRecordId")Long transactionRecordId);

    /**
     * 最新一条未读的信息
     * @param userId
     * @return
     */
    UserMessage newestMessage(@Param("userId")Long userId);

    /**
     * 查询最消息
     * @param userId
     * @param recordId
     * @param transactionCommodityId
     * @param limit
     * @param pageLimit
     * @return
     */
    List<ApiUserMessageRespDto> selectListById(@Param("userId") Long userId, @Param("recordId") Long recordId, @Param("transactionCommodityId") Long transactionCommodityId, @Param("limit") Integer limit, @Param("pageSize") Integer pageSize);

    /**
     * 统计消息
     * @param userId
     * @param recordId
     * @param transactionCommodityId
     * @return
     */
    Integer selectCountById(@Param("userId") Long userId, @Param("recordId") Long recordId, @Param("transactionCommodityId") Long transactionCommodityId);

    /**
     * 统计消息
     * @param userId
     * @param recordId
     */
    void updateStatusByRecordId(@Param("userId") Long userId, @Param("recordId") Long recordId,@Param("transactionCommodityId") Long transactionCommodityId);
}
