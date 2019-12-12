package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.GetInvitationUserLogRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserWorthMentioning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface UserWorthMentioningMapper extends BaseMapper<UserWorthMentioning> {

}
