package com.lingkj.project.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.message.dto.MessageRespDto;
import com.lingkj.project.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 逻辑删除
     *
     * @param asList
     */
    void updateStatusByIds(@Param("asList") List<Long> asList);

    /**
     * 统计消息条数
     *
     * @param userId
     * @param type
     * @param createTime
     * @return
     */
    Integer queryMessageCount(@Param("userId") Long userId, @Param("type") Integer type, @Param("createTime") Date createTime);

    /**
     * 查询消息总数
     *
     * @param pageSize
     * @param limit
     * @param userId
     * @param type
     * @param createTime
     * @return
     */

    List<MessageRespDto> queryMessageList(@Param("pageSize") Integer pageSize, @Param("limitStart") Integer limit,
                                               @Param("userId") Long userId, @Param("type") Integer type, @Param("createTime") Date createTime);

    /**
     * 通过消息id和用户id 查询
     *
     * @param id
     * @param userId
     * @return
     */
    MessageRespDto selectByUserId(@Param("id") Long id, @Param("userId") Long userId);

    MessageRespDto  selectByUserIdNewMessage(@Param("userId") Long userId);

    /**
     * 统计用户未读条数
     *
     * @param userId
     * @param createTime
     * @return
     */
    Integer countUnreadMessages(@Param("userId") Long userId, @Param("createTime") Date createTime);
}
