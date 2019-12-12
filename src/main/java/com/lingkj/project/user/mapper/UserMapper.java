package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.GetInvitationUserLogRespDto;
import com.lingkj.project.user.entity.User;
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
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 查询
	 * @param userId
	 * @param pageStart
	 * @param pageEnd
	 * @return
	 */
	List<GetInvitationUserLogRespDto> getInvitationUserLog(@Param("userId") Long userId, @Param("pageStart") Integer pageStart, @Param("pageEnd") Integer pageEnd);

	/**
	 * 统计
	 * @param userId
	 * @return
	 */
	Integer getInvitationUserLogCount(@Param("userId") Long userId);

	/**
	 * 查询
	 * @param limit
	 * @param pageSize
	 * @return
	 */
    List<User> queryList(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize);
}
