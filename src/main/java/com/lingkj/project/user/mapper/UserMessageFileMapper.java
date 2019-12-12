package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.user.dto.ApiUserMessageFileDto;
import com.lingkj.project.user.entity.UserMessageFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 站内消息图片
 * 
 * @author chenyongsong
 * @date 2019-10-21 20:04:11
 */
@Mapper
public interface UserMessageFileMapper extends BaseMapper<UserMessageFile> {
    /**
     * 消息图片
     * @param messageId
     * @return
     */
    List<ApiUserMessageFileDto> selectListByMessageIdApi(Long messageId);
}
