package com.lingkj.project.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.message.entity.MessageUserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Mapper
public interface MessageUserLogMapper extends BaseMapper<MessageUserLog> {
    /**
     * 查询消消息是否已读
     *
     * @param messageId
     * @param userId
     * @return
     */
    Integer selectCountById(@Param("messageId") Long messageId, @Param("userId") Long userId);
}
